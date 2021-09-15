/* *****************************************************************************
 *  Name: mohamed ashraf farouk
 *  Date: 13/9/2021
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;

public class KdTree {
    private Node root;
    private int size;

    private static final boolean VERTICAL = false;
    private static final boolean HORIZONTAL = true;

    private static class Node {
        private Point2D point;
        private Node right_top;
        private Node left_bottom;

        private Node(Point2D point) {
            this.point = point;
        }
    }

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("the inserted point can not be nulled");
        root = insert(root, p, VERTICAL);
    }

    // insert helper functions
    private Node insert(Node node, Point2D p, boolean orientation) {
        if (node == null) {
            size++;
            return new Node(p);
        }

        int cmpX = Double.compare(p.x(), node.point.x());
        int cmpY = Double.compare(p.y(), node.point.y());

        if (cmpX == 0 && cmpY == 0)
            return node;

        if (orientation == VERTICAL) {
            if (cmpX < 0)
                node.left_bottom = insert(node.left_bottom, p, HORIZONTAL);
            else
                node.right_top = insert(node.right_top, p, HORIZONTAL);
        }
        else {
            if (cmpY < 0)
                node.left_bottom = insert(node.left_bottom, p, VERTICAL);
            else
                node.right_top = insert(node.right_top, p, VERTICAL);
        }
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("the point can not be nulled");

        if (isEmpty())
            return false;

        return contains(root, p, VERTICAL);
    }

    private boolean contains(Node node, Point2D p, boolean orientation) {
        if (node == null)
            return false;

        int cmpX = Double.compare(p.x(), node.point.x());
        int cmpY = Double.compare(p.y(), node.point.y());

        if (cmpX == 0 && cmpY == 0)
            return true;

        if (orientation == VERTICAL) {
            if (cmpX < 0)
                return contains(node.left_bottom, p, HORIZONTAL);
            else
                return contains(node.right_top, p, HORIZONTAL);
        }
        else {
            if (cmpY < 0)
                return contains(node.left_bottom, p, VERTICAL);
            else
                return contains(node.right_top, p, VERTICAL);
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, VERTICAL, 0, 0, 1, 1);
    }

    private void draw(Node node, boolean orientation,
                      double limitMinX, double limitMinY, double limitMaxX, double limitMaxY) {
        if (node == null)
            return;

        double nodeX = node.point.x();
        double nodeY = node.point.y();

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(nodeX, nodeY);

        if (orientation == VERTICAL) {

            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(nodeX, limitMinY, nodeX, limitMaxY);

            // cut the borders of the point range
            // upper node
            draw(node.left_bottom, HORIZONTAL, limitMinX, limitMinY, nodeX, limitMaxY);
            // lower node
            draw(node.right_top, HORIZONTAL, nodeX, limitMinY, limitMaxX, limitMaxY);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(limitMinX, nodeY, limitMaxX, nodeY);

            // cut the borders of the point range
            // upper node
            draw(node.left_bottom, VERTICAL, limitMinX, limitMinY, limitMaxX, nodeY);
            // lower node
            draw(node.right_top, VERTICAL, limitMinX, nodeY, limitMaxX, limitMaxY);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("the rectangle can not be nulled");

        Queue<Point2D> point2DQueue = new Queue<Point2D>();
        return range(root, rect, VERTICAL, point2DQueue);
    }

    private Queue<Point2D> range(Node node, RectHV rect, boolean orientation,
                                 Queue<Point2D> point2DQueue) {
        if (node == null)
            return point2DQueue;

        double nodeX = node.point.x();
        double nodeY = node.point.y();
        // the point is in the rectangle?
        // Y -> add it to the queue
        if (nodeX >= rect.xmin() && nodeX <= rect.xmax()
                && nodeY >= rect.ymin() && nodeY <= rect.ymax())
            point2DQueue.enqueue(node.point);

        if (orientation == VERTICAL) {
            // the rectangle cuts the node border
            if (nodeX >= rect.xmin() && nodeX <= rect.xmax()) {
                range(node.left_bottom, rect, HORIZONTAL, point2DQueue);
                range(node.right_top, rect, HORIZONTAL, point2DQueue);
            }
            // the rectangle is in the left subtree
            else if (nodeX > rect.xmax())
                range(node.left_bottom, rect, HORIZONTAL, point2DQueue);
                // the rectangle is in the right subtree
            else if (nodeX < rect.xmin())
                range(node.right_top, rect, HORIZONTAL, point2DQueue);
        }
        else {
            // the rectangle cuts the node border
            if (nodeY >= rect.ymin() && nodeY <= rect.ymax()) {
                range(node.left_bottom, rect, VERTICAL, point2DQueue);
                range(node.right_top, rect, VERTICAL, point2DQueue);
            }
            // the rectangle is in the lower subtree
            else if (nodeY > rect.ymax())
                range(node.left_bottom, rect, VERTICAL, point2DQueue);
                // the rectangle is in the upper subtree
            else if (nodeY < rect.ymin())
                range(node.right_top, rect, VERTICAL, point2DQueue);
        }
        return point2DQueue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("the point can not be nulled");

        return nearest(root, p, VERTICAL, null,
                       new RectHV(0.0, 0.0, 1.0, 1.0));
    }

    private Point2D nearest(Node node, Point2D point, boolean orientation, Point2D nearestPoint,
                            RectHV rect) {
        if (node == null)
            return nearestPoint;

        // if the node is the root take it as the nearest point so far
        boolean checkLB, checkRT;
        double shortestDst;
        if (node == root) {
            shortestDst = point.distanceSquaredTo(node.point);
            nearestPoint = node.point;
        }
        // determine the shortest distance so far
        else {
            shortestDst = point.distanceSquaredTo(nearestPoint);
        }

        // this.draw();
        // //StdDraw.clear();
        // StdDraw.setPenColor(StdDraw.BLUE);
        // StdDraw.setPenRadius(0.02);
        // StdDraw.point(node.point.x(), node.point.y());
        // StdDraw.show();
        // StdDraw.pause(500);

        // if shortest distance between point and rectangle is bigger that shortest distance return
        if (rect.distanceSquaredTo(point) > shortestDst)
            return nearestPoint;

        // update the point if you found a closer point
        if (point.distanceSquaredTo(node.point) < shortestDst)
            nearestPoint = node.point;


        // check the side of the query point
        boolean sideLB;
        if (orientation == VERTICAL)
            sideLB = point.x() <= node.point.x();
        else
            sideLB = point.y() <= node.point.y();

        // the main search on the point side
        if (orientation == VERTICAL) {
            if (sideLB) {
                RectHV rectLocal = new RectHV(rect.xmin(), rect.ymin(), node.point.x(),
                                              rect.ymax());
                nearestPoint = nearest(node.left_bottom, point, HORIZONTAL, nearestPoint,
                                       rectLocal);
                rectLocal = new RectHV(node.point.x(), rect.ymin(), rect.xmax(),
                                       rect.ymax());
                nearestPoint = nearest(node.right_top, point, HORIZONTAL, nearestPoint,
                                       rectLocal);
            }
            else {
                RectHV rectLocal = new RectHV(node.point.x(), rect.ymin(), rect.xmax(),
                                              rect.ymax());
                nearestPoint = nearest(node.right_top, point, HORIZONTAL, nearestPoint,
                                       rectLocal);
                rectLocal = new RectHV(rect.xmin(), rect.ymin(), node.point.x(),
                                       rect.ymax());
                nearestPoint = nearest(node.left_bottom, point, HORIZONTAL, nearestPoint,
                                       rectLocal);
            }
        }
        else {
            if (sideLB) {
                RectHV rectLocal = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(),
                                              node.point.y());
                nearestPoint = nearest(node.left_bottom, point, VERTICAL, nearestPoint,
                                       rectLocal);
                rectLocal = new RectHV(rect.xmin(), node.point.y(), rect.xmax(),
                                       rect.ymax());
                nearestPoint = nearest(node.right_top, point, VERTICAL, nearestPoint,
                                       rectLocal);
            }
            else {
                RectHV rectLocal = new RectHV(rect.xmin(), node.point.y(), rect.xmax(),
                                              rect.ymax());
                nearestPoint = nearest(node.right_top, point, VERTICAL, nearestPoint,
                                       rectLocal);
                rectLocal = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(),
                                       node.point.y());
                nearestPoint = nearest(node.left_bottom, point, VERTICAL, nearestPoint,
                                       rectLocal);
            }
        }

        return nearestPoint;
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        //PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            //brute.insert(p);
        }

        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.point(0.1875, 1.0);
        StdOut.println(kdtree.nearest(new Point2D(0.1875, 1.0)));
    }
}
