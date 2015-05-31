package at.skert.swe.ue6.contracts;

public interface Continuation<T extends IEntity> {
  void continueWith(T entity);
}
