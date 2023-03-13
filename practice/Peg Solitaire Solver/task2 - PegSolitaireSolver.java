import java.util.ArrayList;
import java.util.Scanner;

public class PegSolitaireSolver {
    final private static boolean RIGHT = true;
    final private static boolean LEFT = false;

    public static void main(String[] args) {

        while (true){
            Scanner sc = new Scanner(System.in);
            int BOARD_SIZE;
            System.out.print("The board size (have to be even number) = ");
            BOARD_SIZE = sc.nextInt();
            while (!(BOARD_SIZE == 3 || BOARD_SIZE % 2 == 0)) {
                System.out.println("board size have to be even number");
                System.out.print("The board size (have to be even number) = ");
                BOARD_SIZE = sc.nextInt();
            }
            System.out.print("The position of the empty slot from 1 to " + BOARD_SIZE +" is ");
            int BLANK_INIT_POS = sc.nextInt();
            boolean reversed = false;

            ArrayList<Integer> board = new ArrayList<>();

            for (int i = 0; i <= BOARD_SIZE; i++) {
                board.add(i, 1);
            }
            if (BLANK_INIT_POS > BOARD_SIZE / 2) {
                BLANK_INIT_POS = BOARD_SIZE - BLANK_INIT_POS + 1;
                reversed = true;
            }
            board.set(BLANK_INIT_POS, 0);

            ArrayList<ArrayList<Integer>> al = solveBoard(board, BLANK_INIT_POS);

            if (al == null)
                System.out.println("This board is unsolvable");

            else {
                System.out.println("The solution is ");
                if (reversed) {
                    for (int k = 0; k < al.size(); k++) {
                        System.out.print("Step " + k + ": \t");
                        for (int i = BOARD_SIZE; i > 0; i--) {
                            System.out.print(al.get(k).get(i) + "\t");
                        }
                        System.out.println();
                    }
                } else {
                    for (int k = 0; k < al.size(); k++) {
                        System.out.print("Step " + k + ": \t");
                        for (int i = 1; i <= BOARD_SIZE; i++) {
                            System.out.print(al.get(k).get(i) + "\t");
                        }
                        System.out.println();
                    }
                }
            }
        }
    }

    public static ArrayList<ArrayList<Integer>> solveBoard(ArrayList<Integer> board, int BLANK_INIT_POS) {
        if (BLANK_INIT_POS != 2 && board.size() == 4) {
            ArrayList<ArrayList<Integer>> aal = new ArrayList<>();
            aal.add(new ArrayList<>(board));
            if (validateMove(board, 3, LEFT)) {
                board = move(board, 3, LEFT);
            } else {
                board = move(board, 1, RIGHT);
            }
            aal.add(new ArrayList<>(board));
            return aal;
        } else if (BLANK_INIT_POS == 2 && board.size() != 4) {
            return solveBoard(board);
        } else {
            return null;
        }
    }

    private static ArrayList<ArrayList<Integer>> solveBoard(ArrayList<Integer> board) {

        ArrayList<ArrayList<Integer>> solution = new ArrayList<>();
        int noOfConversions = 0;
        boolean direction = LEFT;
        final int BOARD_SIZE = board.size() - 1;
        int nextMove = 4;

        solution.add(new ArrayList<>(board));
        while (noOfConversions < BOARD_SIZE - 2) {
            if (validateMove(board, nextMove, direction)) {
                board = move(board, nextMove, direction);
                if (nextMove + 2 > BOARD_SIZE) {
                    nextMove = 1;
                    direction = RIGHT;
                    solution.add(new ArrayList<>(board));
                    noOfConversions++;
                    continue;
                }
                nextMove = nextMove + 2;
                noOfConversions++;
            }
            solution.add(new ArrayList<>(board));
        }
        return solution;
    }

    private static boolean validateMove(ArrayList<Integer> board, int pos, boolean direction) {
        if (direction == LEFT) {
            return board.get(pos - 1) == 1 && board.get(pos - 2) == 0;
        } else {
            return board.get(pos + 1) == 1 && board.get(pos + 2) == 0;
        }
    }

    private static ArrayList<Integer> move(ArrayList<Integer> board, int pos, boolean direction) {
        if (direction == LEFT) {
            board.set(pos, 0);
            board.set(pos - 1, 0);
            board.set(pos - 2, 1);
        } else {
            board.set(pos, 0);
            board.set(pos + 1, 0);
            board.set(pos + 2, 1);
        }
        return board;
    }
}
