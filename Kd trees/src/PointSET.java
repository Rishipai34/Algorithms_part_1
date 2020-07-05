import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException(" The argument for PointSET.insert cannot be null");
        } else {
            points.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException(" The argument for PointSET.contains cannot be null");
        } else {
            return points.contains(p);
        }
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D it : points) {
            it.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> contained = new Stack<Point2D>();
        if (rect == null) {
            throw new IllegalArgumentException(" The argument for PointSET.range cannot be null");
        } else {
            for (Point2D it : points) {
                if (rect.contains(it)) contained.push(it);
            }
            return contained;
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException(" The argument for PointSET.range cannot be null");
        } else {
            Point2D close = points.first();
            double distanceMin = close.distanceSquaredTo(p);
            for (Point2D it : points) {
                if (it.distanceSquaredTo(p) < distanceMin) {
                    distanceMin = it.distanceSquaredTo(p);
                    close = it;
                }
            }
            return close;
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}
