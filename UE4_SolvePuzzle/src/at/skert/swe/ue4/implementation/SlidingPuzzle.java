package at.skert.swe.ue4.implementation;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

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

    // Initialize the closed list, the open list is still empty.
    // The priority of the start node is irrelevant
    openlist.add(new SearchNode(board));

    SearchNode goalNode = new SearchNode(new Board(board.size()));

    // Loop until the best solution is found or every possible action has
    // been executed without solving the puzzle.
    do {
      // Get the node with the minimal f value
      SearchNode currentNode = openlist.remove();

      // Check if solution has been found
      if (currentNode.equals(goalNode))
        return currentNode.toMoves();

      // Don't use the current node for further actions to avoid cycles
      closedlist.add(currentNode);

      // Add the predecessors of the current node to the open list
      expandNode(currentNode);
    } while (!openlist.isEmpty());

    // Open list is empty -> no solution exists
    throw new NoSolutionException();
  }

  private void expandNode(SearchNode currentNode) {
    for (SearchNode successor : currentNode.getSuccessors()) {

      // Skip this node if it has already been checked
      if (closedlist.contains(successor))
        continue;

      int tentativeG = currentNode.getCostsFromStart() + 1;
      if (openlist.contains(successor)
          && tentativeG >= successor.getCostsFromStart())
        continue;

      successor.setPredecessorNode(currentNode);
      successor.setCostsFromStart(tentativeG);

      openlist.remove(successor);
      openlist.add(successor);
    }
  }
}
