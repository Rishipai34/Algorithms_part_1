import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lines;
    private int linesize = 0;
    private Object[] linestemp;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (checkDuplicate(points)) {
            throw new IllegalArgumentException("Cannot have duplicate points.");
        }

        linestemp = new LineSegment[points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int y = k + 1; y < points.length; y++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[y];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s)) {
                            Point[] tuple = new Point[] {p, q, r, s};
                            Arrays.sort(tuple);
                            linestemp[linesize] = new LineSegment(tuple[0], tuple[3]);
                            linesize++;
                            if (linesize == linestemp.length) {
                                resize(linesize * 2);
                            }
                        }
                    }
                }
            }
        }
        lines = new LineSegment[linesize];
        for (int i = 0; i < linesize; i++) {
            lines[i] = (LineSegment) linestemp[i];
        }
    }

    // Checks the existence of duplicate points
    private boolean checkDuplicate(Point[] points) {
        if (points.length > 0) {
            Point[] pointsCopy = new Point[points.length];
            System.arraycopy(points, 0, pointsCopy, 0, points.length);
            Arrays.sort(pointsCopy);
            Point currentPoint = pointsCopy[0];
            for (int i = 1; i < pointsCopy.length; i++) {
                if (pointsCopy[i].compareTo(currentPoint) == 0) {
                    return true;
                } else {
                    currentPoint = pointsCopy[i];
                }
            }
            return false;
        } else {
            return false;
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return linesize;
    }

    // Returns the line segments
    public LineSegment[] segments() {
        return lines;
    }
    // Resize the array if required
    private void resize(int capacity) {
        assert capacity >= linesize;
        if (capacity > linesize) {
            Object[] temp = new Object[capacity];
            System.arraycopy(linestemp, 0, temp, 0, linesize);
            linestemp = temp;
        }
    }
}
