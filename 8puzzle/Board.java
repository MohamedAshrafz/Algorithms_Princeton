/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

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
    //
    // // does this board equal y?
    // public boolean equals(Object y) {
    //
    // }
    //
    // // all neighboring boards
    // public Iterable<Board> neighbors() {
    //
    // }
    //
    // // a board that is obtained by exchanging any pair of tiles
    // public Board twin() {
    //
    // }

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
        arr[0][2] = 3;
        arr[1][0] = 4;
        arr[1][1] = 0;
        arr[1][2] = 2;
        arr[2][0] = 7;
        arr[2][1] = 6;
        arr[2][2] = 5;

        Board b = new Board(arr);
        StdOut.println(b.toString());
        StdOut.println(b.hamming());
        StdOut.println(b.manhattan());
        StdOut.println(b.isGoal());
    }

}