/* *****************************************************************************
 *  Name: mohamed ashraf farouk
 *  Date: 23/10/2021
 *  Description: seam carving implementation
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {

    // data structure variables
    private int[][] picArr;
    private int width, height;
    private double[][] energy;
    private boolean energyAlreadyCalculated;
    private boolean transposed;

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("picture can not be null");

        this.width = picture.width();
        this.height = picture.height();

        picArr = copyPicIntoArr(picture);

        this.energy = new double[width][height];
        transposed = false;

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                energy[x][y] = -1;
    }

    // current picture
    public Picture picture() {
        return toPicture();
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateX(x);
        validateY(y);

        if (!energyAlreadyCalculated && energy[x][y] == -1)
            energy[x][y] = calcEnergyFor(x, y);

        return energy[x][y];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (!energyAlreadyCalculated)
            calcEnergy();

        ShortestPathTopologicalSort spts = new ShortestPathTopologicalSort(energy, HORIZONTAL);

        return spts.path();
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (!energyAlreadyCalculated)
            calcEnergy();

        ShortestPathTopologicalSort spts = new ShortestPathTopologicalSort(energy, VERTICAL);

        return spts.path();
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

        validateHorSeam(seam);

        height--;

        int[][] picArrCopy = new int[width][height];

        if (transposed)
            transpose();
        transposed = false;

        for (int x = 0; x < width; x++) {
            System.arraycopy(picArr[x], 0, picArrCopy[x], 0, seam[x]);
            System.arraycopy(picArr[x], seam[x] + 1, picArrCopy[x], seam[x], height - seam[x]);
        }

        picArr = picArrCopy;
        energyAlreadyCalculated = false;
        reCalcHorEnergy(seam);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

        validateVerSeam(seam);

        width--;

        int[][] picArrCopy = new int[height][width];

        if (!transposed)
            transpose();

        for (int x = 0; x < width; x++) {
            System.arraycopy(picArr[x], 0, picArrCopy[x], 0, seam[x]);
            System.arraycopy(picArr[x], seam[x] + 1, picArrCopy[x], seam[x], height - seam[x]);
        }

        picArr = picArrCopy;
        energyAlreadyCalculated = false;
        reCalcHorEnergy(seam);
    }

    // copy picture into int matrix
    private int[][] copyPicIntoArr(Picture picture) {

        int[][] picArr = new int[width][height];

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                picArr[x][y] = picture.getRGB(x, y);

        return picArr;
    }

    // int rgb matrix into picture
    private Picture toPicture() {
        Picture picture = new Picture(width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                picture.setRGB(x, y, picArr[x][y]);

        return picture;
    }

    // transpose rgb matrix for vertical removal
    private void transpose() {
        int[][] tempMatric = new int[height][width];

        for (int x = 0; x < height; x++)
            for (int y = 0; y < width; y++)
                tempMatric[x][y] = picArr[y][x];

        picArr = tempMatric;
        transposed = true;
    }

    // helper functions for calculate the energy function
    // calculate energy for entire picture first time
    private void calcEnergy() {
        if (energyAlreadyCalculated)
            return;

        // set corners energy
        cornersEnegy();

        // calc normal energy for first time
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                energy[x][y] = Math.sqrt(calcGradSqX(x, y) + calcGradSqY(x, y));
            }
        }
        // update the flag to already-calculated
        energyAlreadyCalculated = true;
    }

    private double calcEnergyFor(int x, int y) {
        if (inCorner(x, y))
            return 1000.0;
        else
            return Math.sqrt(calcGradSqX(x, y) + calcGradSqY(x, y));
    }

    // recalculating energy function
    private void reCalcVerEnergy(int[] seam) {
        for (int y = 1; y < width - 1; y++) {

            if (inCorner(seam[y] - 1, y))
                energy[seam[y] - 1][y] = 1000.0;
            else {
                energy[seam[y] - 1][y] = Math.sqrt(calcGradSqX(seam[y] - 1, y)
                                                           + calcGradSqY(seam[y] - 1, y));
            }
            if (inCorner(seam[y], y))
                energy[seam[y]][y] = 1000.0;
            else {
                energy[seam[y]][y] = Math.sqrt(calcGradSqX(seam[y], y)
                                                       + calcGradSqY(seam[y], y));
            }
        }
        // update the flag to already-calculated
        energyAlreadyCalculated = true;
    }

    private void reCalcHorEnergy(int[] seam) {

        double[][] tempEnergy = new double[width][height];

        for (int x = 0; x < width; x++) {
            System.arraycopy(energy[x], 0, tempEnergy[x], 0, seam[x]);
            System.arraycopy(energy[x], seam[x] + 1, tempEnergy[x], seam[x], height - seam[x]);
        }

        energy = tempEnergy;

        for (int x = 1; x < width - 1; x++) {

            if (inCorner(x, seam[x] - 1))
                energy[x][seam[x] - 1] = 1000.0;
            else {
                energy[x][seam[x] - 1] = Math.sqrt(calcGradSqX(x, seam[x] - 1)
                                                           + calcGradSqY(x, seam[x] - 1));
            }
            if (inCorner(x, seam[x]))
                energy[x][seam[x]] = 1000.0;
            else {
                energy[x][seam[x]] = Math.sqrt(calcGradSqX(x, seam[x])
                                                       + calcGradSqY(x, seam[x]));
            }
        }
        // update the flag to already-calculated
        energyAlreadyCalculated = true;
    }

    // get corners energy
    private void cornersEnegy() {
        // set the corners energy = 1000
        for (int x = 0; x < width; x++) {
            energy[x][0] = 1000;
            energy[x][height - 1] = 1000;
        }
        for (int y = 1; y < height - 1; y++) {
            energy[0][y] = 1000;
            energy[width - 1][y] = 1000;
        }
    }

    private double calcGradSqX(int x, int y) {
        // getting the colour of pixel x - 1
        int rgb = picArr[x - 1][y];

        int rNeg = (rgb >> 16) & 0x00FF;
        int gNeg = (rgb >> 8) & 0x00FF;
        int bNeg = rgb & 0x00FF;

        // getting the colour of pixel x + 1
        rgb = picArr[x + 1][y];

        int rPos = (rgb >> 16) & 0x00FF;
        int gPos = (rgb >> 8) & 0x00FF;
        int bPos = rgb & 0x00FF;

        return Math.pow(rPos - rNeg, 2) + Math.pow(gPos - gNeg, 2) + Math.pow(bPos - bNeg, 2);
    }

    private double calcGradSqY(int x, int y) {
        // getting the colour of pixel y - 1
        int rgb = picArr[x][y - 1];

        int rNeg = (rgb >> 16) & 0x00FF;
        int gNeg = (rgb >> 8) & 0x00FF;
        int bNeg = rgb & 0x00FF;

        // getting the colour of pixel y + 1
        rgb = picArr[x][y + 1];

        int rPos = (rgb >> 16) & 0x00FF;
        int gPos = (rgb >> 8) & 0x00FF;
        int bPos = rgb & 0x00FF;

        return Math.pow(rPos - rNeg, 2) + Math.pow(gPos - gNeg, 2) + Math.pow(bPos - bNeg, 2);
    }

    // validation
    private void validateX(int x) {
        if (x < 0 || x >= width)
            throw new IllegalArgumentException("x provided is illegal");
    }

    private void validateY(int y) {
        if (y < 0 || y >= height)
            throw new IllegalArgumentException("y provided is illegal");
    }

    private void validateVerSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("seam can not be null");
        if (width == 1)
            throw new IllegalArgumentException("the picture is only one pixel in width");

        if (seam.length != width)
            throw new IllegalArgumentException("invalid seam in size");

        // seam in prescribed range
        for (int y = 0; y < seam.length; y++) {
            if (seam[y] < 0 || seam[y] >= width)
                throw new IllegalArgumentException("invalid seam in range");
        }

        // no two entries differ by more than one
        for (int y = 0; y < seam.length - 1; y++) {
            if (Math.abs(seam[y + 1] - seam[y]) > 1)
                throw new IllegalArgumentException("two entries differ by more than one");
        }
    }

    private void validateHorSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("seam can not be null");
        if (height == 1)
            throw new IllegalArgumentException("the picture is only one pixel in height");

        if (seam.length != width)
            throw new IllegalArgumentException("invalid seam in size");

        // seam in prescribed range
        for (int x = 0; x < seam.length; x++) {
            if (seam[x] < 0 || seam[x] >= height) {
                throw new IllegalArgumentException("invalid seam in range");
            }

        }

        // no two entries differ by more than one
        for (int x = 0; x < seam.length - 1; x++) {
            if (Math.abs(seam[x + 1] - seam[x]) > 1)
                throw new IllegalArgumentException("two entries differ by more than one");
        }
    }

    private boolean inCorner(int x, int y) {
        if (x == 0 || x == width - 1)
            return true;
        if (y == 0 || y == height - 1)
            return true;

        return false;
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("image is %d pixels wide by %d pixels high.\n", picture.width(),
                      picture.height());

        SeamCarver sc = new SeamCarver(picture);

        StdOut.printf("Printing energy calculated for each pixel.\n");

        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++)
                StdOut.printf("%9.0f ", sc.energy(col, row));
            StdOut.println();
        }

        int[] seam = { 2, 2, 1, 2, 1, 2 };
        sc.reCalcHorEnergy(seam);

        StdOut.println();
        StdOut.println();
        StdOut.printf("Printing energy calculated for each pixel.\n");
        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++)
                StdOut.printf("%9.0f ", sc.energy(col, row));
            StdOut.println();
        }

        int[] path = sc.findHorizontalSeam();

        for (int i = 0; i < path.length; i++)
            StdOut.print(path[i] + "\t");
    }

}
