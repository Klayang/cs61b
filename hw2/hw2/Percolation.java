package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] open;
    private int N;
    private WeightedQuickUnionUF uf;
    private int topSite;
    private int numOfOpenSites;
    private boolean hasPercolated;
    /**
     * create N-by-N grid, with all sites initially blocked
     * @param N width & height of the grids
     */
    public Percolation(int N) {
        // exception handle
        if (N <= 0) throw new IllegalArgumentException();
        // Initialize instances
        this.N = N;
        open = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 1);
        topSite = N * N;
        numOfOpenSites = 0;
        hasPercolated = false;
    }

    /**
     * open the site (row, col) if it is not open already
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        // exception handle
        if (row < 0 || row >= N || col < 0 || col >= N) throw new IndexOutOfBoundsException();
        // open the prescribed site
        open[row][col] = true;
        connect(row, col);
        // if this grid is at the bottom of the board, we check whether the grid connects to the top
        // if so, the board has percolated already
        if (row == N - 1 && isFull(row, col)) hasPercolated = true;
        // Increment num of open sites
        numOfOpenSites++;
    }

    /**
     * is the site (row, col) open?
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) {
        // exception handle
        if (row < 0 || row >= N || col < 0 || col >= N) throw new IndexOutOfBoundsException();
        // check whether the site is open
        return open[row][col];
    }

    /**
     * is the site (row, col) full?
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        // exception handle
        if (row < 0 || row >= N || col < 0 || col >= N) throw new IndexOutOfBoundsException();
        // check whether the site is full
        return uf.connected(index(row, col), topSite);
    }

    /**
     * number of open sites
     * @return
     */
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    /**
     * does the system percolate?
     * @return
     */
    public boolean percolates() {
        return hasPercolated;
    }

    /**
     * Transform index of a 2d array to index of its 1d version
     * @param i, row index
     * @param j, column index
     * @return index of 1d version
     */
    private int index(int i, int j) {
        return i * N + j;
    }

    /**
     * try connecting the current grip with grids beside it
     * @param i, row index of the grid
     * @param j, column index of the grid
     */
    private void connect(int i, int j) {
        if (j > 0 && open[i][j - 1]) uf.union(index(i, j), index(i, j - 1));
        if (j < N - 1 && open[i][j + 1]) uf.union(index(i, j), index(i, j + 1));
        if (i == 0) uf.union(index(i, j), topSite);
        if (i > 0 && open[i - 1][j]) uf.union(index(i, j), index(i - 1, j));
        if (i < N - 1 && open[i + 1][j]) uf.union(index(i, j), index(i + 1, j));
    }

    /**
     * Unit test
     * @param args
     */
    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        for (int i = 0; i < 4; ++i) {
            p.open(i, 2);
            System.out.println(p.percolates());
        }
        p.open(3, 0);
        System.out.println(p.isFull(3, 0));
        System.out.println(p.numOfOpenSites);
    }
}
