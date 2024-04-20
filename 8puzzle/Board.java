import java.util.LinkedList;

import edu.princeton.cs.algs4.StdOut;

/**
 * The Board data type that models an n-by-n board sliding tiles.
 * 
 * @author Karthikeyan
 */
public class Board {
    private int[][] tiles;      // tiles in the board
    private int n;              // dimension of the board
    private int hamming   = -1; // hamming distance to the goal board
    private int manhattan = -1; // manhattan distance to the goal board
    private int blankRow;       // row index of the blank tile
    private int blankCol;       // column index of the blank tile

    /**
     * Creates a board from an n-by-n array of tiles, where 
     * tiles[i][j] = tile at (row, col).
     * 
     * @param tiles tiles in the board.
     */
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];

                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
    }

    /**
     * Returns the string representation of the board.
     * @return returns the string representation {@code boardString}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(n).append('\n');
        for (int i = 0; i < n; i++) {
            for (int tile : tiles[i])
                builder.append(String.format("%2d ", tile));
            builder.append('\n');
        }       
        return builder.toString();
    }
    
    /**
     * Returns the dimension of the board.
     * @return returns the dimension {@code n}
     */
    public int dimension() {
        return n;
    }

    /**
     * Returns number of tiles out of place.
     * @return returns the hamming distance {@code hamming}
     */
    public int hamming() {
        if (hamming != -1) return hamming;
        hamming = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && i == j)
                    break;
                if (tiles[i][j] != i * n + j + 1)
                    hamming++;
            }
        }
        return hamming;
    }

    /**
     * Returns sum of manhattan distance between tiles and goal.
     * @return returns manhattan distance {@code manhattan}
     */
    public int manhattan() {
        if (manhattan != -1) return manhattan;
        manhattan = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                int x = (tiles[i][j] - 1) / n;
                int y = (tiles[i][j] - 1) % n;
                manhattan += Math.abs(i - x) + Math.abs(j - y);
            }
        }
        return manhattan;
    }

    /**
     * Returns true if the board is the goal board; false, otherwise.
     * @return {@code true} if goal board; otherwise {@code false}
     */
    public boolean isGoal() {
        return hamming() == 0;
    }
    
    /**
     * Returns true if the given board is same as this board; otherwise
     * false.
     * @return {@code true} if equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) 
            return false;

        Board that = (Board) y;
        if (this.n != that.n)          return false;
        if (blankRow != that.blankRow) return false;
        if (blankCol != that.blankCol) return false; 
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) 
                    return false;
            }
        }
        return true;
    }

    /**
     * Returns all neighboring boards.
     * @return returns all neighboring boards
     */
    public Iterable<Board> neighbors() {
        LinkedList<Board> boards = new LinkedList<>();
        if (blankRow > 0) {
            int[][] copy = copy();
            swap(copy, blankRow, blankCol, blankRow - 1, blankCol);
            boards.add(new Board(copy));
        }
        if (blankRow < n - 1) {
            int[][] copy = copy();
            swap(copy, blankRow, blankCol, blankRow + 1, blankCol);
            boards.add(new Board(copy));
        }
        if (blankCol > 0) {
            int[][] copy = copy();
            swap(copy, blankRow, blankCol, blankRow, blankCol - 1);
            boards.add(new Board(copy));
        }
        if (blankCol < n - 1) {
            int[][] copy = copy();
            swap(copy, blankRow, blankCol, blankRow, blankCol + 1);
            boards.add(new Board(copy));
        }
        return boards;
    }

    /**
     * Returns a board that is obtained by any pair of tiles.
     * @return returns a twin board.
     */
    public Board twin() {
        int[][] copy = copy();
        int row = blankRow != 0 ? 0 : 1;
        swap(copy, row, 0, row, 1);
        return new Board(copy);
    }

    // returns a copy of the tiles array
    private int[][] copy() {
        int[][] clone = new int[n][];
        for (int i = 0; i < n; i++)
            clone[i] = tiles[i].clone();
        return clone;
    }

    // swaps the two elements of the given array
    private void swap(int[][] a, int i, int j, int x, int y) {
        int temp = a[i][j];
        a[i][j] = a[x][y];
        a[x][y] = temp;
    }
    
    
    // unit tests the code
    public static void main(String[] args) {
        int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(tiles);
        StdOut.println(board);
        StdOut.println("Hamming: " + board.hamming());
        StdOut.println("Manhattan: " + board.manhattan());
        StdOut.print("Is goal? " + board.isGoal());
        StdOut.println("Neighbors: ");
        for (Board b : board.neighbors()) {
            StdOut.println(b);
        }
    }
}
