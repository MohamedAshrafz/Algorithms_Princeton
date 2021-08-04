import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */
public class Percolation {
    private final int n;
    private int numberOfOpenSites;
    private final int upperPoint, lowerPoint;
    private final WeightedQuickUnionUF qu;
    private boolean[] openArr;
    // creates n-by-n grid, with all sites initially blocked
    //with virtual point out of the measured matrix
    /*public Percolation(int n){
        uppersite = n*n;
        downsite = n*n + 1;
        qu = new WeightedQuickUnionUF(n*n + 2);
        qu.union(0,uppersite);
        qu.union(n*n-1, downsite);

    }*/
    //constructor
    public Percolation(int n){
        if (n <= 0)
            throw new IllegalArgumentException("you can not use 0 or less values for n");
        this.n = n;
        upperPoint = 0;
        lowerPoint = n*n+1;
        qu = new WeightedQuickUnionUF(n*n+2);
        openArr = new boolean[n*n+1];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if (!exist(row, col))
            throw new IllegalArgumentException("row or col out of legal region");

        if(!isOpen(row, col)) {
            //get the point and all the adjecant
            int position = getPosition(row, col),
            up = getPosition(row - 1, col),
                    down = getPosition(row + 1, col),
                    left = getPosition(row, col - 1),
                    right = getPosition(row, col + 1);

            //opening position
            openArr[position] = true;
            numberOfOpenSites++;

            //union the position with near positions
            //first row
            if (position <= n)
                qu.union(upperPoint, position);
            //last row
            /*if (!percolates() && position > n * n - n)
                qu.union(lowerPoint, position);*/
            //up
            if (up != -1 && openArr[up])
                qu.union(up, position);
            //down
            if (down != -1 && openArr[down])
                qu.union(down, position);
            //left
            if (left != -1 && openArr[left])
                qu.union(left, position);
            //right
            if (right != -1 && openArr[right])
                qu.union(right, position);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if (!exist(row, col))
            throw new IllegalArgumentException("row or col out of legal region");
        if (openArr[getPosition(row, col)])
            return true;
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if (!exist(row, col))
            throw new IllegalArgumentException("row or col out of legal region");
        return isOpen(row,col) && qu.find(getPosition(row, col)) == qu.find(upperPoint);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates(){
        for (int i = n*n; i>=(n*n-n+1); i--){
            if(qu.find(i) == qu.find(upperPoint))
                return true;
        }
        //return qu.find(upperPoint) == qu.find(lowerPoint);
        return false;
    }

    //get the position in quick union array from any given matrix
    //position in qu is n*(row-10)+(column-1)
    //notice the matrix starts from 1 but the array indices start from 0
    private int getPosition(int row, int col){
        if(exist(row, col))
            return n*(row-1) + col;
        return -1;
    }

    //is the point really exist in the matrix?
    private boolean exist(int row, int col){
        return row > 0 && col > 0 && row <= n && col <= n;
    }

    // test client (optional)
    /* public static void main(String[] args) {

    } */
}
