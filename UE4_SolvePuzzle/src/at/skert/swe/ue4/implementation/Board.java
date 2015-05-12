package at.skert.swe.ue4.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import at.skert.swe.ue4.exception.*;

public class Board implements Comparable<Board> {
  private int size;
  private int board[][];
  private int emptyTileRow;
  private int emptyTileColumn;

  public Board(int size) {
    if (size == 0)
      throw new IllegalArgumentException("You need to pass a value > 0.");

    this.size = size;
    this.board = new int[size][size];

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
    ArrayList<Integer> mustContain = new ArrayList<Integer>();
    for (int i = 0; i < size * size; i++) {
      mustContain.add(i);
    }
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (!mustContain.contains(board[i][j]))
          return false;
        mustContain.remove(mustContain.indexOf(board[i][j]));
      }
    }
    return mustContain.isEmpty();
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

  public Point getTilePointByNumber(int number)
      throws InvalidTileNumberException {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        // Ignore 0 tile
        if (board[i][j] == number)
          return new Point(i + 1, j + 1); // We need to add 1 because our board
                                          // is 1 based configuration
      }
    }
    // If we reach this point the Tile Number doesn´t exist in our board
    throw new InvalidTileNumberException();
  }

  public void setEmptyTile(int row, int column)
      throws InvalidBoardIndexException {
    if (!areBoardValuesValid(row, column))
      throw new InvalidBoardIndexException();
    board[row - 1][column - 1] = 0;
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
    Random random = new Random();
    for (int i = 0; i < 100; i++) {
      do {
        move = Move.values()[random.nextInt(3)];
      } while (!isMoveValid(move));
      move(move);
    }
  }

  public void move(int row, int column) throws IllegalMoveException {
    try {
      if (!isMoveValid(row, column))
        throw new IllegalMoveException("The move to row " + row
            + " and to column " + column + " was not a valid move.");
      setTile(emptyTileRow, emptyTileColumn, getTile(row, column));
      setEmptyTile(row, column);
    } catch (InvalidBoardIndexException invalidBoardIndexException) {
      throw new IllegalMoveException("Move was out of Board index Range.",
          invalidBoardIndexException);
    } catch (InvalidTileNumberException invalidTileNumberException) {
      throw new IllegalMoveException("The tile contains a invalid number.",
          invalidTileNumberException);
    }
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
    if ((Math.abs(column - emptyTileColumn) == 1 && row == emptyTileRow)
        || (Math.abs(row - emptyTileRow) == 1 && column == emptyTileColumn))
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
      return emptyTileRow;
  }

  private int getTargetColumnForMove(Move move) {
    if (move == Move.Left)
      return emptyTileColumn - 1;
    else if (move == Move.Right)
      return emptyTileColumn + 1;
    else
      return emptyTileColumn;
  }

  public void move(Move move) throws IllegalMoveException {
    move(getTargetRowForMove(move), getTargetColumnForMove(move));
  }

  public int getManhattenDistanceForBoard() throws InvalidTileNumberException,
      InvalidBoardIndexException {
    int costs = 0;

    Board goalBoard = BoardFactory.GetGoalBoard(this.size);

    // Iterate over all tiles and calculate their manhatten distances
    for (int row = 1; row <= size; row++) {
      for (int column = 1; column <= size; column++) {
        // Ignore 0 tile
        int number = getTile(row, column);
        if (number != 0) {
          Point goal = goalBoard.getTilePointByNumber(number);
          costs += Math.abs(row - goal.getRow())
              + Math.abs(column - goal.getColumn());
        }
      }
    }
    return costs;
  }
}