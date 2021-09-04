/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {

    private final int n;
    private int[][] tiles;

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

        for (int[] row : tiles) {
            toStr.append(" ");
            for (int element : row)
                toStr.append(element + "  ");
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
        int hammingNum = 0;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != ((j + 1) + i * n) && tiles[i][j] != 0)
                    hammingNum++;
            }
        return hammingNum;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanSum = 0;

        int tempVal, trueI, trueJ;
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
        if (!(y instanceof Board))
            return false;
        if (this == y)
            return true;

        return this.toString().equals(y.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return neighborsIterator::new;
        // return () -> new neighborsIterator();
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

    // internal iterator class
    private class neighborsIterator implements Iterator<Board> {

        private Queue<Board> boardQueue;

        public neighborsIterator() {
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
        }

        public boolean hasNext() {
            if (boardQueue.isEmpty())
                return false;
            return true;
        }

        public Board next() {
            if (!hasNext())
                throw new NoSuchElementException("no more elements");
            return boardQueue.dequeue();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // helper exchange function
    private void exch(int[][] a, int i1, int j1, int i2, int j2) {
        int temp = a[i1][j1];
        a[i1][j1] = a[i2][j2];
        a[i2][j2] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int n = 2;
        int[][] arr = new int[n][n];

        // int x = 1;
        // for (int i = 0; i < n; i++)
        //     for (int j = 0; j < n; j++)
        //         arr[i][j] = x++;

        // arr[0][0] = 2;
        // arr[0][1] = 1;
        // arr[0][2] = 3;
        // arr[1][0] = 4;
        // arr[1][1] = 6;
        // arr[1][2] = 5;
        // arr[2][0] = 7;
        // arr[2][1] = 8;
        // arr[2][2] = 0;

        arr[0][0] = 0;
        arr[0][1] = 1;
        arr[1][0] = 3;
        arr[1][1] = 2;

        int[][] arr1 = new int[n][n];


        Board b = new Board(arr);
        StdOut.println(b.toString());
        //StdOut.println(b.twin().toString());


        Iterable<Board> i = b.neighbors();

        for (Board x : i)
            StdOut.println(x.toString());

        // arr[0][0] = 1;
        // arr[0][1] = 8;
        // Board b1 = new Board(arr);
        //
        // StdOut.println(b.toString());
        // StdOut.println(b.hamming());
        // StdOut.println(b.manhattan());
        // StdOut.println(b.isGoal());
        //
        // StdOut.println(b.equals(b1));

    }
}
