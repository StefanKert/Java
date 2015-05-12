package at.skert.swe.ue4;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.junit.rules.ExpectedException;

import at.skert.swe.ue4.exception.*;
import at.skert.swe.ue4.implementation.*;

public class BoardTests {
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  
  @Test
  public void constructor_ShouldCreateObject() {
    @SuppressWarnings("unused")
    Board board = new Board(4);
  }
  
  @Test
  public void constructor_ShouldThrowExceptionForGiven0() {
    thrown.expect(IllegalArgumentException.class);
    @SuppressWarnings("unused")
    Board board = new Board(0);
  }
  
  @Test
  public void size_ShouldReturnSize(){
    Board board = new Board(4);
    assertEquals(4, board.size());
  }
  
  @Test
  public void size_ShouldReturnSizeGivenInCtor(){
    int expectedSize = 5;
    Board board = new Board(expectedSize);
    assertEquals(expectedSize, board.size());
  }
  
  @Test
  public void isValid_ShouldReturnTrueAfterInitialization(){
    Board board = new Board(4);
    assertTrue(board.isValid());
  }
  
  @Test
  public void isValid_ShouldReturnFalseIfSameElementExistsTwice(){
    Board board = new Board(3);
    assertTrue(board.isValid());
  }
  
  @Test
  public void getTile_ShouldReturn4() throws InvalidBoardIndexException{
    Board board = new Board(4);
    assertEquals(4, board.getTile(1,4));
  }
  
  @Test
  public void getTile_ShouldThrowExceptionForGivenRowIsZero() throws InvalidBoardIndexException{
    Board board = new Board(4);
    thrown.expect(InvalidBoardIndexException.class);
    board.getTile(0,4);
  }
  
  @Test
  public void getTile_ShouldThrowExceptionForGivenColumnIsZero() throws InvalidBoardIndexException{
    Board board = new Board(4);
    thrown.expect(InvalidBoardIndexException.class);
    board.getTile(1,0);
  }
 
  @Test
  public void getTile_ShouldThrowExceptionForGivenRowIsOutOfRange() throws InvalidBoardIndexException{
    Board board = new Board(4);
    thrown.expect(InvalidBoardIndexException.class);
    board.getTile(5,1);
  }
  
  @Test
  public void getTile_ShouldThrowExceptionForGivenColumnIsOutOfRange() throws InvalidBoardIndexException{
    Board board = new Board(4);
    thrown.expect(InvalidBoardIndexException.class);
    board.getTile(1,5);
  }

  @Test
  public void setTile_ShouldSetTileTo4() throws InvalidBoardIndexException, InvalidTileNumberException{
    int expectedTileNumber = 4;
    Board board = new Board(4);
    board.setTile(1, 1, expectedTileNumber);
    assertEquals(expectedTileNumber, board.getTile(1, 1));
  }
  
  @Test
  public void setTile_ShouldThrowExceptionForTileNumberOutOfRange() throws InvalidBoardIndexException, InvalidTileNumberException{
    Board board = new Board(4);
    thrown.expect(InvalidTileNumberException.class);
    board.setTile(1, 4, 16);
  }
  
  @Test
  public void setTile_ShouldSetEmptyTileRow_AndEmptyTileColumn_For0Value() throws InvalidBoardIndexException, InvalidTileNumberException{
    Board board = new Board(4);
    board.setTile(1, 4, 0);
    assertEquals(1, board.getEmptyTileRow());
    assertEquals(4, board.getEmptyTileColumn());
  }

  @Test
  public void setEmptyTile_ShouldSetEmptyTileToExpectedPosition() throws InvalidBoardIndexException {
    Board board = new Board(4);
    board.setEmptyTile(1, 1);
    assertEquals(0, board.getTile(1, 1));
  }

  @Test
  public void setEmptyTile_ShouldSetEmptyTileRow_AndEmptyTileColumnToExpectedPosition() throws InvalidBoardIndexException {
    Board board = new Board(4);
    board.setEmptyTile(1, 1);
    assertEquals(1, board.getEmptyTileRow());
    assertEquals(1, board.getEmptyTileColumn());
  }

  @Test
  public void equals_ShouldBeTrueForNewCreatedBoards(){
    Board board1 = new Board(4);
    Board board2 = new Board(4);
    assertTrue(board1.equals(board2));
  }
  
  @Test
  public void equals_ShouldBeFalseForNewCreatedBoardAndChangedBoard() throws InvalidBoardIndexException, InvalidTileNumberException{
    Board board1 = new Board(4);
    Board board2 = new Board(4);
    board2.setTile(1, 1, 5);
    assertFalse(board1.equals(board2));
  }
  
  @Test
  public void compareTo_ForSameSizedBoardsShouldReturn0(){
    Board board1 = new Board(4);
    Board board2 = new Board(4);
    assertEquals(0, board1.compareTo(board2));
  }
  
  @Test
  public void compareTo_ForGreaterFirstSizedBoardShouldReturnHigher0(){
    Board board1 = new Board(5);
    Board board2 = new Board(4);
    assertTrue(0 < board1.compareTo(board2));
  }
  
  @Test
  public void compareTo_ForSmallerFirstSizedBoardShouldReturnLower0(){
    Board board1 = new Board(4);
    Board board2 = new Board(5);
    assertTrue(0 > board1.compareTo(board2));
  }
  
  @Test
  public void copy_ShouldReturnEqualBoardButInstanceShouldBeDifferent(){
    Board board = new Board(4);
    Board copiedBoard = board.copy();
    assertTrue(copiedBoard.equals(board));
    assertFalse(board == copiedBoard);
  }
  
  @Test
  public void shuffle_ShouldOrderBoard() throws IllegalMoveException{
    Board board = new Board(4);
    Board beforeShuffleBoard = board.copy();
    board.shuffle();
    assertFalse(board.equals(beforeShuffleBoard));
  }
  
  @Test
  public void move_ShouldMoveEmptyTile() throws IllegalMoveException{
    Board board = new Board(4);
    board.move(4, 3);
    assertEquals(4, board.getEmptyTileRow());
    assertEquals(3, board.getEmptyTileColumn());
  }
  
  @Test
  public void move_ShouldThrowExceptionOnNotAllowedMove() throws IllegalMoveException{
    thrown.expect(IllegalMoveException.class);
    Board board = new Board(4);
    board.move(2, 3);
  }
  
  @Test
  public void moveLeft_ShouldMoveEmptyTileToLeft() throws IllegalMoveException{
    Board board = new Board(4);
    board.moveLeft();
    assertEquals(4, board.getEmptyTileRow());
    assertEquals(3, board.getEmptyTileColumn());
  }
  
  @Test
  public void moveLeft_ShouldThrowIllegalMoveExceptionIfTileIsOnTheLeftOuterBound() throws IllegalMoveException, InvalidBoardIndexException, InvalidTileNumberException{
    thrown.expect(IllegalMoveException.class);
    Board board = new Board(4);
    board.setTile(1, 1, 0);
    board.moveLeft();
  }
  
  @Test
  public void moveRight_ShouldMoveEmptyTileToRight() throws IllegalMoveException, InvalidBoardIndexException, InvalidTileNumberException{
    Board board = new Board(4);
    board.setTile(1, 1, 0);
    board.moveRight();
    assertEquals(1, board.getEmptyTileRow());
    assertEquals(2, board.getEmptyTileColumn());
  }
  
  @Test
  public void moveRight_ShouldThrowIllegalMoveExceptionIfTileIsOnTheRightOuterBound() throws IllegalMoveException, InvalidBoardIndexException, InvalidTileNumberException{
    thrown.expect(IllegalMoveException.class);
    Board board = new Board(4);
    board.moveRight();
  }

  @Test
  public void moveUp_ShouldMoveEmptyTileUp() throws IllegalMoveException{
    Board board = new Board(4);
    board.moveUp();
    assertEquals(3, board.getEmptyTileRow());
    assertEquals(4, board.getEmptyTileColumn());
  }
  
  @Test
  public void moveUp_ShouldThrowIllegalMoveExceptionIfTileIsOnTheUpperOuterBound() throws IllegalMoveException, InvalidBoardIndexException, InvalidTileNumberException{
    thrown.expect(IllegalMoveException.class);
    Board board = new Board(4);
    board.setTile(1, 1, 0);
    board.moveUp();
  }
  
  @Test
  public void moveDown_ShouldMoveEmptyTileDown() throws IllegalMoveException, InvalidBoardIndexException, InvalidTileNumberException{
    Board board = new Board(4);
    board.setTile(1, 1, 0);
    board.moveDown();
    assertEquals(2, board.getEmptyTileRow());
    assertEquals(1, board.getEmptyTileColumn());
  }
  
  @Test
  public void moveDown_ShouldThrowIllegalMoveExceptionIfTileIsOnTheLowerOuterBound() throws IllegalMoveException, InvalidBoardIndexException, InvalidTileNumberException{
    thrown.expect(IllegalMoveException.class);
    Board board = new Board(4);
    board.moveDown();
  }

  @Test
  public void makeMoves_ShouldMoveTile() throws IllegalMoveException{
    Board board = new Board(4);
    List<Move> moves = new ArrayList<Move>();
    moves.add(Move.Up);
    moves.add(Move.Up);
    moves.add(Move.Down);
    moves.add(Move.Left);
    moves.add(Move.Left);
    moves.add(Move.Right);
    board.makeMoves(moves);
    assertEquals(3, board.getEmptyTileColumn());
    assertEquals(3, board.getEmptyTileRow());
  }

  @Test
  public void makeMoves_ShouldThrowExceptionOnIllegalMove() throws IllegalMoveException{
    thrown.expect(IllegalMoveException.class);
    Board board = new Board(4);
    List<Move> moves = new ArrayList<Move>();
    moves.add(Move.Down);
    board.makeMoves(moves);
  }
  
  @Test
  public void getTilePointByNumber_ShouldReturn_Point() throws InvalidBoardIndexException, InvalidTileNumberException{
    Board board = new Board(4);
    board.setTile(1, 1, 5);
    Point point = board.getTilePointByNumber(5);
    assertEquals(1, point.getRow());
    assertEquals(1, point.getColumn());
  }
  
  @Test
  public void getTilePointByNumber_ShouldThrowExceptionOnNotAvailabelNumber() throws InvalidBoardIndexException, InvalidTileNumberException{
    thrown.expect(InvalidTileNumberException.class);
    Board board = new Board(4);
    board.getTilePointByNumber(100);
  }
  
  @Test
  public void getManhattenDistanceForBoard_ShouldReturn0_OnGoalBoard() throws InvalidTileNumberException, InvalidBoardIndexException {
    Board board = BoardFactory.GetGoalBoard(4);
    assertEquals(0, board.getManhattenDistanceForBoard());
  }
  
  @Test
  public void getManhattenDistanceForBoard_ShouldReturn1_OnMovedBoard() throws InvalidTileNumberException, InvalidBoardIndexException, IllegalMoveException {
    Board board = BoardFactory.GetGoalBoard(4);
    board.moveLeft();
    assertEquals(1, board.getManhattenDistanceForBoard());
  } 
  
  @Test
  public void isMoveValid_ShouldReturnFalseOnMoveRight(){
    Board board = BoardFactory.GetGoalBoard(4);
    assertFalse(board.isMoveValid(Move.Right));
  }
  
  @Test
  public void isMoveValid_ShouldReturnTrueOnMoveLeft(){
    Board board = BoardFactory.GetGoalBoard(4);
    assertTrue(board.isMoveValid(Move.Left));
  }

  @Test
  public void isMoveValid_ShouldReturnFalseIfValuesExceedBoardRange(){
    Board board = BoardFactory.GetGoalBoard(3);
    assertFalse(board.isMoveValid(100,1));
    assertFalse(board.isMoveValid(1,100));
    assertFalse(board.isMoveValid(0,1));
    assertFalse(board.isMoveValid(1,0));
  }
  
  @Test
  public void isMoveValid_ShouldReturnTrueIfMoveIsValid(){
    Board board = BoardFactory.GetGoalBoard(3);
    assertTrue(board.isMoveValid(3,2));
    assertTrue(board.isMoveValid(2,3));
  }
  
  @Test
  public void isMoveValid_ShouldReturnTrueIfMoveIsNotAllowed(){
    Board board = BoardFactory.GetGoalBoard(3);
    assertFalse(board.isMoveValid(2,2));
  }
  
  @Test
  public void simpleIsValidTest() {
    Board board;
    try {
      board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 8);
      board.setTile(3, 3, 0);

      assertTrue(board.isValid());
    }
    catch (BoardException e) {
      fail("BoardException not expected.");
    } 
  }

  @Test
  public void simpleIsNotValidTest() {
    Board board;
    try {
      board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 1);
      board.setTile(3, 3, 0);

      assertTrue(! board.isValid());
    }
    catch (BoardException e) {
      fail("BoardException not expected.");
    } 
  }

  @Test
  public void simpleIsNotValidTest2() {
    Board board;
    try {
      board = new Board(3);      
      board.setTile(1, 1, 8);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 0);
      board.setTile(2, 1, 7);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 4);
      board.setTile(3, 1, 3);
      board.setTile(3, 2, 1);
      board.setTile(3, 3, 6);

      assertTrue(board.isValid());
    }
    catch (BoardException e) {
      fail("BoardException not expected.");
    } 
  }
}