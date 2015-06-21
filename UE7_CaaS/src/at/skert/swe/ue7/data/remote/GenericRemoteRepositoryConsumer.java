package at.skert.swe.ue7.data.remote;

import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import at.skert.swe.ue7.contracts.Action;
import at.skert.swe.ue7.contracts.ActionWithParam;
import at.skert.swe.ue7.contracts.data.IEntity;
import at.skert.swe.ue7.contracts.data.IRemoteRepository;
import at.skert.swe.ue7.contracts.data.IRepository;

public class GenericRemoteRepositoryConsumer<T extends IEntity> implements IRepository<T> {
  protected IRemoteRepository remoteRepository;
  private Type repositoryType;

  public GenericRemoteRepositoryConsumer(Type repositoryType, IRemoteRepository remoteRepository) {
    this.remoteRepository = remoteRepository;
    this.repositoryType = repositoryType;
  }

  @Override
  public List<T> getAll(){
    try {
      return remoteRepository.getAllForType(repositoryType);
    } catch (RemoteException e) {
      e.printStackTrace();
      return new ArrayList<T>();
    }
  }

  @Override
  public void getById(long id, ActionWithParam<T> continuation, ActionWithParam<Exception> onError) {
    onError.invoke(new NotImplementedException());
  }

  @Override
  public void create(T entity, Action onSuccess, ActionWithParam<Exception> onError) {
    try {
      remoteRepository.create((IEntity) entity);
      onSuccess.invoke();
    } catch (Exception e) {
      onError.invoke(e);
    }
  }

  @Override
  public void update(T entity, Action onSuccess, ActionWithParam<Exception> onError) {
    try {
      remoteRepository.update(entity);
      onSuccess.invoke();
    } catch (Exception e) {
      onError.invoke(e);
    }
  }

  @Override
  public void delete(T entity, Action onSuccess, ActionWithParam<Exception> onError) {
    try {
      remoteRepository.delete(entity);
      onSuccess.invoke();
    } catch (Exception e) {
      onError.invoke(e);
    }
  }

  @Override
  public List<T> getAllByPredicate(Predicate<? super T> predicate) {
    return getAll().stream().filter(predicate).collect(Collectors.toList());
  }
}