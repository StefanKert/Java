package at.skert.swe.ue6.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import at.skert.swe.ue6.contracts.Continuation;
import at.skert.swe.ue6.contracts.ErrorContinuation;
import at.skert.swe.ue6.contracts.IEntity;
import at.skert.swe.ue6.contracts.IRepository;
import at.skert.swe.ue6.contracts.User;
import at.skert.swe.ue6.contracts.exceptions.EntityNotAddedException;

public class Repository<T extends IEntity>  implements IRepository<T> {
  private List<T> _staticList;
  
  public Repository(){
    _staticList = new ArrayList<T>();
  }
  
  @Override
  public List<T> getAll() {
    return _staticList;
  }

  @Override
  public void create(T entity) {
    _staticList.add(entity);
  }

  @Override
  public void update(T entity) throws EntityNotAddedException {
    Optional<T> entityOptional = _staticList.stream().filter(x -> x.getId() == entity.getId()).findFirst();
   if(!entityOptional.isPresent()){
     throw new EntityNotAddedException();
   }
   else{
     T entityToUpdate = entityOptional.get();
     entityToUpdate = entity;
   }
  }

  @Override
  public void delete(T entity) throws EntityNotAddedException {
    if(!_staticList.removeIf(x -> x.getId() == entity.getId()))
      throw new EntityNotAddedException();
  }

  @Override
  public void getById(long id, Continuation<T> continueWith, ErrorContinuation errorContinuation) {
    Optional<T> entityOptional = _staticList.stream().filter(x -> x.getId() == id).findFirst();
   if(!entityOptional.isPresent())
     errorContinuation.onError(new EntityNotAddedException());
   else
     continueWith.continueWith(entityOptional.get());
  }

}
