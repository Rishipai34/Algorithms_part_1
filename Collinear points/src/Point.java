import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        if (this.y > that.y) return +1;
        if (this.y < that.y) return -1;
        return Integer.compare(this.x, that.x);
    }

    public double slopeTo(Point that) {
        if (that == null) {
            throw new IllegalArgumentException("Argument point cannot be null ");
        }
        double slope;
        if ((this.y != that.y) && (this.x == that.x)) {
            slope = Double.POSITIVE_INFINITY;
            return slope;
        }
        else if (this.x == that.x) {
            slope = Double.NEGATIVE_INFINITY;
            return slope;
        }
        else if (this.y == that.y) {
            return 0;
        }
        slope = (((double) (that.y - this.y))/((double) (that.x - this.x)));
        return slope;

    }

    public Comparator<Point> slopeOrder() {
        return Comparator.comparingDouble(this::slopeTo);
    }


}
