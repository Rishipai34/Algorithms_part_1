import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int open[];
    final private WeightedQuickUnionUF ob1;
    private int open_num;
    final private int n2;

    /* Creates a grid of n by n staring from 0 to n^2 +1
    where 0 and n^2 +1 are reserved for uppermost and lowermost element
     */
    public Percolation(int n) {
        n2 = n;
        open_num = 0;
        if (n <= 0) {
            throw new IllegalArgumentException("N has to be greater than 0");
        }
        ob1 = new WeightedQuickUnionUF(n * n + 2);
        open = new int[n * n + 2];

    }

    //Opens a given cell
    public void open(int row, int col) {
        int k;
        k = (row - 1) * n2 + col;
        if (row < 1 || (col < 1) || (row > n2) || (col > n2)) {
            throw new IllegalArgumentException("Argument out of range ");
        }
        open[k] = 1;
        open_num += 1;
        if (row != n2) {
            if (isOpen(row + 1, col)) ob1.union(k + n2, k);
        }
        if (col != 1) {
            if (isOpen(row, col - 1)) ob1.union(k, k - 1);
        }
        if (row != 1) {
            if (isOpen(row - 1, col)) ob1.union(k, k - n2);
        }
        if (col != n2) {
            if (isOpen(row, col + 1)) ob1.union(k, k + 1);
        }
        if (row == 1) {
            ob1.union(k, 0);
        }
        if (row == n2) {
            ob1.union(k, (n2 * n2 + 1));
        }

    }

    //Checks if the cell is open
    public boolean isOpen(int row, int col) {
        int k;
        k = (row - 1) * n2 + col;
        if (row < 1 || (col < 1) || (row > n2) || (col > n2)) {
            throw new IllegalArgumentException("Argument out of range k = " + k);
        }
        return (open[k] == 1);
    }

    //Checks if the cell is connected to root
    public boolean isFull(int row, int col) {
        int k;
        k = (row - 1) * n2 + col;
        if (row < 1 || (col < 1) || (row > n2) || (col > n2)) {
            throw new IllegalArgumentException("Argument out of range ");
        }
        return ob1.connected(k, 0);

    }

    // Used to find the number of open sites
    public int numberOfOpenSites() {
        return open_num;
    }

    //Ches if system percolates
    public boolean percolates() {
        return ob1.connected((n2 * n2 + 1), 0);
    }
}
