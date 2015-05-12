package at.skert.swe.ue4.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.skert.swe.ue4.implementation.Move;
import at.skert.swe.ue4.exception.BoardException;
import at.skert.swe.ue4.exception.IllegalMoveException;

public class SearchNode implements Comparable<SearchNode> {
  private Board board;
  private SearchNode predecessor;
  private int costsFromStart;
  private Move move;

  public SearchNode(Board board) {
    if (board == null)
      throw new IllegalArgumentException("The board mustn´t be null.");
    this.board = board;
  }

  public Board getBoard() {
    return board;
  }

  public SearchNode getPredecessor() {
    return predecessor;
  }

  public void setPredecessorNode(SearchNode predecessor) {
    this.predecessor = predecessor;
  }

  public int getCostsFromStart() {
    return costsFromStart;
  }

  public void setCostsFromStart(int costsFromStart) {
    this.costsFromStart = costsFromStart;
  }

  public Move getMove() {
    return move;
  }

  public void setMove(Move move) {
    this.move = move;
  }

  @Override
  public boolean equals(Object other) {
    SearchNode otherNode = (other instanceof SearchNode) ? (SearchNode) other
        : null;
    if (otherNode == null)
      return false;

    return board.equals(otherNode.getBoard());
  }

  public int estimatedCostsToTarget() throws BoardException {
    return board.getManhattenDistanceForBoard();
  }

  @Override
  public int compareTo(SearchNode other) {
    try {
      return this.estimatedTotalCosts() - other.estimatedTotalCosts();
    } catch (BoardException e) {
      throw new RuntimeException(
          "There happend an unexpected error. There is a bug in the Board Methods.",
          e);
    }
  }

  public int estimatedTotalCosts() throws BoardException {
    return estimatedCostsToTarget() + costsFromStart;
  }

  public List<Move> toMoves() {
    ArrayList<Move> moves = new ArrayList<Move>();
    SearchNode pre = this;
    do {
      if(pre.move != null)
        moves.add(pre.move);
    } while ((pre = pre.getPredecessor()) != null);
    Collections.reverse(moves);
    return moves;
  }

  public List<SearchNode> getSuccessors() {
    ArrayList<SearchNode> nodes = new ArrayList<SearchNode>();
    addSearchNodesForValidMoves(nodes, Move.Up);
    addSearchNodesForValidMoves(nodes, Move.Down);
    addSearchNodesForValidMoves(nodes, Move.Left);
    addSearchNodesForValidMoves(nodes, Move.Right);
    return nodes;
  }

  public void addSearchNodesForValidMoves(List<SearchNode> nodes, Move move) {
    try {
      if (board.isMoveValid(move)) {
        Board newBoard = board.copy();
        newBoard.move(move);
        SearchNode newNode = new SearchNode(newBoard);
        newNode.setMove(move);
        nodes.add(newNode);
      }
    } catch (IllegalMoveException e) {
      // This should not happen because we always check if move is valid before
      // we proceed
    }
  }
}
