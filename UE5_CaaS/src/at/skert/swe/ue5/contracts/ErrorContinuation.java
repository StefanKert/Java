package at.skert.swe.ue5.contracts;

public interface ErrorContinuation {
  void onError(Exception exception);
}
