package at.skert.swe.ue6.contracts;

public interface ActionWithParam<T> {
  void invoke(T param);
}