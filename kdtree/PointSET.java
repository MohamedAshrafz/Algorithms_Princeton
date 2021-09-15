/* *****************************************************************************
 *  Name: mohamed ashraf farouk
 *  Date: 13/9/2021
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {
    private SET<Point2D> pointsSet;

    // construct an empty set of points
    public PointSET() {
        pointsSet = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointsSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointsSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("the inserted point can not be nulled");

        pointsSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("the point can not be nulled");
        return pointsSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D x : pointsSet) {
            x.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("the rectangle can not be nulled");

        Queue<Point2D> point2DQueue = new Queue<Point2D>();

        for (Point2D point : pointsSet)
            if (rect.contains(point))
                point2DQueue.enqueue(point);

        return point2DQueue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("the point can not be nulled");

        if (this.isEmpty())
            return null;

        double shortestDistance = Double.POSITIVE_INFINITY;
        Point2D nearestPoint = new Point2D(0, 0);

        for (Point2D point : pointsSet)
            if (p.distanceSquaredTo(point) < shortestDistance) {
                shortestDistance = p.distanceSquaredTo(point);
                nearestPoint = point;
            }

        return new Point2D(nearestPoint.x(), nearestPoint.y());
    }

    public static void main(String[] args) {
        PointSET ps = new PointSET();

        ps.insert(new Point2D(0, 0));
        ps.insert(new Point2D(0.1, 0.1));
        ps.insert(new Point2D(0.2, 0.2));
        ps.insert(new Point2D(0.3, 0.3));
        ps.insert(new Point2D(0.5, 0.5));

        StdOut.println(ps.range(new RectHV(0, 0, 4, 5)));


        //StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        ps.draw();

    }
}
