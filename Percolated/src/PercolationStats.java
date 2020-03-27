import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials2;
    final private double x[];

    // Performs independent trials on an n by n grid
    public PercolationStats(int n, int trials) {
        final Percolation[] ob2 = new Percolation[trials];

        int k, h;
        trials2 = trials;
        x = new double[trials];
        if ((n <= 0) || (trials <= 0)) {
            throw new IllegalArgumentException("n and Trials should not be less tha 0");
        }
        for (int i = 0; i < trials; i++) {
            ob2[i] = new Percolation(n);
            do {
                k = StdRandom.uniform(1, n + 1);
                h = StdRandom.uniform(1, n + 1);
                if (!ob2[i].isOpen(k, h)) {
                    ob2[i].open(k, h);
                }
            } while (!ob2[i].percolates());
            x[i] = ((double) (ob2[i].numberOfOpenSites()) / (double) (n * n));
        }
    }

    public double mean() {
        return StdStats.mean(x);
    }

    //Finds the standard deviation
    public double stddev() {
        return StdStats.stddev(x);
    }

    //Lower limit of confidence interval
    public double confidenceLo() {
        double lo;
        double temp;

        double sr = (double) trials2 / 2;

        do {
            temp = sr;
            sr = (temp + (trials2 / temp)) / 2;
        } while ((temp - sr) != 0);

        lo = mean() - ((1.96 * stddev()) / sr);
        return lo;
    }

    //Upper limit of confidence interval
    public double confidenceHi() {
        double lo;
        double temp;

        double sr = (double) trials2 / 2;

        do {
            temp = sr;
            sr = (temp + (trials2 / temp)) / 2;
        } while ((temp - sr) != 0);

        lo = mean() + ((1.96 * stddev()) / sr);
        return lo;
    }

    //Test client
    public static void main(String[] args) {
        PercolationStats last = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println(" mean                       =  " + last.mean());
        StdOut.println(" standard deviation         =  " + last.stddev());
        StdOut.println(" confidence interval        =  " + last.confidenceLo() + " , " + last.confidenceHi());
    }
}

