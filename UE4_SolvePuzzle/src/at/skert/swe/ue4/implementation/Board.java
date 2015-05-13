package at.skert.swe.ue4.implementation;

import java.util.*;
import at.skert.swe.ue4.exception.*;

public class Board implements Comparable<Board> {
  private static final int SHUFFLE_COUNT = 100;
  private int size;
  private int board[][];
  private int emptyTileRow;
  private int emptyTileColumn;

  //Factory method for getting finished Board. All the tiles are at the goal position.
  public static Board CreateGoalBoard(int size) {
    Board board = new Board(size);
    board.setBoardFinished();
    return board;
  }
  //Factory method for getting empty board. This method returns a Board with no Tiles set
  public static Board CreateEmptyBoard(int size) {
    return new Board(size);
  }
 
  private Board(int size) {
    if (size == 0)
      throw new IllegalArgumentException("You need to pass a value > 0.");

    this.size = size;
    this.board = new int[size][size];
  }
  
  private void setBoardFinished(){
    int count = 1;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (count == size * size) {
          board[i][j] = 0;
          emptyTileRow = i + 1;
          emptyTileColumn = j + 1; // We need to set these values to +1 because
                                   // we have a 1 based board.
        } else
          board[i][j] = count++;
      }
    }
  }

  public int getEmptyTileRow() {
    return emptyTileRow;
  }

  public int getEmptyTileColumn() {
    return emptyTileColumn;
  }

  public int size() {
    return size;
  }

  @Override
  public boolean equals(Object other) {
    Board otherBoard = (other instanceof Board) ? (Board) other : null;
    if (otherBoard == null)
      return false;

    if (this.compareTo(otherBoard) != 0) 
      return false;

    //Perform check for each element in the boards for equality
    for (int i = 0; i < this.size; i++) {
      for (int j = 0; j < this.size; j++) {
        if (this.board[i][j] != otherBoard.board[i][j])
          return false;
      }
    }

    return true;
  }

  @Override
  public int compareTo(Board other) {
    return this.size() - other.size();
  }

  public boolean isValid() {
    ArrayList<Integer> boardTiles = new ArrayList<Integer>();
    for (int i = 0; i < size * size; i++) { // generates the valid entries for the board
      boardTiles.add(i);
    }
    // We check if the element exists in the board. If yes we remove it and if there are no more Elements the board is valid
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (!boardTiles.contains(board[i][j]))
          return false;
        boardTiles.remove(boardTiles.indexOf(board[i][j]));
      }
    }
    return boardTiles.isEmpty();
  }

  public int getTile(int row, int column) throws InvalidBoardIndexException {
    if (!areBoardValuesValid(row, column))
      throw new InvalidBoardIndexException();
    return board[row - 1][column - 1];
  }

  public void setTile(int row, int column, int number)
      throws InvalidBoardIndexException, InvalidTileNumberException {
    if (!areBoardValuesValid(row, column))
      throw new InvalidBoardIndexException();
    if (!isTileNumberValid(number))
      throw new InvalidTileNumberException();
    if (number == 0) {
      setEmptyTile(row, column);
    } else {
      board[row - 1][column - 1] = number;
    }
  }

  public Position getTilePositionByNumber(int number)
      throws InvalidTileNumberException {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        // Ignore 0 tile
        if (board[i][j] == number)
          return new Position(i + 1, j + 1); // We need to add 1 because our board
                                          // is 1 based configuration
      }
    }
    // If we reach this point the Tile Number does not exist in our board
    throw new InvalidTileNumberException();
  }

  public void setEmptyTile(int row, int column)
      throws InvalidBoardIndexException {
    if (!areBoardValuesValid(row, column))
      throw new InvalidBoardIndexException();
    board[row - 1][column - 1] = 0; // We need to substract 1 because we get the row and the column 1 based and our array is zero based
    emptyTileRow = row;
    emptyTileColumn = column;
  }

  public Board copy() {
    Board b = new Board(this.size);
    b.emptyTileColumn = this.emptyTileColumn;
    b.emptyTileRow = this.emptyTileRow;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        b.board[i][j] = this.board[i][j];
      }
    }
    return b;
  }

  public void shuffle() throws IllegalMoveException {
    Move move;
    for (int i = 0; i < SHUFFLE_COUNT; i++) {
      do {
        move = Move.getRandomMove();
      } while (!isMoveValid(move));
      move(move);
    }
  }

  // This method is just a wrapper for the tryMove method to get rid of those try-catch things to concentrate on the logic
  public void move(int row, int column) throws IllegalMoveException {
    try {
      tryMove(row, column);
    } catch (InvalidBoardIndexException invalidBoardIndexException) {
      throw new IllegalMoveException("Move was out of Board index Range.",
          invalidBoardIndexException);
    } catch (InvalidTileNumberException invalidTileNumberException) {
      throw new IllegalMoveException("The tile contains a invalid number.",
          invalidTileNumberException);
    }
  }
  
  // This method contains the logic for the move Method. 
  private void tryMove(int row, int column) throws IllegalMoveException, InvalidBoardIndexException, InvalidTileNumberException {
    if (!isMoveValid(row, column))
      throw new IllegalMoveException("The move to row " + row + " and to column " + column + " was not a valid move.");
    setTile(emptyTileRow, emptyTileColumn, getTile(row, column));
    setEmptyTile(row, column);
  }

  public void moveLeft() throws IllegalMoveException {
    move(Move.Left);
  }

  public void moveRight() throws IllegalMoveException {
    move(Move.Right);
  }

  public void moveUp() throws IllegalMoveException {
    move(Move.Up);
  }

  public void moveDown() throws IllegalMoveException {
    move(Move.Down);
  }

  public void makeMoves(List<Move> moves) throws IllegalMoveException {
    for (Move move : moves) {
      move(move);
    }
  }

  private boolean areBoardValuesValid(int row, int column) {
    if (row < 1 || row > size || column < 1 || column > size)
      return false;
    return true;
  }

  private boolean isTileNumberValid(int number) {
    if (number < 0 || number >= size * size)
      return false;
    return true;
  }

  public boolean isMoveValid(int row, int column) {
    if (!areBoardValuesValid(row, column))
      return false;
    
    boolean isHorizontalMoveAllowed = Math.abs(column - emptyTileColumn) == 1 && row == emptyTileRow;
    boolean isVerticalMoveAllowed = Math.abs(row - emptyTileRow) == 1 && column == emptyTileColumn;
    if (isHorizontalMoveAllowed || isVerticalMoveAllowed)
      return true;
    return false;
  }

  public boolean isMoveValid(Move move) {
    int targetRow = getTargetRowForMove(move);
    int targetColumn = getTargetColumnForMove(move);
    return isMoveValid(targetRow, targetColumn);
  }

  private int getTargetRowForMove(Move move) {
    if (move == Move.Down)
      return emptyTileRow + 1;
    else if (move == Move.Up)
      return emptyTileRow - 1;
    else
      return emptyTileRow; // We can return the row if a other move is applied because when we Move left or right the row is not changed
  }

  private int getTargetColumnForMove(Move move) {
    if (move == Move.Left)
      return emptyTileColumn - 1;
    else if (move == Move.Right)
      return emptyTileColumn + 1;
    else
      return emptyTileColumn; // We can return the column if a other move is applied because when we Move up or down the column is not changed
  }
  
  public void move(Move move) throws IllegalMoveException {
    move(getTargetRowForMove(move), getTargetColumnForMove(move));
  }

  public int getManhattenDistanceForBoard() throws InvalidTileNumberException, InvalidBoardIndexException {
    int costs = 0;

    Board goalBoard = Board.CreateGoalBoard(this.size);
    for (int row = 1; row <= size; row++) { // We need to start with 1 because we use the getTile Method that is 1 based
      for (int column = 1; column <= size; column++) {
        int number = getTile(row, column);
        if (number != 0) { // If number is 0 we do not need to calculate the costs
          Position goal = goalBoard.getTilePositionByNumber(number);
          costs += Math.abs(row - goal.getRow()) + Math.abs(column - goal.getColumn()); // Add difference to goal Position to costs
        }
      }
    }
    return costs;
  }
  
  @Override
  public String toString(){
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
          builder.append(board[i][j]);
          builder.append(" ");
      }
      builder.append("\n");
    }
    return builder.toString(); 
  }
}