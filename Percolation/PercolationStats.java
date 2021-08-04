/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final double[] fractionArr;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if( n<=0 || trials<=0 )
            throw new IllegalArgumentException("n and number of trials can not be 0 or less");
        this.trials = trials;
        int numOfSitedOpened;
        fractionArr = new double[trials];
        int randomRow;
        int randomCol;
        Percolation pero;
        for (int i = 0; i < trials; i++) {
            pero = new Percolation(n);
            numOfSitedOpened = 0;
            while(!pero.percolates()) {
                randomRow = StdRandom.uniform(1, n+1);
                randomCol = StdRandom.uniform(1, n+1);
                if(!pero.isOpen(randomRow, randomCol)) {
                    pero.open(randomRow, randomCol);
                    numOfSitedOpened++;
                }
            }
            fractionArr [i] = ((double) numOfSitedOpened / (double)(n*n));
        }
    }

   // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(fractionArr);
    }
    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(fractionArr);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return this.mean() - ((1.96 * this.stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return this.mean() + ((1.96 * this.stddev()) / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        PercolationStats PS = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + PS.mean());
        StdOut.println("stddev                  = " + PS.stddev());
        StdOut.println("95% confidence interval = [" + PS.confidenceLo() + ", " + PS.confidenceHi() + "]");
    }
}
