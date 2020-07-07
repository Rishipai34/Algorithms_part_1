import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private NodeOfTree root;
    private int size;
    private final RectHV region;

    private static class NodeOfTree {
        private NodeOfTree left, right;
        private final boolean isVertical;
        private Point2D val;
        private final Point2D key;
        private RectHV rectOfNode;

        NodeOfTree(Point2D key, boolean isVertical, RectHV rectOfNode) {
            this.key = key;
            this.val = key;
            this.isVertical = isVertical;
            this.rectOfNode = rectOfNode;
            this.left = null;
            this.right = null;
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
        root = put(root, p, true, region);
    }

    private NodeOfTree put(NodeOfTree p, Point2D key, boolean isVertical, RectHV rect) {
        if (p == null) {
            size++;
            return new NodeOfTree(key, isVertical, rect);
        } else if ((p.key.x() == key.x()) && (p.key.y() == key.y())) {
            p.val = key;
        } else if ((p.isVertical && (p.key.x() <= key.x())) || (!p.isVertical && (p.key.y() <= key.y()))) {
            p.right = put(p.right, key, !isVertical, rectRightOrUp(p));
        } else {
            p.left = put(p.left, key, !isVertical, rectLeftOrDown(p));
        }
        return p;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException(" Point passed cannot be null ");
        }
        NodeOfTree searchNode = root;
        while (searchNode != null) {
            if (searchNode.key.x() == p.x() && searchNode.key.y() == p.y()) {
                return true;
            } else if ((searchNode.isVertical && searchNode.key.x() > p.x()) || (!searchNode.isVertical && searchNode.key.y() > p.y())) {
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

    private void draw(NodeOfTree nod, RectHV rect) {
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

            draw(nod.left, rectLeftOrDown(nod));
            draw(nod.right, rectRightOrUp(nod));
        }

    }

    private RectHV rectRightOrUp(NodeOfTree nod) {
        RectHV rect = nod.rectOfNode;
        if (nod.isVertical) {
            return new RectHV(nod.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
        }
        return new RectHV(rect.xmin(), nod.key.y(), rect.xmax(), rect.ymax());
    }

    private RectHV rectLeftOrDown(NodeOfTree nod) {
        RectHV rect = nod.rectOfNode;
        if (nod.isVertical) {
            return new RectHV(rect.xmin(), rect.ymin(), nod.key.x(), rect.ymax());
        }
        return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), nod.key.y());
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException(" The argument of KdTree.range cannot be null ");
        } else {
            Stack<Point2D> points = new Stack<Point2D>();
            root = getPoints(root, rect, points);
            return points;
        }
    }

    private NodeOfTree getPoints(NodeOfTree point, RectHV queryRect, Stack<Point2D> points) {
        if (point != null) {
            if (point.rectOfNode.intersects(queryRect)) {
                if (queryRect.contains(point.val)) {
                    points.push(point.val);
                }
                getPoints(point.right, queryRect, points);
                getPoints(point.left, queryRect, points);
            }
        }
        return point;
    }

    private Point2D findNear(NodeOfTree p, Point2D queryPoint, Point2D closePoint) {
        Point2D closest = closePoint;
        if (p != null) {
            if ((closest == null) || (queryPoint.distanceSquaredTo(closest) >= p.rectOfNode.distanceSquaredTo(queryPoint))) {
                if (closest == null)
                    closest = p.key;
                if (queryPoint.distanceSquaredTo(closest) > queryPoint.distanceSquaredTo(p.key))
                    closest = p.key;
                if (p.isVertical) {
                    if (p.key.x() > queryPoint.x()) {
                        closest = findNear(p.left, queryPoint, closest);
                        closest = findNear(p.right, queryPoint, closest);
                    } else {
                        closest = findNear(p.right, queryPoint, closest);
                        closest = findNear(p.left, queryPoint, closest);
                    }
                } else {

                    if (p.key.y() > queryPoint.y()) {
                        closest = findNear(p.left, queryPoint, closest);
                        closest = findNear(p.right, queryPoint, closest);
                    } else {
                        closest = findNear(p.right, queryPoint, closest);
                        closest = findNear(p.left, queryPoint, closest);
                    }
                }
            }
        }
        return closest;

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("The argument to KdTree.nearest cannot be null ");
        }
        return findNear(root, p, null);
    }

}
