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
  
  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void Constructor_ShouldCreateObject() {
    Board board = new Board(4);
  }
  
  @Test
  public void Constructor_ShouldThrowExceptionForGiven0() {
    thrown.expect(IllegalArgumentException.class);
    Board board = new Board(0);
  }
  
  @Test
  public void Size_ShouldReturnSize(){
    Board board = new Board(4);
    assertEquals(4, board.size());
  }
  
  @Test
  public void Size_ShouldReturnSizeGivenInCtor(){
    int expectedSize = 5;
    Board board = new Board(expectedSize);
    assertEquals(expectedSize, board.size());
  }
  
  @Test
  public void IsValid_ShouldReturnTrueAfterInitialization(){
    Board board = new Board(4);
    assertTrue(board.isValid());
  }
  
  @Test
  public void IsValid_ShouldReturnFalseIfSameElementExistsTwice(){
    Board board = new Board(3);
    assertTrue(board.isValid());
  }
  
  @Test
  public void GetTile_ShouldReturn4() throws InvalidBoardIndexException{
    Board board = new Board(4);
    assertEquals(4, board.getTile(1,4));
  }
  
  @Test
  public void GetTile_ShouldThrowExceptionForGivenRowIsZero() throws InvalidBoardIndexException{
    Board board = new Board(4);
    thrown.expect(InvalidBoardIndexException.class);
    board.getTile(0,4);
  }
  
  @Test
  public void GetTile_ShouldThrowExceptionForGivenColumnIsZero() throws InvalidBoardIndexException{
    Board board = new Board(4);
    thrown.expect(InvalidBoardIndexException.class);
    board.getTile(1,0);
  }
 
  @Test
  public void GetTile_ShouldThrowExceptionForGivenRowIsOutOfRange() throws InvalidBoardIndexException{
    Board board = new Board(4);
    thrown.expect(InvalidBoardIndexException.class);
    board.getTile(5,1);
  }
  
  @Test
  public void GetTile_ShouldThrowExceptionForGivenColumnIsOutOfRange() throws InvalidBoardIndexException{
    Board board = new Board(4);
    thrown.expect(InvalidBoardIndexException.class);
    board.getTile(1,5);
  }

  @Test
  public void SetTile_ShouldSetTileTo4() throws InvalidBoardIndexException, InvalidTileNumberException{
    int expectedTileNumber = 4;
    Board board = new Board(4);
    board.setTile(1, 1, expectedTileNumber);
    assertEquals(expectedTileNumber, board.getTile(1, 1));
  }
  
  @Test
  public void SetTile_ShouldThrowExceptionForTileNumberOutOfRange() throws InvalidBoardIndexException, InvalidTileNumberException{
    Board board = new Board(4);
    thrown.expect(InvalidTileNumberException.class);
    board.setTile(1, 4, 16);
  }
  
  @Test
  public void SetTile_ShouldSetEmptyTileRow_AndEmptyTileColumn_For0Value() throws InvalidBoardIndexException, InvalidTileNumberException{
    Board board = new Board(4);
    board.setTile(1, 4, 0);
    assertEquals(1, board.getEmptyTileRow());
    assertEquals(4, board.getEmptyTileColumn());
  }

  @Test
  public void SetEmptyTile_ShouldSetEmptyTileToExpectedPosition() throws InvalidBoardIndexException {
    Board board = new Board(4);
    board.setEmptyTile(1, 1);
    assertEquals(0, board.getTile(1, 1));
  }

  @Test
  public void SetEmptyTile_ShouldSetEmptyTileRow_AndEmptyTileColumnToExpectedPosition() throws InvalidBoardIndexException {
    Board board = new Board(4);
    board.setEmptyTile(1, 1);
    assertEquals(1, board.getEmptyTileRow());
    assertEquals(1, board.getEmptyTileColumn());
  }

  @Test
  public void Equals_ShouldBeTrueForNewCreatedBoards(){
    Board board1 = new Board(4);
    Board board2 = new Board(4);
    assertTrue(board1.equals(board2));
  }
  
  @Test
  public void Equals_ShouldBeFalseForNewCreatedBoardAndChangedBoard() throws InvalidBoardIndexException, InvalidTileNumberException{
    Board board1 = new Board(4);
    Board board2 = new Board(4);
    board2.setTile(1, 1, 5);
    assertFalse(board1.equals(board2));
  }
  
  @Test
  public void CompareTo_ForSameSizedBoardsShouldReturn0(){
    Board board1 = new Board(4);
    Board board2 = new Board(4);
    assertEquals(0, board1.compareTo(board2));
  }
  
  @Test
  public void CompareTo_ForGreaterFirstSizedBoardShouldReturnHigher0(){
    Board board1 = new Board(5);
    Board board2 = new Board(4);
    assertTrue(0 < board1.compareTo(board2));
  }
  
  @Test
  public void CompareTo_ForSmallerFirstSizedBoardShouldReturnLower0(){
    Board board1 = new Board(4);
    Board board2 = new Board(5);
    assertTrue(0 > board1.compareTo(board2));
  }
  
  @Test
  public void Copy_ShouldReturnEqualBoardButInstanceShouldBeDifferent(){
    Board board = new Board(4);
    Board copiedBoard = board.copy();
    assertTrue(copiedBoard.equals(board));
    assertFalse(board == copiedBoard);
  }
  
  @Test
  public void Shuffle_ShouldOrderBoard() throws IllegalMoveException{
    Board board = new Board(4);
    Board beforeShuffleBoard = board.copy();
    board.shuffle();
    assertFalse(board.equals(beforeShuffleBoard));
  }
  
  @Test
  public void Move_ShouldMoveEmptyTile() throws IllegalMoveException{
    Board board = new Board(4);
    board.move(4, 3);
    assertEquals(4, board.getEmptyTileRow());
    assertEquals(3, board.getEmptyTileColumn());
  }
  
  @Test
  public void Move_ShouldThrowExceptionOnNotAllowedMove() throws IllegalMoveException{
    thrown.expect(IllegalMoveException.class);
    Board board = new Board(4);
    board.move(2, 3);
  }
  
  @Test
  public void MoveLeft_ShouldMoveEmptyTileToLeft() throws IllegalMoveException{
    Board board = new Board(4);
    board.moveLeft();
    assertEquals(4, board.getEmptyTileRow());
    assertEquals(3, board.getEmptyTileColumn());
  }
  
  @Test
  public void MoveLeft_ShouldThrowIllegalMoveExceptionIfTileIsOnTheLeftOuterBound() throws IllegalMoveException, InvalidBoardIndexException, InvalidTileNumberException{
    thrown.expect(IllegalMoveException.class);
    Board board = new Board(4);
    board.setTile(1, 1, 0);
    board.moveLeft();
  }
  
  @Test
  public void MoveRight_ShouldMoveEmptyTileToRight() throws IllegalMoveException, InvalidBoardIndexException, InvalidTileNumberException{
    Board board = new Board(4);
    board.setTile(1, 1, 0);
    board.moveRight();
    assertEquals(1, board.getEmptyTileRow());
    assertEquals(2, board.getEmptyTileColumn());
  }
  
  @Test
  public void MoveRight_ShouldThrowIllegalMoveExceptionIfTileIsOnTheRightOuterBound() throws IllegalMoveException, InvalidBoardIndexException, InvalidTileNumberException{
    thrown.expect(IllegalMoveException.class);
    Board board = new Board(4);
    board.moveRight();
  }

  @Test
  public void MoveUp_ShouldMoveEmptyTileUp() throws IllegalMoveException{
    Board board = new Board(4);
    board.moveUp();
    assertEquals(3, board.getEmptyTileRow());
    assertEquals(4, board.getEmptyTileColumn());
  }
  
  @Test
  public void MoveUp_ShouldThrowIllegalMoveExceptionIfTileIsOnTheUpperOuterBound() throws IllegalMoveException, InvalidBoardIndexException, InvalidTileNumberException{
    thrown.expect(IllegalMoveException.class);
    Board board = new Board(4);
    board.setTile(1, 1, 0);
    board.moveUp();
  }
  
  @Test
  public void MoveDown_ShouldMoveEmptyTileDown() throws IllegalMoveException, InvalidBoardIndexException, InvalidTileNumberException{
    Board board = new Board(4);
    board.setTile(1, 1, 0);
    board.moveDown();
    assertEquals(2, board.getEmptyTileRow());
    assertEquals(1, board.getEmptyTileColumn());
  }
  
  @Test
  public void MoveDown_ShouldThrowIllegalMoveExceptionIfTileIsOnTheLowerOuterBound() throws IllegalMoveException, InvalidBoardIndexException, InvalidTileNumberException{
    thrown.expect(IllegalMoveException.class);
    Board board = new Board(4);
    board.moveDown();
  }

  @Test
  public void MakeMoves_ShouldMoveTile() throws IllegalMoveException{
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
  public void MakeMoves_ShouldThrowExceptionOnIllegalMove() throws IllegalMoveException{
    thrown.expect(IllegalMoveException.class);
    Board board = new Board(4);
    List<Move> moves = new ArrayList<Move>();
    moves.add(Move.Down);
    board.makeMoves(moves);
  }
}