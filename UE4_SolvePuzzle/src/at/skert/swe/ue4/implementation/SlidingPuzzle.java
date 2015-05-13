package at.skert.swe.ue4.implementation;

import java.util.*;

import at.skert.swe.ue4.exception.IllegalMoveException;
import at.skert.swe.ue4.exception.NoSolutionException;

public class SlidingPuzzle {
  private HashSet<SearchNode> closedlist;
  private PriorityQueue<SearchNode> openlist;

  public SlidingPuzzle() {
    closedlist = new HashSet<SearchNode>();
    openlist = new PriorityQueue<SearchNode>();
  }

  public List<Move> solve(Board board) throws NoSolutionException {
    closedlist.clear();
    openlist.clear();
    openlist.add(new SearchNode(board));
    Board goalBoard = Board.CreateGoalBoard(board.size());
    SearchNode goalNode = new SearchNode(goalBoard);
    do {
      SearchNode currentNode = openlist.remove();
      if (currentNode.equals(goalNode))
        return currentNode.toMoves(); // Here we reached our goalNode
      closedlist.add(currentNode);
      expandNode(currentNode);
    } while (!openlist.isEmpty());
    throw new NoSolutionException();
  }

  private void expandNode(SearchNode currentNode) {
    for (SearchNode possibleNode : currentNode.getPossibleNodes()) {
      if (closedlist.contains(possibleNode))
        continue; // If possibleNode is already available in cosedList we can ignore it.
      int expectedCosts = currentNode.getCostsFromStart() + 1;
      if (openlist.contains(possibleNode) && expectedCosts >= possibleNode.getCostsFromStart())
        continue; //If the node is available in the openList but the available node is cheaper than the possiblenode

      possibleNode.setPredecessorNode(currentNode);
      possibleNode.setCostsFromStart(expectedCosts);

      openlist.remove(possibleNode);
      openlist.add(possibleNode);
    }
  }

  
  public void printMoves(Board board, List<Move> moves) {
    try{
      for(Move move: moves){
        board.move(move);
        System.out.println(board.toString());
        System.out.println("--------");
      }
    }catch (IllegalMoveException e) {
      System.err.println(e.getMessage());
    }
  }
}
