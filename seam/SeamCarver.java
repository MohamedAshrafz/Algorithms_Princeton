/* *****************************************************************************
 *  Name: mohamed ashraf farouk
 *  Date: 23/10/2021
 *  Description: seam carving implementation
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {

    private Picture picture;
    private double[][] energy;
    private boolean energyAlreadyCalculated;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        energy = new double[picture.width()][picture.height()];
        energyAlreadyCalculated = false;
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateX(x);
        validateY(y);

        if (!energyAlreadyCalculated)
            calcEnergy();

        energyAlreadyCalculated = true;

        return energy[x][y];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return new int[2];
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return new int[2];
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("seam can not be null");
        if (picture.width() == 1)
            throw new IllegalArgumentException("can not remove this seam");
        energyAlreadyCalculated = false;
        reCalcHorEnergy(seam);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("seam can not be null");
        if (picture.width() == 1)
            throw new IllegalArgumentException("can not remove this seam");
    }

    // helper functions for calculate the energy function
    // calculate energy for first time
    private void calcEnergy() {
        // set corners energy
        cornersEnegy();

        // calc normal energy for first time
        for (int x = 1; x < picture.width() - 1; x++) {
            for (int y = 1; y < picture.height() - 1; y++) {
                energy[x][y] = Math.sqrt(calcGradSqX(x, y) + calcGradSqY(x, y));
            }
        }
        // update the flag to already-calculated
        energyAlreadyCalculated = true;
    }

    // recalculating energy function
    private void reCalcVerEnergy(int[] seam) {
        for (int y = 1; y < picture.height() - 1; y++) {

            if (inCorner(seam[y] - 1, y))
                energy[seam[y] - 1][y] = 1000.0;
            else {
                energy[seam[y] - 1][y] = Math.sqrt(calcGradSqX(seam[y] - 1, y)
                                                           + calcGradSqY(seam[y] - 1, y));
            }
            if (inCorner(seam[y] + 1, y))
                energy[seam[y] + 1][y] = 1000.0;
            else {
                energy[seam[y] + 1][y] = Math.sqrt(calcGradSqX(seam[y] + 1, y)
                                                           + calcGradSqY(seam[y] + 1, y));
            }
        }
        // update the flag to already-calculated
        energyAlreadyCalculated = true;
    }

    private void reCalcHorEnergy(int[] seam) {
        for (int x = 1; x < picture.width() - 1; x++) {

            if (inCorner(x, seam[x] - 1))
                energy[x][seam[x] - 1] = 1000.0;
            else {
                energy[x][seam[x] - 1] = Math.sqrt(calcGradSqX(x, seam[x] - 1)
                                                           + calcGradSqY(x, seam[x] - 1));
            }
            if (inCorner(x, seam[x] + 1))
                energy[x][seam[x] + 1] = 1000.0;
            else {
                energy[x][seam[x] + 1] = Math.sqrt(calcGradSqX(x, seam[x] + 1)
                                                           + calcGradSqY(x, seam[x] + 1));
            }
        }
        // update the flag to already-calculated
        energyAlreadyCalculated = true;
    }

    // get corners energy
    private void cornersEnegy() {
        // set the corners energy = 1000
        for (int x = 0; x < picture.width(); x++) {
            energy[x][0] = 1000;
            energy[x][picture.height() - 1] = 1000;
        }
        for (int y = 1; y < picture.height() - 1; y++) {
            energy[0][y] = 1000;
            energy[picture.width() - 1][y] = 1000;
        }
    }

    private double calcGradSqX(int x, int y) {
        // getting the colour of pixel x - 1
        int rgb = picture.getRGB(x - 1, y);

        int rNeg = (rgb >> 16) & 0x00FF;
        int gNeg = (rgb >> 8) & 0x00FF;
        int bNeg = rgb & 0x00FF;

        // getting the colour of pixel x + 1
        rgb = picture.getRGB(x + 1, y);

        int rPos = (rgb >> 16) & 0x00FF;
        int gPos = (rgb >> 8) & 0x00FF;
        int bPos = rgb & 0x00FF;

        return Math.pow(rPos - rNeg, 2) + Math.pow(gPos - gNeg, 2) + Math.pow(bPos - bNeg, 2);
    }

    private double calcGradSqY(int x, int y) {
        // getting the colour of pixel y - 1
        int rgb = picture.getRGB(x, y - 1);

        int rNeg = (rgb >> 16) & 0x00FF;
        int gNeg = (rgb >> 8) & 0x00FF;
        int bNeg = rgb & 0x00FF;

        // getting the colour of pixel y + 1
        rgb = picture.getRGB(x, y + 1);

        int rPos = (rgb >> 16) & 0x00FF;
        int gPos = (rgb >> 8) & 0x00FF;
        int bPos = rgb & 0x00FF;

        return Math.pow(rPos - rNeg, 2) + Math.pow(gPos - gNeg, 2) + Math.pow(bPos - bNeg, 2);
    }

    // validation
    private void validateX(int x) {
        if (x < 0 || x >= picture.width())
            throw new IllegalArgumentException("x provided is illegal");
    }

    private void validateY(int y) {
        if (y < 0 || y >= picture.height())
            throw new IllegalArgumentException("y provided is illegal");
    }

    private boolean inCorner(int x, int y) {
        if (x == 0 || x == picture.width() - 1)
            return true;
        if (y == 0 || y == picture.height() - 1)
            return true;

        return false;
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("image is %d pixels wide by %d pixels high.\n", picture.width(), picture.height());

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
    }

}
