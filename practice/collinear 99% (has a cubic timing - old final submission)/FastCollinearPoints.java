/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> arrayListLS;


    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        arrayListLS = new ArrayList<LineSegment>();
        ArrayList<Point> startPionts = new ArrayList<Point>();
        ArrayList<Point> endPoints = new ArrayList<Point>();
        ArrayList<Double> slopes = new ArrayList<Double>();

        if (points == null)
            throw new IllegalArgumentException("the array connot be nulled");

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("points connot be nulled");
        }

        //no mutation for the original points array
        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            pointsCopy[i] = points[i];

        Point[] pointsCopy2 = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            pointsCopy2[i] = points[i];

        //sorting then making a copy for slopeOrder sorting
        Arrays.sort(pointsCopy);
        //checking for points duplication
        for (int i = 0; i < pointsCopy.length - 1; i++)
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0)
                throw new IllegalArgumentException("duplicated points are not allowed");

        for (int i = 0; i < pointsCopy.length - 1; i++) {
            boolean flag = false;
            Arrays.sort(pointsCopy, i + 1, pointsCopy.length, pointsCopy[i].slopeOrder());

            int noSuccessiveEqualSlopes = 0;
            int j;
            double tempSlope = pointsCopy[i].slopeTo(pointsCopy[i + 1]);
            for (j = i + 2; j < pointsCopy.length; j++) {

                flag = false;

                if (pointsCopy[i].slopeTo(pointsCopy[j]) == tempSlope) {
                    noSuccessiveEqualSlopes++;
                }
                else {
                    if (noSuccessiveEqualSlopes >= 2) {
                        for (int x = i - 1; x >= 0; x--)
                            if (pointsCopy[i].slopeTo(pointsCopy[x]) == tempSlope) {
                                flag = true;
                                break;
                            }
                    }
                    //if the series broke and there is 3 equal slopes at least
                    //there are more points to check
                    if (noSuccessiveEqualSlopes >= 2 && !flag) {
                        arrayListLS.add(new LineSegment(pointsCopy[i], pointsCopy[j - 1]));
                    }
                    //no successive equal slopes
                    noSuccessiveEqualSlopes = 0;
                    tempSlope = pointsCopy[i].slopeTo(pointsCopy[j]);
                }

            }
            if (noSuccessiveEqualSlopes >= 2) {
                for (int x = i - 1; x >= 0; x--)
                    if (pointsCopy[i].slopeTo(pointsCopy[x]) == tempSlope) {
                        flag = true;
                        break;
                    }
            }
            //all points checked and there is one series of successive equal slopes
            if (noSuccessiveEqualSlopes >= 2 && !flag) {
                arrayListLS.add(new LineSegment(pointsCopy[i], pointsCopy[j - 1]));
            }
            Arrays.sort(pointsCopy);
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return arrayListLS.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] arrLS = new LineSegment[arrayListLS.size()];
        for (int i = 0; i < arrayListLS.size(); i++) {
            arrLS[i] = arrayListLS.get(i);
        }
        return arrLS;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
