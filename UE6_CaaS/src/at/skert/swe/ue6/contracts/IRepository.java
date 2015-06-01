package at.skert.swe.ue6.contracts;

import java.util.List;
import java.util.function.Predicate;

import at.skert.swe.ue6.contracts.exceptions.EntityNotAddedException;

public interface IRepository<T extends IEntity> {
  List<T> getAll();
  void getById(long id, Continuation<T> continuation, ErrorContinuation errorContinuation);
  void create(T entity);
  void update(T entity) throws EntityNotAddedException;
  void delete(T entity) throws EntityNotAddedException;
  List<T> getAllByPredicate(Predicate<? super T> predicate);
}
