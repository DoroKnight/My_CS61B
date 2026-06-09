import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // TODO: Add any necessary instance variables.
    private int scale;              // The scale of images
    private boolean[][] grids;      // The images
    private int virtualTop;         // To judge whether full
    private int virtualBottom;      // To judge whether percolated
    private int numOfOpen;          // To write down the numOfOpenSites.
    private WeightedQuickUnionUF percolated;
    private WeightedQuickUnionUF full;

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        if (N <= 0) throw new java.lang.IllegalArgumentException();
        scale = N;
        virtualTop = N * N;
        virtualBottom = N * N + 1;
        numOfOpen = 0;
        grids = new boolean[N][N];
        percolated = new WeightedQuickUnionUF(scale * scale + 2);   // Add the virtualTop and virtualBottom
        full = new WeightedQuickUnionUF(scale * scale + 1);         // Only has the virtualTop
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        if (isIllegal(row, col)) throw new java.lang.IndexOutOfBoundsException();

        if (isOpen(row, col)) return;
        grids[row][col] = true;
        connectAround(row, col);
        numOfOpen += 1;
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        if (isIllegal(row, col)) throw new java.lang.IndexOutOfBoundsException();

        return grids[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        if (isIllegal(row, col)) throw new java.lang.IndexOutOfBoundsException();

        return isOpen(row, col) && full.connected(index(row, col), virtualTop);
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return numOfOpen;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return percolated.connected(virtualTop, virtualBottom);
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.
    private int index(int row, int col) {
        return scale * row + col;
    }

    private void connectAround(int row, int col) {
        // Check the top
        if (row > 0 && isOpen(row - 1, col)) {
            full.union(index(row - 1, col), index(row, col));
            percolated.union(index(row - 1, col), index(row, col));
        }
        // Check the bottom
        if (row < scale - 1 && isOpen(row + 1, col)) {
            full.union(index(row + 1, col), index(row, col));
            percolated.union(index(row + 1, col), index(row, col));
        }
        // Check the left
        if (col > 0 && isOpen(row, col - 1)) {
            full.union(index(row, col - 1), index(row, col));
            percolated.union(index(row, col - 1), index(row, col));
        }
        // Check the right
        if (col < scale - 1 && isOpen(row, col + 1)) {
            full.union(index(row, col + 1), index(row, col));
            percolated.union(index(row, col + 1), index(row, col));
        }

        // Check the edge
        if (row == 0) {
            full.union(index(row, col), virtualTop);
            percolated.union(index(row, col), virtualTop);
        }
        if (row == scale - 1) {
            percolated.union(index(row, col), virtualBottom);
        }
    }

    private boolean isIllegal(int row, int col) {
        return (row < 0 || row >= scale || col < 0 || col >= scale);
    }
}
