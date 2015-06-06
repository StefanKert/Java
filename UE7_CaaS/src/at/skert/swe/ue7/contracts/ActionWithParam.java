package at.skert.swe.ue7.contracts;

public interface ActionWithParam<T> {
  void invoke(T param);
}