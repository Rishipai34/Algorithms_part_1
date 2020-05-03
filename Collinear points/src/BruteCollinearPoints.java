import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lines;
    private int linesize = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        Point[] p = new Point[points.length];
        Point[] q = new Point[points.length];
        Point[] points1 = new Point[4];
        int linesize1 = 0;
        LineSegment[] linestemp = new LineSegment[points.length];
        Arrays.sort(points);
        for (Point element : points) {
            if (linesize1 != linesize ){
                points1[0] = null;
                points1[1] = null;
                points1[2] = null;
                points1[3] = null;
                linesize1 = linesize;
            }
            points1[0] = element;
            for (Point item : points) {
                if (element.compareTo(item) != 0) {
                    points1[1] = item;
                    double slope1 = points1[0].slopeTo(points1[1]);
                    for (Point value : points) {
                        if ((value.compareTo(element) != 0) && (value.compareTo(item) != 0)) {
                            points1[2] = value;
                            double slope2 = points1[1].slopeTo(points1[2]);
                            if (slope1 == slope2) {
                                for (Point point : points)
                                    if ((point.compareTo(element) != 0) && (point.compareTo(value) != 0) && (point.compareTo(item) != 0) && (item.compareTo(value) != 0) && (element.compareTo(value) != 0) && (item.compareTo(element) != 0)) {
                                        points1[3] = point;
                                        double slope3 = points1[2].slopeTo(points1[3]);
                                        if (slope2 == slope3)
                                            if (!Arrays.asList(p).contains(points1[3]) && !Arrays.asList(q).contains(points1[0]) && !Arrays.asList(q).contains(points1[3]) && !Arrays.asList(p).contains(points1[0])) {
                                                linestemp[linesize] = new LineSegment(points1[0], points1[3]);
                                                p[linesize] = points1[0];
                                                q[linesize++] = points1[3];
                                            }

                                    }
                            }
                        }
                    }
                }
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
