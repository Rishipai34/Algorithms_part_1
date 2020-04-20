import edu.princeton.cs.algs4.StdOut;

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
            sorted = points;
            Arrays.sort(sorted, a.slopeOrder());
            /*for (Point temp1 : sorted) {
                StdOut.println(temp1.toString());
            }*/
            for (int j = 1; j < points.length; j++) {
                int i = j;
                j = check(j);
                if (j - i >= 2) {
                    LineSegment num = new LineSegment(points[j], points[i]);
                    if (!Arrays.asList(lines).contains(num)) {
                        lines[linesize] = new LineSegment(points[i], points[j]);
                        if (linesize <= points.length-2) linesize++;
                    }
                }
            }
        }
    }

    // Checks the collinear points
    private int check(int k) {
        int bar = k;
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