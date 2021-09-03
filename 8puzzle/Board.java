/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

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
        int [][] tilesCopy = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tilesCopy[i][j] = this.tiles[i][j];
            exch(tilesCopy, );
    }

    // internal iterator class
    private class neighborsIterator implements Iterator<Board> {

        private int i_OfBlank, j_OfBlank;
        private int neighborAddress;
        private int copyTiles[][];
        private boolean iplus = false, ineg = false, jplus = false, jneg = false;

        public neighborsIterator() {
            copyTiles = new int[n][n];

            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {
                    copyTiles[i][j] = tiles[i][j];
                    if (copyTiles[i][j] == 0) {
                        i_OfBlank = i;
                        j_OfBlank = j;
                    }
                }
        }

        public boolean hasNext() {
            if (!jneg && j_OfBlank - 1 >= 0) {
                neighborAddress = 0;
                return true;
            }
            if (!iplus && i_OfBlank + 1 < n) {
                neighborAddress = 1;
                return true;
            }
            if (!jplus && j_OfBlank + 1 < n) {
                neighborAddress = 2;
                return true;
            }
            if (!ineg && i_OfBlank - 1 >= 0) {
                neighborAddress = 3;
                return true;
            }
            return false;
        }

        public Board next() {
            int i_exch = i_OfBlank;
            int j_exch = j_OfBlank;

            if (neighborAddress == 0) {
                j_exch--;
                jneg = true;
            }
            else if (neighborAddress == 1) {
                i_exch++;
                iplus = true;
            }
            else if (neighborAddress == 2) {
                j_exch++;
                jplus = true;
            }
            else if (neighborAddress == 3) {
                i_exch--;
                ineg = true;
            }

            exch(copyTiles, i_OfBlank, j_OfBlank, i_exch, j_exch);
            Board b = new Board(copyTiles);
            exch(copyTiles, i_OfBlank, j_OfBlank, i_exch, j_exch);

            return b;
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
        int n = 3;
        int[][] arr = new int[n][n];

        // int x = 1;
        // for (int i = 0; i < n; i++)
        //     for (int j = 0; j < n; j++)
        //         arr[i][j] = x++;

        arr[0][0] = 8;
        arr[0][1] = 1;
        arr[0][2] = 0;
        arr[1][0] = 4;
        arr[1][1] = 3;
        arr[1][2] = 2;
        arr[2][0] = 7;
        arr[2][1] = 5;
        arr[2][2] = 6;

        int[][] arr1 = new int[n][n];


        Board b = new Board(arr);
        StdOut.println(b.toString());

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
