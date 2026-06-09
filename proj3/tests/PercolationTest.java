import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class PercolationTest {

    /**
     * Enum to represent the state of a cell in the grid. Use this enum to help you write tests.
     * <p>
     * (0) CLOSED: isOpen() returns true, isFull() return false
     * <p>
     * (1) OPEN: isOpen() returns true, isFull() returns false
     * <p>
     * (2) INVALID: isOpen() returns false, isFull() returns true
     *              (This should not happen! Only open cells should be full.)
     * <p>
     * (3) FULL: isOpen() returns true, isFull() returns true
     * <p>
     */
    private enum Cell {
        CLOSED, OPEN, INVALID, FULL
    }

    /**
     * Creates a Cell[][] based off of what Percolation p returns.
     * Use this method in your tests to see if isOpen and isFull are returning the
     * correct things.
     */
    private static Cell[][] getState(int N, Percolation p) {
        Cell[][] state = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = Cell.values()[open + full];
            }
        }
        return state;
    }

    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        // open sites at (r, c) = (0, 1), (2, 0), (3, 1), etc. (0, 0) is top-left
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void oneByOneTest() {
        int N = 1;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        Cell[][] expectedState = {
                {Cell.FULL}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    // TODO: Using the given tests above as a template,
    //       write some more tests and delete the fail() line
    @Test
    public void yourFirstTestHere() {
        return;
    }

    @Test
    public void testConstructorIllegalArgumentExceptions() {
        int[] invalidSizes = {0, -1, -100};
        for (int n : invalidSizes) {
            try {
                new Percolation(n);
                fail("Expected IllegalArgumentException for N = " + n);
            } catch (IllegalArgumentException e) {
                // Expected behavior (符合预期的异常，不做任何处理)
            }
        }
    }

    @Test
    public void testIndexOutOfBoundsExceptionsOnUpperBounds() {
        int n = 5;
        Percolation p = new Percolation(n);

        // Test open()
        try { p.open(n, 0); fail("Expected IndexOutOfBoundsException"); } catch (IndexOutOfBoundsException e) {}
        try { p.open(0, n); fail("Expected IndexOutOfBoundsException"); } catch (IndexOutOfBoundsException e) {}
        try { p.open(n, n); fail("Expected IndexOutOfBoundsException"); } catch (IndexOutOfBoundsException e) {}

        // Test isOpen()
        try { p.isOpen(n, 0); fail("Expected IndexOutOfBoundsException"); } catch (IndexOutOfBoundsException e) {}

        // Test isFull()
        try { p.isFull(0, n); fail("Expected IndexOutOfBoundsException"); } catch (IndexOutOfBoundsException e) {}
    }

    @Test
    public void testIndexOutOfBoundsExceptionsOnIntegerExtremes() {
        Percolation p = new Percolation(10);

        try {
            p.open(Integer.MIN_VALUE, 0);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {}

        try {
            p.open(0, Integer.MAX_VALUE);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {}

        try {
            p.isOpen(Integer.MIN_VALUE, Integer.MAX_VALUE);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {}
    }
    
    @Test
    public void testMinimumValidGridOneByOne() {
        Percolation p = new Percolation(1);

        assertThat(p.numberOfOpenSites()).isEqualTo(0);
        assertThat(p.isOpen(0, 0)).isFalse();
        assertThat(p.isFull(0, 0)).isFalse();
        assertThat(p.percolates()).isFalse();

        p.open(0, 0);

        assertThat(p.numberOfOpenSites()).isEqualTo(1);
        assertThat(p.isOpen(0, 0)).isTrue();
        assertThat(p.isFull(0, 0)).isTrue();
        assertThat(p.percolates()).isTrue();
    }

    @Test
    public void testInitialStateOfGrid() {
        int n = 4;
        Percolation p = new Percolation(n);

        assertThat(p.numberOfOpenSites()).isEqualTo(0);
        assertThat(p.percolates()).isFalse();

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                assertThat(p.isOpen(r, c)).isFalse();
                assertThat(p.isFull(r, c)).isFalse();
            }
        }
    }

    @Test
    public void testIdempotenceOfOpenCall() {
        Percolation p = new Percolation(3);

        p.open(1, 1);
        assertThat(p.numberOfOpenSites()).isEqualTo(1);

        p.open(1, 1);
        p.open(1, 1);
        assertThat(p.numberOfOpenSites()).isEqualTo(1);
        assertThat(p.isOpen(1, 1)).isTrue();
    }

    @Test
    public void testDiagonalNonConnectivity() {
        Percolation p = new Percolation(3);

        p.open(0, 0);
        p.open(1, 1);
        p.open(2, 2);

        assertThat(p.isFull(0, 0)).isTrue();
        assertThat(p.isFull(1, 1)).isFalse();
        assertThat(p.isFull(2, 2)).isFalse();
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void testUShapedFlowConnectivity() {
        Percolation p = new Percolation(4);

        p.open(0, 1);
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(2, 3);
        p.open(1, 3);

        assertThat(p.isFull(1, 3)).isTrue();
        assertThat(p.percolates()).isFalse();

        p.open(0, 3);
        assertThat(p.isFull(0, 3)).isTrue();
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void testStrictBackwashPrevention() {
        int n = 5;
        Percolation p = new Percolation(n);

        for (int r = 0; r < n; r++) {
            p.open(r, 0);
        }
        assertThat(p.percolates()).isTrue();

        p.open(n - 1, n - 1);
        p.open(n - 2, n - 1);

        assertThat(p.isOpen(n - 1, n - 1)).isTrue();
        assertThat(p.isOpen(n - 2, n - 1)).isTrue();

        assertThat(p.isFull(n - 1, n - 1)).isFalse();
        assertThat(p.isFull(n - 2, n - 1)).isFalse();
    }

    @Test
    public void testFullGridPercolation() {
        int n = 3;
        Percolation p = new Percolation(n);

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                p.open(r, c);
            }
        }

        assertThat(p.numberOfOpenSites()).isEqualTo(n * n);
        assertThat(p.percolates()).isTrue();

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                assertThat(p.isFull(r, c)).isTrue();
            }
        }
    }

}
