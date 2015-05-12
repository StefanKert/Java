package at.skert.swe.ue4;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import at.skert.swe.ue4.exception.BoardException;
import at.skert.swe.ue4.exception.IllegalMoveException;
import at.skert.swe.ue4.exception.InvalidBoardIndexException;
import at.skert.swe.ue4.exception.InvalidTileNumberException;
import at.skert.swe.ue4.implementation.Board;
import at.skert.swe.ue4.implementation.BoardFactory;
import at.skert.swe.ue4.implementation.Move;
import at.skert.swe.ue4.implementation.SearchNode;

public class SearchNodeTests {
  @Rule
  public ExpectedException thrown = ExpectedException.none();  
  
  @Test
  public void constructor_ShouldCreateObject() {
    @SuppressWarnings("unused")
    SearchNode searchNode = new SearchNode(new Board(4));
  }
  
  @Test
  public void constructor_ShouldThrowExceptionForGiven0() {
    thrown.expect(IllegalArgumentException.class);
    @SuppressWarnings("unused")
    SearchNode searchNode = new SearchNode(null);
  }
  
  @Test
  public void getBoard_ShouldReturnBoardPassedInConstructor(){
    Board board = new Board(4);
    SearchNode searchNode = new SearchNode(board);
    
    assertTrue(board == searchNode.getBoard());
  }

  @Test
  public void getPredecessor_ShouldReturnNullBeforeSetOfPredecessor(){
    Board board = new Board(4);
    SearchNode searchNode = new SearchNode(board); 
    assertTrue(null == searchNode.getPredecessor());
  }
  
  @Test
  public void getPredecessor_SetPredecessor_ShouldReturnPredecessorAfterPredecessorSets(){
    Board board = new Board(4);
    SearchNode predecessorNode = new SearchNode(board);
    SearchNode searchNode = new SearchNode(board); 
    searchNode.setPredecessorNode(predecessorNode);
    assertTrue(predecessorNode == searchNode.getPredecessor());
  }
  
  @Test
  public void getMove_ShouldReturnNullBeforeSetOfMove(){
    Board board = new Board(4);
    SearchNode searchNode = new SearchNode(board); 
    assertTrue(null == searchNode.getMove());
  }
  
  @Test
  public void getMove_SetMove_ShouldReturnMoveAfterMoveSets(){
    Board board = new Board(4);
    Move move = Move.Up;
    SearchNode searchNode = new SearchNode(board); 
    searchNode.setMove(move);
    assertTrue(move == searchNode.getMove());
  } 
  
  @Test
  public void costsFromStart_ShouldReturn0BeforeSetOfCostsFromStart(){
    Board board = new Board(4);
    SearchNode searchNode = new SearchNode(board); 
    assertEquals(0, searchNode.getCostsFromStart());
  }
  
  @Test
  public void costsFromStart_SetCostsFromStart_ShouldReturnPredecessorAfterPredecessorSets(){
    Board board = new Board(4);
    SearchNode searchNode = new SearchNode(board); 
    searchNode.setCostsFromStart(10);
    assertEquals(10, searchNode.getCostsFromStart());
  }
  
  @Test
  public void estimatedCostsToTarget_ShouldReturn0_ForGoalBoard() throws BoardException{
    Board board = BoardFactory.GetGoalBoard(4);
    SearchNode searchNode = new SearchNode(board); 
    assertEquals(0, searchNode.estimatedCostsToTarget());
  }
  
  @Test
  public void estimatedTotalCosts_ShouldReturnCostsFromStart_ForGoalBoard() throws BoardException {
    Board board = BoardFactory.GetGoalBoard(4);
    SearchNode searchNode = new SearchNode(board); 
    searchNode.setCostsFromStart(10);
    assertEquals(10, searchNode.estimatedTotalCosts());
  }
  
  @Test
  public void estimatedTotalCosts_ShouldReturnCostsFromStartWithManhattenDistance_ForBoard() throws BoardException, IllegalMoveException {
    Board board = BoardFactory.GetGoalBoard(4);
    board.moveLeft();
    SearchNode searchNode = new SearchNode(board); 
    searchNode.setCostsFromStart(10);
    assertEquals(11, searchNode.estimatedTotalCosts());
  }
  
  @Test
  public void equals_ShouldReturnTrue_IfBoardConfigurationIsEqual(){
    Board board = new Board(4);
    Board board2 = new Board(4);
    SearchNode searchNode = new SearchNode(board); 
    SearchNode searchNode2 = new SearchNode(board2); 
    assertTrue(searchNode.equals(searchNode2));
  }
  
  @Test
  public void equals_ShouldReturnFalse_IfBoardConfigurationIsNotEqual() throws InvalidBoardIndexException, InvalidTileNumberException{
    Board board = new Board(4);
    Board board2 = new Board(4);
    board.setTile(1, 1, 1);
    board2.setTile(1, 1, 2);
    SearchNode searchNode = new SearchNode(board); 
    SearchNode searchNode2 = new SearchNode(board2); 
    assertFalse(searchNode.equals(searchNode2));
  }
  
  @Test
  public void compareTo_ShouldReturn0_IfCostsAreEqual(){
    Board board = BoardFactory.GetGoalBoard(4);
    SearchNode searchNode = new SearchNode(board); 
    SearchNode searchNode2 = new SearchNode(board); 
    searchNode.setCostsFromStart(10);
    searchNode2.setCostsFromStart(10);
    assertEquals(0, searchNode.compareTo(searchNode2));
  }
  
  @Test
  public void compareTo_ShouldReturnSmaller0_IfCostsOfFirstAreSmaller(){
    Board board = BoardFactory.GetGoalBoard(4);
    SearchNode searchNode = new SearchNode(board); 
    SearchNode searchNode2 = new SearchNode(board); 
    searchNode.setCostsFromStart(10);
    searchNode2.setCostsFromStart(15);
    assertTrue(0 > searchNode.compareTo(searchNode2));
  }
  
  @Test
  public void compareTo_ShouldReturnBigger0_IfCostsOfFirstAreBigger(){
    Board board = BoardFactory.GetGoalBoard(4);
    SearchNode searchNode = new SearchNode(board); 
    SearchNode searchNode2 = new SearchNode(board); 
    searchNode.setCostsFromStart(15);
    searchNode2.setCostsFromStart(10);
    assertTrue(0 < searchNode.compareTo(searchNode2));
  }
  
  @Test
  public void toMoves_ShouldReturnNoMoves_IfNoPredecessorsAreAvailable(){
    Board goalBoard = BoardFactory.GetGoalBoard(4);
    SearchNode node = new SearchNode(goalBoard);
    List<Move> moves = node.toMoves();
    assertEquals(0, moves.size());
  }
  
  @Test
  public void toMoves_ShouldReturnMoves_IfPredecessorsAreAvailable(){
    Board goalBoard = BoardFactory.GetGoalBoard(4);
    SearchNode node = new SearchNode(goalBoard);
    SearchNode predecessor1 = new SearchNode(goalBoard);
    predecessor1.setMove(Move.Left);
    node.setPredecessorNode(predecessor1);
    List<Move> moves = node.toMoves();
    assertEquals(1, moves.size());
    assertEquals(Move.Left, moves.get(0));
  }
  
  @Test
  public void simpleNodeTest() {
    try {
      Board board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 8);
      board.setTile(3, 3, 0);     
      SearchNode node = new SearchNode(board);      
      assertEquals(0, node.estimatedCostsToTarget());
      
      board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 0);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 8);
      board.setTile(3, 3, 5);     
      node = new SearchNode(board);      
      assertEquals(2, node.estimatedCostsToTarget());

      board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 0);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 8);
      board.setTile(3, 3, 2);     
      node = new SearchNode(board);      
      assertEquals(3, node.estimatedCostsToTarget());
    }
    catch (BoardException e) {
      fail("Unexpeced BoardException.");
    }
  }
}
