package at.skert.swe.ue4;

import java.util.ArrayList;
import java.util.List;

import at.skert.swe.ue4.exception.InvalidBoardIndexException;
import at.skert.swe.ue4.exception.InvalidTileNumberException;

public class Board {
  private int size;
  private int board[][];
  private int emptyTileRow;
  private int emptyTileColumn;

  public Board(int size) {
    this.size = size;
    this.board = new int[size][size];

    int count = 1;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (count == size * size)
          board[i][j] = 0;
        else
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
    return this.size;
  }

  public boolean equals(Object other) {
    Board otherBoard =  (other instanceof Board) ? (Board) other : null;
    if(otherBoard == null)
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
    validateBoardValues(row, column);
    return board[row - 1][column - 1];
  }

  public void setTile(int row, int column, int number)
      throws InvalidBoardIndexException, InvalidTileNumberException {
    validateBoardValues(row, column);
    validateTileNumber(number);
    if (number == 0) {
      setEmptyTile(row, column);
    } else {
      board[row - 1][column - 1] = number;
    }
  }

  public void setEmptyTile(int row, int column)
      throws InvalidBoardIndexException {
    validateBoardValues(row, column);
    board[row - 1][column - 1] = 0;
    emptyTileRow = row;
    emptyTileColumn = column;
  }

  private void validateBoardValues(int row, int column)
      throws InvalidBoardIndexException {
    if (row < 1 || row > size || column < 1 || column > size)
      throw new InvalidBoardIndexException();
  }

  private void validateTileNumber(int number) throws InvalidTileNumberException {
    if (number < 0 || number >= size * size)
      throw new InvalidTileNumberException();
  }
}
