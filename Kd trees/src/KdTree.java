import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

import java.util.NoSuchElementException;

public class KdTree {
    private node root;
    private int size;
    private RectHV region;

    private static class node {
        private node left, right;
        private boolean isVertical;
        private Point2D val;
        private Point2D key;

        node(Point2D key, Point2D val, node left, node right, boolean isVertical) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
            this.isVertical = isVertical;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
        region = new RectHV(0.0, 0.0, 1.0, 1.0);
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException(" Passed point cannot be null");
        }
        root = put(root, p, p, true);
    }

    private node put(node p, Point2D key, Point2D val, boolean isVertical) {
        if (p == null) {
            size++;
            return new node(key, val, null, null, isVertical);
        } else if ((p.key.x() == key.x()) && (p.key.y() == key.y())) {
            p.val = val;
        } else if ((p.isVertical && (p.key.x() >= key.x())) || (!p.isVertical && (p.key.y() >= key.y()))) {
            p.right = put(p.right, key, val, !isVertical);
        } else {
            p.left = put(p.left, key, val, !isVertical);
        }
        return p;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException(" Point passed cannot be null ");
        }
        node searchNode = root;
        while (searchNode != null) {
            if (searchNode.key.x() == p.x() && searchNode.key.y() == p.y()) {
                return true;
            } else if ((searchNode.isVertical && searchNode.key.x() >= p.x()) || (!searchNode.isVertical && searchNode.key.y() >= p.y())) {
                searchNode = searchNode.left;
            } else {
                searchNode = searchNode.right;
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setScale(0, 1);
        draw(root, region);
    }

    private void draw(node nod, RectHV rect) {
        if (nod != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.02);
            new Point2D(nod.key.x(), nod.key.y()).draw();
            StdDraw.setPenRadius();

            if (nod.isVertical) {
                StdDraw.setPenColor(StdDraw.RED);
                new Point2D(nod.key.x(), rect.ymin()).drawTo(new Point2D(nod.key.x(), rect.ymax()));
            } else {
                StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                new Point2D(rect.xmin(), nod.key.y()).drawTo(new Point2D(rect.xmax(), nod.key.y()));
            }

            draw(nod.left, RectHigh(nod, rect));
            draw(nod.right, RectLow(nod, rect));
        }

    }

    private RectHV RectHigh(node nod, RectHV rect) {
        if (nod.isVertical) {
            return new RectHV(nod.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
        }
        return new RectHV(rect.xmin(), nod.key.y(), rect.xmax(), rect.ymax());
    }

    private RectHV RectLow(node nod, RectHV rect) {
        if (nod.isVertical) {
            return new RectHV(rect.xmin(), rect.ymin(), nod.key.x(), rect.ymax());
        }
        return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), nod.key.y());
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException(" The argument of KdTree.range cannot be null ");
        } else if (root == null) {
            throw new NoSuchElementException(" There exists no elements in the tree ");
        } else {
            Stack<Point2D> points = new Stack<Point2D>();
            getPoints(root, region, rect, points);
            return points;
        }
    }

    private void getPoints(node point, RectHV toSearch, RectHV queryRect, Stack<Point2D> points) {
        if (point != null) {
            if (toSearch.intersects(queryRect)) {
                if (queryRect.contains(point.val)) points.push(point.val);
                getPoints(point.right, RectLow(point, toSearch), queryRect, points);
                getPoints(point.left, RectHigh(point, toSearch), queryRect, points);
            }
        }
    }

    private Point2D findNear(node start, Point2D queryPoint, RectHV toSearch, Point2D closePoint) {
        Point2D closest = closePoint;
        if (start != null) {
            if (closest == null || queryPoint.distanceSquaredTo(closest) > toSearch.distanceSquaredTo(closest)) {
                if (closest == null) {
                    closest = start.key;
                } else {
                    if (start.key.distanceSquaredTo(queryPoint) < closest.distanceSquaredTo(queryPoint)) {
                        closest = start.key;
                    }
                }

                if (start.isVertical) {
                    RectHV rightRect = new RectHV(toSearch.xmin(), toSearch.ymin(), start.key.x(), toSearch.ymax());
                    RectHV leftRect = new RectHV(start.key.x(), toSearch.ymin(), toSearch.xmax(), toSearch.ymax());
                    if (start.key.x() > queryPoint.x()) {
                        findNear(start.left, queryPoint, leftRect, closest);
                        findNear(start.right, queryPoint, rightRect, closest);
                    } else {
                        findNear(start.right, queryPoint, rightRect, closest);
                        findNear(start.left, queryPoint, leftRect, closest);
                    }
                }
                if (!start.isVertical) {
                    RectHV upRect = new RectHV(toSearch.xmin(), toSearch.ymin(), start.key.x(), toSearch.ymax());
                    RectHV downRect = new RectHV(start.key.x(), toSearch.ymin(), toSearch.xmax(), toSearch.ymax());
                    if (start.key.y() > queryPoint.y()) {
                        findNear(start.left, queryPoint, downRect, closest);
                        findNear(start.right, queryPoint, upRect, closest);
                    } else {
                        findNear(start.right, queryPoint, downRect, closest);
                        findNear(start.left, queryPoint, upRect, closest);
                    }
                }
            }
        }
        return closest;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return findNear(root, p, region, null);
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }

}
