import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] lines;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points)
    {
        List<LineSegment> linesTemp = new ArrayList<>();
        Arrays.sort(points);
        for (int i = 0; i < points.length-1; i++) {
            List<Double> firstSlopeArray = new ArrayList<>();
            List<Double> secondSlopeArray = new ArrayList<>();
            Point[] aux = points.clone();
            if (i != 0)
                Arrays.sort(aux, 0, i-1, aux[i].slopeOrder());
            Arrays.sort(aux, i+1, aux.length, aux[i].slopeOrder());
            for (int n = i+1; n < points.length; n++) {
                double slopes = aux[i].slopeTo(aux[n]);
                secondSlopeArray.add(slopes);
            }
            for (int n = 0; n < i; n++) {
                double slopes = aux[i].slopeTo(aux[n]);
                firstSlopeArray.add(slopes);
            }
            int head = 0, tail = 0;
            int k = 0;
            while (head < secondSlopeArray.size()-1) {
                while (k < secondSlopeArray.size()-1) {
                    double slope1 = secondSlopeArray.get(k);
                    double slope2 = secondSlopeArray.get(k+1);
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
                    // check for duplicates
                    double slope = aux[i].slopeTo(aux[i+tail+1]);
                    if (!firstSlopeArray.contains(slope))
                        linesTemp.add(new LineSegment(aux[i], aux[i+tail+1]));
                    // add it to segment
                }
                head = tail+1;
                tail = head;
            }
        }
        lines = linesTemp.toArray(new LineSegment[0]);
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
