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
import java.util.Iterator;

public class Solver {

    private SearchNode goalNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> searchNodeMinPQ = new MinPQ<SearchNode>(manhattanOrder());
        SearchNode currentSearchNode = new SearchNode(initial, null, 0);
        searchNodeMinPQ.insert(currentSearchNode);

        searchNodeMinPQ.delMin();
        boolean checkThePreBoard = false;
        do {
            for (Board neighbor : currentSearchNode.board.neighbors()) {
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
            currentSearchNode = null;
            currentSearchNode = searchNodeMinPQ.delMin();
            checkThePreBoard = true;
        } while (!currentSearchNode.board.isGoal() &&
                 !(currentSearchNode.board).equals(currentSearchNode.board.twin()));

        goalNode = currentSearchNode;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return goalNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable())
            return goalSolution::new;
        else
            return null;
    }

    class goalSolution implements Iterator<Board> {

        private Stack<SearchNode> stacksSolnNodes = new Stack<SearchNode>();

        public goalSolution() {
            SearchNode chainingNode = goalNode;

            stacksSolnNodes.push(chainingNode);

            while (chainingNode.parentNode != null) {
                chainingNode = chainingNode.parentNode;
                stacksSolnNodes.push(chainingNode);
            }
        }

        public boolean hasNext() {
            return !(stacksSolnNodes.isEmpty());
        }

        public Board next() {
            return stacksSolnNodes.pop().board;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    private class SearchNode {
        private Board board;
        private SearchNode parentNode;
        private int moves;
        private int priorityH;
        private int priorityM;


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
