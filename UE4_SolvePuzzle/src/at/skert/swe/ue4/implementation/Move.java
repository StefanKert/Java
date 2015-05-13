package at.skert.swe.ue4.implementation;

import java.util.Random;

public enum Move{
  Up,
  Down,
  Left, 
  Right;
  
  public static Move getRandomMove(){
    Random random = new Random();
    return Move.values()[random.nextInt(Move.values().length - 1)];
  }
}
