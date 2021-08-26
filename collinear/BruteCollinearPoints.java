/* *****************************************************************************
 *  Name: mohamed ashraf
 *  Date: 26/8/2021
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> arrListLS = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points


        if (points == null)
            throw new IllegalArgumentException("the array connot be nulled");

        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                throw new IllegalArgumentException("points connot be nulled");

        //no mutation for the original points array
        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            pointsCopy[i] = points[i];

        Arrays.sort(pointsCopy);
        //checking for duplication
        for (int i = 0; i < pointsCopy.length - 1; i++)
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0)
                throw new IllegalArgumentException("duplicated points are not allowed");

        for (int i = 0; i < pointsCopy.length - 3; i++) {

            for (int j = i + 1; j < pointsCopy.length - 2; j++) {

                for (int k = j + 1; k < pointsCopy.length - 1; k++) {

                    if (pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i]
                            .slopeTo(pointsCopy[k])) {

                        for (int l = k + 1; l < pointsCopy.length; l++) {

                            if (pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i]
                                    .slopeTo(pointsCopy[l]))
                                arrListLS.add(new LineSegment(pointsCopy[i], pointsCopy[l]));
                        }
                    }
                }
            }
        }
    }


    public int numberOfSegments() {        // the number of line segments
        return arrListLS.size();
    }

    public LineSegment[] segments() {                // the line segments
        LineSegment[] arrLS = new LineSegment[arrListLS.size()];
        for (int i = 0; i < arrListLS.size(); i++) {
            arrLS[i] = arrListLS.get(i);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
