package at.skert.swe.ue4.implementation;

import at.skert.swe.ue4.exception.IllegalMoveException;
import at.skert.swe.ue4.exception.NoSolutionException;

public class Main {
  public static void main(String[] args){
    try {
      SlidingPuzzle puzzle = new SlidingPuzzle();
      Board board = Board.CreateGoalBoard(3);
      board.shuffle();
      puzzle.printMoves(board, puzzle.solve(board));
    }catch(IllegalMoveException ex){
      ex.printStackTrace();
    } catch (NoSolutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
