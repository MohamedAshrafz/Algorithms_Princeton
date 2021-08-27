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

        if (points == null)
            throw new IllegalArgumentException("the array connot be nulled");

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("points connot be nulled");
        }

        //no mutation for the original points array
        // Point[] pointsCopy = new Point[points.length];
        // for (int i = 0; i < points.length; i++)
        //     pointsCopy[i] = points[i];

        Point[] pointsCopy2 = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            pointsCopy2[i] = points[i];

        //sorting then making a copy for slopeOrder sorting
        Arrays.sort(pointsCopy2);
        //checking for points duplication
        for (int i = 0; i < pointsCopy2.length - 1; i++)
            if (pointsCopy2[i].compareTo(pointsCopy2[i + 1]) == 0)
                throw new IllegalArgumentException("duplicated points are not allowed");


        for (int i = 0; i < pointsCopy2.length - 1; i++) {

            Arrays.sort(pointsCopy2, pointsCopy2[i].slopeOrder());

            int noSuccessiveEqualSlopes = 0;
            double prevSlope = pointsCopy2[0].slopeTo(pointsCopy2[1]);
            Point startPoint = pointsCopy2[1];
            int j;
            for (j = 1; j < pointsCopy2.length - 1; j++) {

                if (pointsCopy2[0].slopeTo(pointsCopy2[j + 1]) == prevSlope)
                    noSuccessiveEqualSlopes++;

                else if (noSuccessiveEqualSlopes >= 2) {
                    startPionts.add(startPoint);
                    endPoints.add(pointsCopy2[j]);

                    startPoint = pointsCopy2[j + 1];
                    noSuccessiveEqualSlopes = 0;
                    prevSlope = pointsCopy2[0].slopeTo(pointsCopy2[j + 1]);
                }
                else {
                    startPoint = pointsCopy2[j + 1];
                    noSuccessiveEqualSlopes = 0;
                    prevSlope = pointsCopy2[0].slopeTo(pointsCopy2[j + 1]);
                }
            }
            if (noSuccessiveEqualSlopes >= 2) {
                startPionts.add(startPoint);
                endPoints.add(pointsCopy2[j - 1]);
            }

            for (int k = 0; k < startPionts.size(); k++)
                if (pointsCopy2[0].compareTo(startPionts.get(k)) < 0)
                    arrayListLS.add(new LineSegment(pointsCopy2[0], endPoints.get(k)));

            Arrays.sort(pointsCopy2);
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
