/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private final SearchNode goalNode;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("board can not be nulled");

        MinPQ<SearchNode> searchNodeMinPQ = new MinPQ<SearchNode>(manhattanOrder());
        SearchNode currentSearchNode = new SearchNode(initial, null, 0);
        searchNodeMinPQ.insert(currentSearchNode);

        MinPQ<SearchNode> unsolvableSearchNodeMinPQ = new MinPQ<SearchNode>(manhattanOrder());
        SearchNode unsolvableCurrentSearchNode = new SearchNode(initial.twin(), null, 0);
        unsolvableSearchNodeMinPQ.insert(unsolvableCurrentSearchNode);

        searchNodeMinPQ.delMin();
        unsolvableSearchNodeMinPQ.delMin();
        boolean checkThePreBoard = false;
        solvable = true;

        // if the board is solved already do not enter the while loop
        while (!currentSearchNode.board.isGoal()
                && !unsolvableCurrentSearchNode.board.isGoal()) {
            solvable = true;
            for (Board neighbor : currentSearchNode.board.neighbors()) {
                // if it is not the first loop compare between neighbors boards and
                // the previous board to eliminate duplications
                if (checkThePreBoard) {
                    if (!neighbor.equals(currentSearchNode.parentNode.board)) {
                        SearchNode node = new SearchNode(neighbor, currentSearchNode,
                                                         currentSearchNode.moves + 1);
                        searchNodeMinPQ.insert(node);
                    }
                }
                else {
                    SearchNode node = new SearchNode(neighbor, currentSearchNode,
                                                     currentSearchNode.moves + 1);
                    searchNodeMinPQ.insert(node);
                }
            }
            currentSearchNode = searchNodeMinPQ.delMin();

            if (currentSearchNode.board.isGoal())
                break;

            solvable = false;
            // second A* algorithm in the twin board
            // to determine if the board is unsolvable
            for (Board neighbor : unsolvableCurrentSearchNode.board.neighbors()) {
                // if it is not the first loop compare between neighbors boards and
                // the previous board to eliminate duplications
                if (checkThePreBoard) {
                    if (!neighbor.equals(unsolvableCurrentSearchNode.parentNode.board)) {
                        SearchNode node = new SearchNode(neighbor, unsolvableCurrentSearchNode,
                                                         unsolvableCurrentSearchNode.moves + 1);
                        unsolvableSearchNodeMinPQ.insert(node);
                    }
                }
                else {
                    SearchNode node = new SearchNode(neighbor, unsolvableCurrentSearchNode,
                                                     unsolvableCurrentSearchNode.moves + 1);
                    unsolvableSearchNodeMinPQ.insert(node);
                }
            }
            unsolvableCurrentSearchNode = unsolvableSearchNodeMinPQ.delMin();
            checkThePreBoard = true;
        }
        goalNode = currentSearchNode;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        return goalNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            final Stack<Board> stacksSolnBoards = new Stack<Board>();

            SearchNode chainingNode = goalNode;

            while (chainingNode.parentNode != null) {
                stacksSolnBoards.push(chainingNode.board);
                chainingNode = chainingNode.parentNode;
            }
            stacksSolnBoards.push(chainingNode.board);

            return stacksSolnBoards;
        }
        else
            return null;
    }


    private class SearchNode {
        private final  Board board;
        private final SearchNode parentNode;
        private final int moves;
        private final int priorityH;
        private final int priorityM;

        private SearchNode(Board board, SearchNode parentNode, int moves) {
            this.board = board;
            this.parentNode = parentNode;
            this.moves = moves;
            this.priorityH = board.hamming() + moves;
            this.priorityM = board.manhattan() + moves;
        }
    }

    private static Comparator<SearchNode> hammingOrder() {
        return (s1, s2) -> Integer.compare(s1.priorityH, s2.priorityH);
    }

    private static Comparator<SearchNode> manhattanOrder() {
        return (s1, s2) -> Integer.compare(s1.priorityM, s2.priorityM);
    }

    public static void main(String[] args) {

        // create initial board from file
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
