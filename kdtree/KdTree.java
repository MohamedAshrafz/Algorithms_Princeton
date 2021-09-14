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
            else if (cmpX > 0)
                node.right_top = insert(node.right_top, p, HORIZONTAL);
            else
                node.right_top = insert(node.right_top, p, HORIZONTAL);

            return node;
        }
        else {
            if (cmpY < 0)
                node.left_bottom = insert(node.left_bottom, p, VERTICAL);
            else if (cmpY > 0)
                node.right_top = insert(node.right_top, p, VERTICAL);
            else
                node.right_top = insert(node.right_top, p, VERTICAL);

            return node;
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("the point can not be nulled");

        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        Node node = x;

        while (true) {
            int cmpX = Double.compare(p.x(), node.point.x());

            if (cmpX < 0)
                node = node.left_bottom;
            else if (cmpX > 0)
                node = node.right_top;
            else
                return true;

            if (node == null)
                break;

            int cmpY = Double.compare(p.y(), node.point.y());

            if (cmpY < 0)
                node = node.left_bottom;
            else if (cmpY > 0)
                node = node.right_top;
            else
                return true;

            if (node == null)
                break;
        }
        return false;
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

            draw(node.left_bottom, HORIZONTAL, limitMinX, limitMinY, nodeX, limitMaxY);
            draw(node.right_top, HORIZONTAL, nodeX, limitMinY, limitMaxX, limitMaxY);
        }
        else if (orientation == HORIZONTAL) {

            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(limitMinX, nodeY, limitMaxX, nodeY);

            draw(node.left_bottom, VERTICAL, limitMinX, limitMinY, limitMaxX, nodeY);
            draw(node.right_top, VERTICAL, limitMinX, nodeY, limitMaxX, limitMaxY);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> point2DQueue = new Queue<Point2D>();
        point2DQueue = range(root, rect, VERTICAL, point2DQueue);
        return point2DQueue;
    }

    private Queue<Point2D> range(Node node, RectHV rect, boolean orientation,
                                 Queue<Point2D> point2DQueue) {
        if (node == null)
            return point2DQueue;

        double nodeX = node.point.x();
        double nodeY = node.point.y();

        if (nodeX >= rect.xmin() && nodeX <= rect.xmax()
                && nodeY >= rect.ymin() && nodeY <= rect.ymax())
            point2DQueue.enqueue(node.point);

        if (orientation == VERTICAL) {
            if (nodeX >= rect.xmin() && nodeX <= rect.xmax()) {
                range(node.left_bottom, rect, HORIZONTAL, point2DQueue);
                range(node.right_top, rect, HORIZONTAL, point2DQueue);
            }
            else if (nodeX > rect.xmax())
                range(node.left_bottom, rect, HORIZONTAL, point2DQueue);
            else if (nodeX < rect.xmin())
                range(node.right_top, rect, HORIZONTAL, point2DQueue);
        }
        else {
            if (nodeY >= rect.ymin() && nodeY <= rect.ymax()) {
                range(node.left_bottom, rect, VERTICAL, point2DQueue);
                range(node.right_top, rect, VERTICAL, point2DQueue);
            }
            else if (nodeY > rect.ymax())
                range(node.left_bottom, rect, VERTICAL, point2DQueue);
            else if (nodeY < rect.ymin())
                range(node.right_top, rect, VERTICAL, point2DQueue);
        }
        return point2DQueue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return nearest(root, p);
    }

    private Point2D nearest(Node node, Point2D point){
        return new Point2D(0, 0);
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        kdtree.draw();
    }
}
