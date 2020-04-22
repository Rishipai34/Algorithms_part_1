import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lines;
    private int linesize = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        Point[] p = new Point[points.length];
        Point[] q = new Point[points.length];
        LineSegment[] linestemp = new LineSegment[points.length];
        for (Point element : points)
            for (Point item : points)
                for (Point value : points)
                    for (Point point : points)
                        if ((point.compareTo(element) != 0) && (point.compareTo(value) != 0) && (point.compareTo(item) != 0) && (item.compareTo(value) != 0) && (element.compareTo(value) != 0) && (item.compareTo(element) != 0)) {
                            Point[] points1 = new Point[4];
                            points1[0] = element;
                            points1[1] = item;
                            points1[2] = value;
                            points1[3] = point;
                            Arrays.sort(points1);
                            double slope1 = points1[0].slopeTo(points1[1]);
                            double slope3 = points1[2].slopeTo(points1[3]);
                            double slope2 = points1[1].slopeTo(points1[2]);
                            if ((slope1 == slope2) && (slope1 == slope3))
                                if (!Arrays.asList(p).contains(points[3]) && !Arrays.asList(q).contains(points[0]) && !Arrays.asList(q).contains(points[3]) && !Arrays.asList(p).contains(points[0])) {
                                        linestemp[linesize] = new LineSegment(points1[0], points1[3]);
                                        p[linesize] = points1[0];
                                        q[linesize++] = points1[3];
                                }

                        }
        lines = new LineSegment[linesize];
        System.arraycopy(linestemp, 0, lines, 0, linesize);

    }

    // the number of line segments
    public int numberOfSegments() {
        return linesize;
    }

    // Returns the line segments
    public LineSegment[] segments() {
        return lines;
    }
}
