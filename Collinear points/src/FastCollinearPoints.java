import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class FastCollinearPoints {
    private  LineSegment[] lines;
    private int linesize = 0;
    private int mark ;
    public FastCollinearPoints(Point[] points){
        lines = new LineSegment[points.length];
        for(Point a: points){
            Comparator<Point> comp = Objects.requireNonNull(a).slopeOrder();
            Arrays.sort(points, comp);
            for(int i=0 ; i < points.length ; i++) {
                int j;
                mark = 0;
                for(j = i + 1; ;j++){
                    if((j <= points.length-2) && (points[i].slopeTo(points[j]) ==  points[i].slopeTo(points[j+1]))){
                        continue;
                    }
                    else break;
                }
                if(j - i >= 4){
                    LineSegment num = new LineSegment(points[j-1], points[i]);
                    for (LineSegment temp1 : lines) {
                        if (Objects.equals(temp1, num)) {
                            mark = 1;
                            break;
                        }
                    }
                    if (mark == 0) {
                        lines[linesize] = new LineSegment(points[i],points[j-1]);
                        if(linesize <= points.length-2) linesize++;
                        else break;
                    }
                    mark = 0;
                    i = j;
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
