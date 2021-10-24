/* *****************************************************************************
 *  Name: mohamed ashraf farouk
 *  Date: 24/10/2021
 *  Description: customized implementation of topological sort shortest path
 **************************************************************************** */

public class ShortestPathTopologicalSort {

    private int width, height;
    private boolean orientation;
    private double[][] energy;

    private double[][] disTo;
    private int[][] edgeTo;

    private int shortestPathVertex;

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    public ShortestPathTopologicalSort(double[][] energy, boolean orientation) {

        this.height = energy[0].length;
        this.width = energy.length;
        this.energy = energy;
        boolean tooSmall = false;

        disTo = new double[width][height];
        edgeTo = new int[width][height];
        this.orientation = orientation;

        double shortestPathDistance = Integer.MAX_VALUE;

        if (orientation == VERTICAL) {

            for (int x = 0; x < width; x++) {
                disTo[x][0] = 0;

                for (int y = 1; y < height; y++)
                    disTo[x][y] = Double.POSITIVE_INFINITY;
            }

            for (int y = 0; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    relaxVer(x, y);
                }
            }

            for (int x = 0; x < width; x++) {
                if (shortestPathDistance > disTo[x][height - 1]) {
                    shortestPathVertex = x;
                    shortestPathDistance = disTo[x][height - 1];
                }
            }
        }

        if (orientation == HORIZONTAL) {

            for (int y = 0; y < height; y++) {
                disTo[0][y] = 0;

                for (int x = 1; x < width; x++)
                    disTo[x][y] = Double.POSITIVE_INFINITY;
            }

            for (int x = 0; x < width - 1; x++) {
                for (int y = 1; y < height - 1; y++) {
                    relaxHor(x, y);
                }
            }

            for (int y = 0; y < height; y++) {
                if (shortestPathDistance > disTo[width - 1][y]) {
                    shortestPathVertex = y;
                    shortestPathDistance = disTo[width - 1][y];
                }
            }
        }
    }

    public int[] path() {
        int[] path;
        if (orientation == VERTICAL) {
            path = new int[height];

            path[height - 1] = shortestPathVertex;

            int y = height - 2;
            while (y >= 0){
                path[y] = edgeTo[path[y + 1]][y + 1];
                y--;
            }
        }
        else {
            path = new int[width];

            path[width - 1] = shortestPathVertex;

            int x = width - 2;
            while (x >= 0){
                path[x] = edgeTo[x + 1][path[x + 1]];
                x--;
            }
        }

        return path;
    }

    // relaxation
    private void relaxVer(int x, int y) {
        if (disTo[x - 1][y + 1] > disTo[x][y] + energy[x - 1][y + 1]) {
            disTo[x - 1][y + 1] = disTo[x][y] + energy[x - 1][y + 1];
            edgeTo[x - 1][y + 1] = x;
        }

        if (disTo[x][y + 1] > disTo[x][y] + energy[x][y + 1]) {
            disTo[x][y + 1] = disTo[x][y] + energy[x][y + 1];
            edgeTo[x][y + 1] = x;
        }

        if (disTo[x + 1][y + 1] > disTo[x][y] + energy[x + 1][y + 1]) {
            disTo[x + 1][y + 1] = disTo[x][y] + energy[x + 1][y + 1];
            edgeTo[x + 1][y + 1] = x;
        }
    }

    private void relaxHor(int x, int y) {
        if (disTo[x + 1][y - 1] > disTo[x][y] + energy[x + 1][y - 1]) {
            disTo[x + 1][y - 1] = disTo[x][y] + energy[x + 1][y - 1];
            edgeTo[x + 1][y - 1] = y;
        }

        if (disTo[x + 1][y] > disTo[x + 1][y] + energy[x + 1][y]) {
            disTo[x + 1][y] = disTo[x + 1][y] + energy[x + 1][y];
            edgeTo[x + 1][y] = y;
        }

        if (disTo[x + 1][y + 1] > disTo[x][y] + energy[x + 1][y + 1]) {
            disTo[x + 1][y + 1] = disTo[x][y] + energy[x + 1][y + 1];
            edgeTo[x + 1][y + 1] = y;
        }
    }
}
