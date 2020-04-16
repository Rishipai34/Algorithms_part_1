import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class FastCollinearPoints {
    private  LineSegment[] lines;
    private int linesize = 0;
    private Point[] sorted;

    public FastCollinearPoints(Point[] points) {
        lines = new LineSegment[points.length];
        for (Point a: points) {
            Comparator<Point> comp = a.slopeOrder();
            Arrays.sort(points, comp);
            sorted = points;
            for (int j = 0; j < points.length; j++) {
                int i =j;
                int mark =0;
                j = check(j);
                if (j - i >= 2) {
                    LineSegment num = new LineSegment(points[j-1], points[i]);
                    for (LineSegment temp1 : lines) {
                        if (temp1 == num) {
                            mark = 1;
                            break;
                        }
                    }
                    if (mark == 0) {
                        lines[linesize] = new LineSegment(points[i], points[j-1]);
                        if (linesize <= points.length-2) linesize++;
                        else break;
                    }
                }
            }
        }
    }

    // Checks the collinear points
    private int check(int j) {
        int bar = j;
        if ((bar <= sorted.length-2) && (sorted[0].slopeTo(sorted[bar]) ==  sorted[0].slopeTo(sorted[bar+1]))) {
            bar++;
            bar = check(bar);
        }
        return bar;
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
