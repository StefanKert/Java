package at.skert.swe.ue4.implementation;

import java.util.*;
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
    Board goalBoard = BoardFactory.GetGoalBoard(board.size());
    SearchNode goalNode = new SearchNode(goalBoard);
    do {
      SearchNode currentNode = openlist.remove();
      if (currentNode.equals(goalNode))
        return currentNode.toMoves();
      
      closedlist.add(currentNode);
      expandNode(currentNode);
    } while (!openlist.isEmpty());
    throw new NoSolutionException();
  }

  private void expandNode(SearchNode currentNode) {
    for (SearchNode possibleNode : currentNode.getPossibleNodes()) {
      if (closedlist.contains(possibleNode))
        continue;
      int tentativeG = currentNode.getCostsFromStart() + 1;
      if (openlist.contains(possibleNode) && tentativeG >= possibleNode.getCostsFromStart())
        continue;

      possibleNode.setPredecessorNode(currentNode);
      possibleNode.setCostsFromStart(tentativeG);

      openlist.remove(possibleNode);
      openlist.add(possibleNode);
    }
  }
}
