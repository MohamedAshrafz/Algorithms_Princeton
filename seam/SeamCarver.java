/* *****************************************************************************
 *  Name: mohamed ashraf farouk
 *  Date: 23/10/2021
 *  Description: seam carving implementation
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdIn;

public class SeamCarver {

    // data structure variables
    private int[][] picArr;
    private int width, height;
    private double[][] energy;
    private boolean energyAlreadyCalculatedOnce;
    private boolean transposed;

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("picture can not be null");

        this.width = picture.width();
        this.height = picture.height();

        this.picArr = copyPicIntoArr(picture);

        transposed = false;

        this.energy = new double[width][height];

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                energy[x][y] = -1;
    }

    // current picture
    public Picture picture() {

        if (transposed)
            transpose();

        return toPicture();
    }

    // width of current picture
    public int width() {
        if (transposed)
            return height;
        else
            return width;
    }

    // height of current picture
    public int height() {
        if (transposed)
            return width;
        else
            return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {

        if (transposed)
            transpose();

        if (!inPicture(x, y))
            throw new IllegalArgumentException("not a valid point");

        if (!energyAlreadyCalculatedOnce || energy[x][y] == -1)
            energy[x][y] = calcEnergyFor(x, y);

        return energy[x][y];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (!energyAlreadyCalculatedOnce)
            calcEnergy();

        ShortestPathTopologicalSort spts = new ShortestPathTopologicalSort(energy);

        if (!transposed) {
            return spts.findPath(HORIZONTAL);
        }
        else {
            return spts.findPath(VERTICAL);
        }
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (!energyAlreadyCalculatedOnce)
            calcEnergy();

        ShortestPathTopologicalSort spts = new ShortestPathTopologicalSort(energy);

        if (!transposed) {
            return spts.findPath(VERTICAL);
        }
        else {
            return spts.findPath(HORIZONTAL);
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

        removeSeam(seam, HORIZONTAL);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

        removeSeam(seam, VERTICAL);
    }

    // copy picture into int matrix
    private int[][] copyPicIntoArr(Picture picture) {

        int[][] newPicArr = new int[width][height];

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                newPicArr[x][y] = picture.getRGB(x, y);

        return newPicArr;
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

        int[][] tempMatricPicture = new int[height][width];
        double[][] tempMatricEnergy = new double[height][width];

        for (int x = 0; x < height; x++)
            for (int y = 0; y < width; y++) {
                tempMatricPicture[x][y] = picArr[y][x];
                tempMatricEnergy[x][y] = energy[y][x];
            }

        int temp;
        temp = width;
        width = height;
        height = temp;

        picArr = tempMatricPicture;
        energy = tempMatricEnergy;
        transposed = !transposed;
    }

    // helper function for removal
    private void removeSeam(int[] seam, boolean orientaion) {

        if (!energyAlreadyCalculatedOnce){
            calcEnergy();
        }

        if (orientaion == HORIZONTAL) {
            if (transposed)
                transpose();
        }
        else {
            if (!transposed)
                transpose();
        }

        // everything from now on is just horizontal seam
        validateHorSeam(seam);

        height--;

        int[][] picArrCopy = new int[width][height];

        for (int x = 0; x < width; x++) {
            System.arraycopy(picArr[x], 0, picArrCopy[x], 0, seam[x]);
            System.arraycopy(picArr[x], seam[x] + 1, picArrCopy[x], seam[x], height - seam[x]);
        }

        picArr = picArrCopy;
        reCalcHorEnergy(seam);
    }

    // helper functions for calculate the energy function
    // calculate energy for entire picture first time
    private void calcEnergy() {
        if (energyAlreadyCalculatedOnce)
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
        energyAlreadyCalculatedOnce = true;
    }

    private double calcEnergyFor(int x, int y) {
        if (inCorner(x, y))
            return 1000.0;
        else
            return Math.sqrt(calcGradSqX(x, y) + calcGradSqY(x, y));
    }

    // recalculating energy function
    private void reCalcHorEnergy(int[] seam) {

        double[][] tempEnergy = new double[width][height];

        for (int x = 0; x < width; x++) {
            System.arraycopy(energy[x], 0, tempEnergy[x], 0, seam[x]);
            System.arraycopy(energy[x], seam[x] + 1, tempEnergy[x], seam[x], height - seam[x]);
        }

        energy = tempEnergy;

        for (int x = 0; x < width; x++) {
            if (inPicture(x, seam[x] - 1)) {

                    energy[x][seam[x] - 1] = calcEnergyFor(x, seam[x] - 1);
            }
            if (inPicture(x, seam[x])) {

                    energy[x][seam[x]] = calcEnergyFor(x, seam[x]);
            }
        }
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
        int rgbNag = picArr[x - 1][y];

        int rNeg = (rgbNag >> 16) & 0x00FF;
        int gNeg = (rgbNag >> 8) & 0x00FF;
        int bNeg = rgbNag & 0x00FF;

        // getting the colour of pixel x + 1
        int rgbPos = picArr[x + 1][y];

        int rPos = (rgbPos >> 16) & 0x00FF;
        int gPos = (rgbPos >> 8) & 0x00FF;
        int bPos = rgbPos & 0x00FF;

        return Math.pow(rPos - rNeg, 2) + Math.pow(gPos - gNeg, 2) + Math.pow(bPos - bNeg, 2);
    }

    private double calcGradSqY(int x, int y) {
        // getting the colour of pixel y - 1
        int rgbNag = picArr[x][y - 1];

        int rNeg = (rgbNag >> 16) & 0x00FF;
        int gNeg = (rgbNag >> 8) & 0x00FF;
        int bNeg = rgbNag & 0x00FF;

        // getting the colour of pixel y + 1
        int rgbPos = picArr[x][y + 1];

        int rPos = (rgbPos >> 16) & 0x00FF;
        int gPos = (rgbPos >> 8) & 0x00FF;
        int bPos = rgbPos & 0x00FF;

        return Math.pow(rPos - rNeg, 2) + Math.pow(gPos - gNeg, 2) + Math.pow(bPos - bNeg, 2);
    }

    // validation
    private boolean inPicture(int x, int y) {
        if (x < 0 || x >= width)
            return false;
        if (y < 0 || y >= height)
            return false;

        return true;
    }

    private void validateHorSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("seam can not be null");
        if (height == 1)
            throw new IllegalArgumentException("the picture is only one pixel");

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

        int height = 6, width = 6;

        int[][] arr = new int[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                arr[j][i] = Integer.parseInt(StdIn.readString(), 16);
            }
        }

        Picture picture = new Picture(width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                picture.setRGB(x, y, arr[x][y]);

        SeamCarver carver = new SeamCarver(picture);
        int[] arr1 = { 3, 3, 3, 4, 3, 3 };
        carver.removeVerticalSeam(arr1);
        int[] arr2 = {0, 0, 0, 0, 0, 0};
        carver.removeVerticalSeam(arr2);
        int[] arr3 = {0, 0, 0, 1};
        carver.removeHorizontalSeam(arr3);
        int [] arr4 = carver.findVerticalSeam();

    }


}
