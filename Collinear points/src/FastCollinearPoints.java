import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private  LineSegment[] lines;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points)
    {
        List<LineSegment> linestemp = new ArrayList<>();
        Arrays.sort(points);
        for (int i = 0; i < points.length-1; i++) {
            List<Double> firstslopearray = new ArrayList<>();
            List<Double> secondslopearray = new ArrayList<>();
            Point[] aux = points.clone();
            if (i != 0)
                Arrays.sort(aux, 0, i-1, aux[i].slopeOrder());
            Arrays.sort(aux, i+1, aux.length, aux[i].slopeOrder());
            for (int n = i+1; n < points.length; n++) {
                double slopes = aux[i].slopeTo(aux[n]);
                secondslopearray.add(slopes);
            }
            for (int n = 0; n < i; n++) {
                double slopes = aux[i].slopeTo(aux[n]);
                firstslopearray.add(slopes);
            }
            int head = 0, tail = 0;
            int k = 0;
            while (head < secondslopearray.size()-1) {
                while (k < secondslopearray.size()-1) {
                    double slope1 = secondslopearray.get(k);
                    double slope2 = secondslopearray.get(k+1);
                    if (slope1 == slope2) {
                        tail++;
                    }
                    else {
                        k++;
                        break;
                    }
                    k++;
                }
                if (tail - head >= 2) {
                    // checkfor duplicates
                    double slope = aux[i].slopeTo(aux[i+tail+1]);
                    if (!firstslopearray.contains(slope))
                        linestemp.add(new LineSegment(aux[i], aux[i+tail+1]));
                    // add it to segment
                }
                head = tail+1;
                tail = head;
            }
        }
        lines = linestemp.toArray(new LineSegment[linestemp.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lines.length;
    }

    // Returns the line segments
    public LineSegment[] segments() {
        return lines;
    }
}
