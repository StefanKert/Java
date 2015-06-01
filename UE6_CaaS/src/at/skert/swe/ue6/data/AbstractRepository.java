package at.skert.swe.ue6.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import at.skert.swe.ue6.contracts.Continuation;
import at.skert.swe.ue6.contracts.ErrorContinuation;
import at.skert.swe.ue6.contracts.IEntity;
import at.skert.swe.ue6.contracts.IRepository;
import at.skert.swe.ue6.contracts.Menu;
import at.skert.swe.ue6.contracts.User;
import at.skert.swe.ue6.contracts.exceptions.EntityNotAddedException;

public abstract class AbstractRepository<T extends IEntity> implements IRepository<T> {
 protected List<T> staticList;
  
  public AbstractRepository(){
    staticList = new ArrayList<T>();
  }
  
  @Override
  public List<T> getAll() {
    return staticList;
  }

  @Override
  public List<T> getAllByPredicate(Predicate<? super T> predicate){
    return staticList.stream().filter(predicate).collect(Collectors.toList());
  }
  
  @Override
  public void create(T entity) {
    staticList.add(entity);
  }

  @Override
  public void update(T entity) throws EntityNotAddedException {
    Optional<T> entityOptional = staticList.stream().filter(x -> x.getId() == entity.getId()).findFirst();
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
    if(!staticList.removeIf(x -> x.getId() == entity.getId()))
      throw new EntityNotAddedException();
  }

  @Override
  public void getById(long id, Continuation<T> continueWith, ErrorContinuation errorContinuation) {
    Optional<T> entityOptional = staticList.stream().filter(x -> x.getId() == id).findFirst();
   if(!entityOptional.isPresent())
     errorContinuation.onError(new EntityNotAddedException());
   else
     continueWith.continueWith(entityOptional.get());
  }

}
