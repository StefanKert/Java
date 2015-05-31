package at.skert.swe.ue6.contracts;

public interface ErrorContinuation {
  void onError(Exception exception);
}
