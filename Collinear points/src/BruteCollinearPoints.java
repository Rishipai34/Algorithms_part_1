import java.util.Objects;

public class BruteCollinearPoints {
    private  LineSegment[] lines;
    private int linesize = 0;
    private int mark = 0;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        lines = new LineSegment[points.length*points.length];
        for (Point element : points)
            for (Point item : points) {
                if (item.compareTo(element) != 0 ) {
                    for (Point value : points) {
                        if ((item.compareTo(value) != 0) && (element.compareTo(value) != 0)) {
                            for (Point point : points) {
                                double slope1 = element.slopeTo(item);
                                double slope3 = value.slopeTo(point);
                                double slope2 = item.slopeTo(value);
                                if ((point.compareTo(element) != 0) && (point.compareTo(value) != 0) && (point.compareTo(item) != 0)) {
                                    if((slope1 == slope2) && (slope2 == slope3)) {
                                        LineSegment num = new LineSegment(point, element);
                                        for (LineSegment temp1 : lines) {
                                            if (Objects.equals(temp1, num)) {
                                                mark = 1;
                                                break;
                                            }
                                        }
                                        if (mark == 0) lines[linesize++] = new LineSegment(element, point);
                                        mark = 0;
                                    }
                                }
                            }
                        }
                    }
                }
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
}
