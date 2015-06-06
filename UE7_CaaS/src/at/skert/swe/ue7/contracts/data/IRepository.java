package at.skert.swe.ue7.contracts.data;

import java.util.List;
import java.util.function.Predicate;

import at.skert.swe.ue7.contracts.Action;
import at.skert.swe.ue7.contracts.ActionWithParam;

public interface IRepository<T extends IEntity> {
  List<T> getAll();

  void getById(long id, ActionWithParam<T> continuation, ActionWithParam<Exception> onError);

  void create(T entity, Action onSuccess, ActionWithParam<Exception> onError);

  void update(T entity, Action onSuccess, ActionWithParam<Exception> onError);

  void delete(T entity, Action onSuccess, ActionWithParam<Exception> onError);

  List<T> getAllByPredicate(Predicate<? super T> predicate);
}
