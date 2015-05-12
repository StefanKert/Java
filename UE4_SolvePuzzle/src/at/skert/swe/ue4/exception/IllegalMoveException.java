package at.skert.swe.ue4.exception;

public class IllegalMoveException extends Exception {
  private static final long serialVersionUID = -5091795438768459755L;

  public IllegalMoveException(String message) {
    super(message);
  }

  public IllegalMoveException(String message, Throwable cause) {
    super(message, cause);
  }
}
