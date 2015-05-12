package at.skert.swe.ue4.implementation;

public final class BoardFactory {
  public static Board GetGoalBoard(int size) {
    return new Board(size);
  }
}
