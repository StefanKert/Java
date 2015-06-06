package at.skert.swe.ue7.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import at.skert.swe.ue7.contracts.Action;
import at.skert.swe.ue7.contracts.ActionWithParam;
import at.skert.swe.ue7.contracts.data.IEntity;
import at.skert.swe.ue7.contracts.data.IRepository;
import at.skert.swe.ue7.contracts.exceptions.EntityNotAddedException;

public class AbstractRepository<T extends IEntity> implements
    IRepository<T> {
  protected List<T> staticList;
  private long idCounter = 0;

  public AbstractRepository() {
    staticList = new ArrayList<T>();
  }

  @Override
  public List<T> getAll() {
    return staticList;
  }

  @Override
  public List<T> getAllByPredicate(Predicate<? super T> predicate) {
    return staticList.stream().filter(predicate).collect(Collectors.toList());
  }

  @Override
  public void create(T entity, Action onSuccess,
      ActionWithParam<Exception> onError) {
    entity.setId(getNextIdForInsert());
    staticList.add(entity);
    onSuccess.invoke();
  }

  @Override
  public void update(T entity, Action onSuccess,
      ActionWithParam<Exception> onError) {
    if (!staticList.stream().anyMatch(x -> x.equals(entity))) {
      onError.invoke(new EntityNotAddedException());
    } else {
      staticList.stream().forEach(x -> {
        if (x.equals(entity))
          x = entity;
      });
      onSuccess.invoke();
    }
  }

  @Override
  public void delete(T entity, Action onSuccess,
      ActionWithParam<Exception> onError) {
    if (!staticList.removeIf(x -> x.equals(entity)))
      onError.invoke(new EntityNotAddedException());
    else
      onSuccess.invoke();
  }

  @Override
  public void getById(long id, ActionWithParam<T> continueWith,
      ActionWithParam<Exception> onError) {
    Optional<T> entityOptional = staticList.stream()
        .filter(x -> x.getId() == id).findFirst();
    if (!entityOptional.isPresent())
      onError.invoke(new EntityNotAddedException());
    else
      continueWith.invoke(entityOptional.get());
  }

  private long getNextIdForInsert() {
    return idCounter++;
  }
}
