import java.util.ArrayList;
import java.util.BitSet;

public class BoardTree {
    private final Board head;
    ArrayList<ArrayList<Board>> treeInArrayList;

    public static void main(String[] args) {

    }

    static class Board {
        private final BitSet boardBitSet;
        private final int BOARD_SIZE;
        private final ArrayList<Board> children;

        public Board(BitSet boardBitSet, int BOARD_SIZE) {
            this.BOARD_SIZE = BOARD_SIZE;
            this.boardBitSet = (BitSet) boardBitSet.clone();

            children = new ArrayList<>();
        }

        public BitSet getBoardBitSet() {
            return boardBitSet;
        }

        public ArrayList<Board> getChildren() {
            return children;
        }

        public void addChild(Board b) {
            this.children.add(b);
        }

        public static void showBoard(Board b, int BOARD_SIZE) {
            for (int i = 1; i <= BOARD_SIZE; i++) {
                if (b.boardBitSet.get(i))
                    System.out.printf("%2d\t", 1);
                else
                    System.out.printf("%2d\t", 0);
            }
        }
    }

    public BoardTree(Board head) {
        this.head = head;
    }

    public BoardTree(BitSet boardBitSet, int BOARD_SIZE) {
        this.head = new Board(boardBitSet, BOARD_SIZE);
    }

    public Board getHead() {
        return head;
    }

    public void traverseTree() {
        treeInArrayList = new ArrayList<>();
        ArrayList<Board> tempBoardsQueueMain = new ArrayList<>();
        Board chainingBoard = head;

        tempBoardsQueueMain.add(chainingBoard);
        if (chainingBoard.children.size() != 0) {
            getTreeInArrayList(chainingBoard.children.get(0), new ArrayList<>(tempBoardsQueueMain));
        }
        for (int i = 1; i < chainingBoard.children.size(); i++) {
            getTreeInArrayList(chainingBoard.children.get(i), new ArrayList<>(tempBoardsQueueMain));
        }
    }

    public ArrayList<ArrayList<Board>> getTreeInArrayList() {
        return treeInArrayList;
    }

    public void getTreeInArrayList(Board node, ArrayList<Board> queueMain) {

        queueMain.add(node);
        if (node.children.size() != 0) {
            getTreeInArrayList(node.children.get(0), new ArrayList<>(queueMain));
        } else {
            treeInArrayList.add(queueMain);
        }
        for (int i = 1; i < node.children.size(); i++) {
            getTreeInArrayList(node.children.get(i), new ArrayList<>(queueMain));
        }
    }

    public static void showAllSolution(ArrayList<ArrayList<Board>> ArrArrBoard, int BOARD_SIZE) {
        int solutionNo = 0;
        for (ArrayList<Board> ArrBoard : ArrArrBoard) {
            System.out.println("//****************************************************//");
            System.out.println("Try number: " + ++solutionNo);
            int i = 0;
            for (Board b : ArrBoard) {
                System.out.printf("Step %2d :\t", i++);
                Board.showBoard(b, BOARD_SIZE);
                System.out.println();
            }
            System.out.println("//****************************************************//");
        }
    }

    public static void showCompleteSolution(ArrayList<ArrayList<Board>> arrArrBoard, int boardSize) {
        int solutionNo = 0;
        int largestArrBoard = 0;
        int target = boardSize - 1;
        for (ArrayList<Board> arrBoard : arrArrBoard) {
            if (arrBoard.size() == target) {
                System.out.println("//****************************************************//");
                System.out.println("Solution number: " + ++solutionNo);
                System.out.println();
                int i = 0;
                for (Board b : arrBoard) {
                    System.out.printf("Step %2d :\t", i++);
                    Board.showBoard(b, boardSize);
                    System.out.println();
                }
                System.out.println("//****************************************************//");
            }
            if (arrBoard.size() > largestArrBoard) {
                largestArrBoard = arrBoard.size();
            }
        }
        if (solutionNo == 0) {
            System.out.println("Unsolvable Board");
            System.out.println("Largest tries : ");
            for (ArrayList<Board> arrBoard : arrArrBoard) {
                if (arrBoard.size() == largestArrBoard) {
                    System.out.println("//****************************************************//");
                    System.out.println("Largest try number: " + ++solutionNo);
                    System.out.println();
                    int i = 0;
                    for (Board b : arrBoard) {
                        System.out.printf("Step %2d :\t", i++);
                        Board.showBoard(b, boardSize);
                        System.out.println();
                    }
                    System.out.println("Unsolvable Board");
                    System.out.println("//****************************************************//");
                    System.out.println();
                }
            }
        }
    }
}