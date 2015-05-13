package at.skert.swe.ue4;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import at.skert.swe.ue4.exception.BoardException;
import at.skert.swe.ue4.exception.IllegalMoveException;
import at.skert.swe.ue4.exception.InvalidBoardIndexException;
import at.skert.swe.ue4.exception.InvalidTileNumberException;
import at.skert.swe.ue4.exception.NoSolutionException;
import at.skert.swe.ue4.implementation.Board;
import at.skert.swe.ue4.implementation.Move;
import at.skert.swe.ue4.implementation.SlidingPuzzle;

public class SlidingPuzzleSolverTest {

  @Test
  public void solveSimplePuzzleTest1() throws InvalidBoardIndexException, InvalidTileNumberException {
    try {
      SlidingPuzzle solver = new SlidingPuzzle();
      Board board = Board.CreateGoalBoard(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 0);
      board.setTile(3, 3, 8);
    
      List<Move> moves = solver.solve(board);
      assertEquals(1, moves.size());
      assertEquals(Move.Right, moves.get(0));
    }
    catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }

  
  @Test
  public void solveSimplePuzzleTest2() throws InvalidBoardIndexException, InvalidTileNumberException {
    try {
      SlidingPuzzle solver = new SlidingPuzzle();
      Board board = Board.CreateGoalBoard(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 0);
      board.setTile(3, 2, 7);
      board.setTile(3, 3, 8);
    
      List<Move> moves = solver.solve(board);
      assertEquals(2, moves.size());
      assertTrue(moves.get(0) == Move.Right);
      assertTrue(moves.get(1) == Move.Right);
    }
    catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }

  @Test
  public void solveComplexPuzzleTest1() throws BoardException, IllegalMoveException {

    try {
      SlidingPuzzle solver = new SlidingPuzzle();

      //  8  2  7 
      //  1  4  6 
      //  3  5  X 
      Board board = Board.CreateGoalBoard(3);      
      board.setTile(1, 1, 8);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 7);
      board.setTile(2, 1, 1);
      board.setTile(2, 2, 4);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 3);
      board.setTile(3, 2, 5);
      board.setTile(3, 3, 0);
    
      List<Move> moves = solver.solve(board);
      board.makeMoves(moves);
      assertEquals(Board.CreateGoalBoard(3), board);
    }
    catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }

  @Test
  public void solveRandomPuzzlesTest() throws BoardException, InvalidTileNumberException, IllegalMoveException {
    SlidingPuzzle solver = new SlidingPuzzle();

    for (int k = 0; k < 50; k++) {
      try {
        Board board = Board.CreateGoalBoard(3);
        int n = 1;
        int maxN = board.size() * board.size();
        for (int i = 1; i <= board.size(); i++)
          for (int j = 1; j <= board.size(); j++)
            board.setTile(i, j, (n++) % maxN);

        board.shuffle();
                
        List<Move> moves = solver.solve(board);
        board.makeMoves(moves);
        assertEquals(Board.CreateGoalBoard(3), board);
      }
      catch (NoSolutionException nse) {
        fail("NoSolutionException is not expected.");
      }
    }
  }
  
  @Test
  public void solveSimplePuzzleTest_4x4() throws IllegalMoveException {
    try {
      SlidingPuzzle solver = new SlidingPuzzle();
      Board board = Board.CreateGoalBoard(4);      

      board.moveLeft();
      
      List<Move> moves = solver.solve(board);
      assertEquals(1, moves.size());
      assertEquals(moves.get(0), Move.Right);
    }
    catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }

  @Test
  public void solveComplexPuzzleTest_4x4() throws IllegalMoveException {
    try {
      SlidingPuzzle solver = new SlidingPuzzle();
      Board board = Board.CreateGoalBoard(4);      

      board.moveLeft();
      board.moveLeft();
      board.moveUp();
      board.moveLeft();
      board.moveUp();
      board.moveUp();
      board.moveRight();
      board.moveDown();
      board.moveLeft();
      
      List<Move> moves = solver.solve(board);
      board.makeMoves(moves);
      assertEquals(Board.CreateGoalBoard(4), board);
    }
    catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }
}
