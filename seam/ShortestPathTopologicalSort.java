/* *****************************************************************************
 *  Name: mohamed ashraf farouk
 *  Date: 24/10/2021
 *  Description: customized implementation of topological sort shortest path
 **************************************************************************** */

public class ShortestPathTopologicalSort {

    private final int width, height;
    private final double[][] energy;

    private double[][] disTo;
    private int[][] edgeTo;

    private int shortestPathVertex;

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    public ShortestPathTopologicalSort(double[][] energy) {

        this.height = energy[0].length;
        this.width = energy.length;
        this.energy = energy;

        disTo = new double[width][height];
        edgeTo = new int[width][height];

    }

    // fina horizontal or vertical path
    public int[] findPath(boolean orientation) {

        double shortestPathDistance = Double.POSITIVE_INFINITY;
        int[] path;

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
        else {
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

        if (orientation == VERTICAL) {
            path = new int[height];

            path[height - 1] = shortestPathVertex;

            int y = height - 2;
            while (y >= 0) {
                path[y] = edgeTo[path[y + 1]][y + 1];
                y--;
            }
        }
        else {
            path = new int[width];

            path[width - 1] = shortestPathVertex;

            int x = width - 2;
            while (x >= 0) {
                path[x] = edgeTo[x + 1][path[x + 1]];
                x--;
            }
        }
        return path;
    }

    // relaxation
    private void relaxVer(int x, int y) {
        for (int xLocal = x - 1; xLocal <= x + 1; xLocal++) {
            if (disTo[xLocal][y + 1] > disTo[x][y] + energy[xLocal][y + 1]) {
                disTo[xLocal][y + 1] = disTo[x][y] + energy[xLocal][y + 1];
                edgeTo[xLocal][y + 1] = x;
            }
        }
    }

    private void relaxHor(int x, int y) {
        for (int yLocal = y - 1; yLocal <= y + 1; yLocal++) {
            if (disTo[x + 1][yLocal] > disTo[x][y] + energy[x + 1][yLocal]) {
                disTo[x + 1][yLocal] = disTo[x][y] + energy[x + 1][yLocal];
                edgeTo[x + 1][yLocal] = y;
            }
        }
    }
}
