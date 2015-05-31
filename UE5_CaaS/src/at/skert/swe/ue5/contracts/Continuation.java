package at.skert.swe.ue5.contracts;

public interface Continuation<T extends IEntity> {
  void continueWith(T entity);
}
