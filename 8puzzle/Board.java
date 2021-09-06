/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private final int n;
    private final int[][] tiles;
    private int hammingNum = -1;
    private int manhattanSum = -1;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException("the tiles array con not be nulled");

        n = tiles.length;
        this.tiles = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                this.tiles[i][j] = tiles[i][j];
    }

    // string representation of this board
    public String toString() {

        StringBuilder toStr = new StringBuilder(n + "\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                toStr.append(String.format("%2d ", tiles[i][j]));
            }
            toStr.append("\n");
        }

        return toStr.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        // is it the first time to call this function YES OR NO ?
        // NO .. return the value stored
        if (hammingNum != -1)
            return hammingNum;

        //YES .. calculate the hammingNum, store and return it
        hammingNum = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != ((j + 1) + i * n) && tiles[i][j] != 0)
                    hammingNum++;
            }
        return hammingNum;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        // is it the first time to call this function YES OR NO ?
        // NO .. return the value stored
        if (manhattanSum != -1)
            return manhattanSum;

        //YES .. calculate the manhattanSum, store and return it
        int tempVal, trueI, trueJ;
        manhattanSum = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {

                if (tiles[i][j] != ((j + 1) + i * n) && tiles[i][j] != 0) {
                    // first method to solve it (mathematical method)
                    trueI = (tiles[i][j] - 1) / n;
                    trueJ = (tiles[i][j] - trueI * n - 1);

                    manhattanSum += Math.abs(i - trueI) + Math.abs(j - trueJ);

                    // second method
                    // trueI = 0;
                    // trueJ = 0;
                    //
                    // tempVal = tiles[i][j] - n;
                    // while (tempVal > 0) {
                    //     trueI++;
                    //     tempVal -= n;
                    // }
                    // trueJ = tiles[i][j] - trueI * n - 1;
                    //
                    // manhattanSum += Math.abs(i - trueI) + Math.abs(j - trueJ);
                }
            }
        return manhattanSum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (this.hamming() == 0)
            return true;
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (this == y)
            return true;
        if (this.getClass() != y.getClass())
            return false;
        if (this.dimension() != ((Board)y).dimension())
            return false;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (this.tiles[i][j] != ((Board) y).tiles[i][j])
                    return false;

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Queue<Board> boardQueue;

        int tilesCopy[][];
        int i_OfBlank = 0, j_OfBlank = 0;
        tilesCopy = new int[n][n];
        boardQueue = new Queue<Board>();

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                tilesCopy[i][j] = tiles[i][j];
                if (tilesCopy[i][j] == 0) {
                    i_OfBlank = i;
                    j_OfBlank = j;
                }
            }

        if (j_OfBlank - 1 >= 0) {
            exch(tilesCopy, i_OfBlank, j_OfBlank, i_OfBlank, j_OfBlank - 1);
            boardQueue.enqueue(new Board(tilesCopy));
            exch(tilesCopy, i_OfBlank, j_OfBlank, i_OfBlank, j_OfBlank - 1);
        }
        if (i_OfBlank + 1 < n) {
            exch(tilesCopy, i_OfBlank, j_OfBlank, i_OfBlank + 1, j_OfBlank);
            boardQueue.enqueue(new Board(tilesCopy));
            exch(tilesCopy, i_OfBlank, j_OfBlank, i_OfBlank + 1, j_OfBlank);
        }
        if (j_OfBlank + 1 < n) {
            exch(tilesCopy, i_OfBlank, j_OfBlank, i_OfBlank, j_OfBlank + 1);
            boardQueue.enqueue(new Board(tilesCopy));
            exch(tilesCopy, i_OfBlank, j_OfBlank, i_OfBlank, j_OfBlank + 1);
        }
        if (i_OfBlank - 1 >= 0) {
            exch(tilesCopy, i_OfBlank, j_OfBlank, i_OfBlank - 1, j_OfBlank);
            boardQueue.enqueue(new Board(tilesCopy));
            exch(tilesCopy, i_OfBlank, j_OfBlank, i_OfBlank - 1, j_OfBlank);
        }

        return boardQueue;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] tilesCopy = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tilesCopy[i][j] = tiles[i][j];

        for (int i = 0; i < n; i++)
            if (tilesCopy[i][0] != 0 && tilesCopy[i][1] != 0) {
                exch(tilesCopy, i, 0, i, 1);
                break;
            }
        return new Board(tilesCopy);
    }

    // helper exchange function
    private void exch(int[][] a, int i1, int j1, int i2, int j2) {
        int temp = a[i1][j1];
        a[i1][j1] = a[i2][j2];
        a[i2][j2] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int n = 3;
        int[][] arr = new int[n][n];

        // int x = 1;
        // for (int i = 0; i < n; i++)
        //     for (int j = 0; j < n; j++)
        //         arr[i][j] = x++;

        arr[0][0] = 2;
        arr[0][1] = 1;
        arr[0][2] = 4;
        arr[1][0] = 3;
        arr[1][1] = 5;
        arr[1][2] = 6;
        arr[2][0] = 7;
        arr[2][1] = 8;
        arr[2][2] = 0;

        // arr[0][0] = 0;
        // arr[0][1] = 1;
        // arr[1][0] = 3;
        // arr[1][1] = 2;

        int[][] arr1 = new int[n][n];


        Board b = new Board(arr);
        StdOut.println(b.toString());
        //StdOut.println(b.twin().toString());


        //Iterable<Board> i = b.neighbors();

        // for (Board x : i)
        //     StdOut.println(x.toString());

        //arr[0][0] = 1;
        //arr[0][1] = 8;
        Board b1 = new Board(arr);
        //
        // StdOut.println(b.toString());
         StdOut.println(b.hamming());
         StdOut.println(b.manhattan());
        StdOut.println(b.hamming());
        StdOut.println(b.manhattan());
         StdOut.println(b.isGoal());
        //
        StdOut.println(b.equals(b1));

    }
}
