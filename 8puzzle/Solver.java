import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 * The Solver data type implements A* search to solve n-by-n
 * slider puzzles.
 * 
 * @author Karthikeyan
 */
public class Solver {
    private Node solutionNode;  
    
    /**
     * Finds a solution to the initial board using the A* algorithm.
     * @param initial initial board of the game.
     * @throws IllegalArgumentException if the initial board is null
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Null arguments not allowed.");
        }
        MinPQ<Node> q = new MinPQ<>();
        q.insert(new Node(initial, null, 0));

        while (!q.min().board.isGoal() && !isUnsolvable(q.min())) {
            Node currNode = q.delMin();
            Node prevNode = currNode.prev;

            for (Board neighbor : currNode.board.neighbors()) {
                if (prevNode != null && prevNode.board.equals(neighbor)) {
                    continue;
                }
                q.insert(new Node(neighbor, currNode, currNode.moves + 1));
            }
        }
        solutionNode = q.delMin();
    }

    /**
     * Returns true if the initial board is solved; false otherwise.
     * @return true if the initial board is solved; false otherwise.
     */
    public boolean isSolvable() {
        return solutionNode.board.isGoal();
    }

    /**
     * Returns the number of moves made to reach the goal board if the
     * initial board is solved; -1 otherwise.
     * @return the number of moves made to reach the goal board; -1 if
     * unsolvable
     */
    public int moves() {
        return isSolvable() ? solutionNode.moves : -1;
    }

    /**
     * Returns the sequence of boards in a shortest solution; null if 
     * solvable.
     * @return returns the sequence of boards in a shorted solution; null
     * if solvable
     */
    public Iterable<Board> solution() {
        if (!isSolvable())  return null;
        LinkedList<Board> boards = new LinkedList<>();

        for (Node node = solutionNode; node != null; node = node.prev) {
            boards.addFirst(node.board);
        }
        return boards;
    }
    
    // Returns true if the board is unsolvable
    private boolean isUnsolvable(Node node) {
        return node.board.twin().isGoal();
    }
    
    // search node of the game 
    private class Node implements Comparable<Node> {
        private Node prev;
        private Board board;
        private int moves;
        private int manhattan = -1;

        Node(Board board, Node prev, int moves) {
            this.board = board;
            this.prev  = prev;
            this.moves = moves;
            this.manhattan = board.manhattan();
        }

        public int compareTo(Node that) {
            return this.priority() - that.priority();
        }

        private int priority() {
            return manhattan + moves;
        }
    }


    // test client
    public static void main(String[] args) {

        // create initial board from the file 
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}