package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] open;
    private int N;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufToAvoidBackwash;
    private int topSite;
    private int bottomSite;
    private int numOfOpenSites;
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
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufToAvoidBackwash = new WeightedQuickUnionUF(N * N + 1);
        topSite = N * N;
        bottomSite = topSite + 1;
        numOfOpenSites = 0;
    }

    /**
     * open the site (row, col) if it is not open already
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        // exception handle
        if (row < 0 || row >= N || col < 0 || col >= N) throw new IndexOutOfBoundsException();
        if (open[row][col]) return;
        // open the prescribed site
        open[row][col] = true;
        connect(row, col);
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
        return ufToAvoidBackwash.connected(index(row, col), topSite);
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
        return uf.connected(topSite, bottomSite);
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
        if (j > 0 && open[i][j - 1]) {
            uf.union(index(i, j), index(i, j - 1));
            ufToAvoidBackwash.union(index(i, j), index(i, j - 1));
        }
        if (j < N - 1 && open[i][j + 1]) {
            uf.union(index(i, j), index(i, j + 1));
            ufToAvoidBackwash.union(index(i, j), index(i, j + 1));
        }
        if (i > 0 && open[i - 1][j]) {
            uf.union(index(i, j), index(i - 1, j));
            ufToAvoidBackwash.union(index(i, j), index(i - 1, j));
        }
        if (i < N - 1 && open[i + 1][j]) {
            uf.union(index(i, j), index(i + 1, j));
            ufToAvoidBackwash.union(index(i, j), index(i + 1, j));
        }
        if (i == 0) {
            uf.union(index(i, j), topSite);
            ufToAvoidBackwash.union(index(i, j), topSite);
        }
        if (i == N - 1) uf.union(index(i, j), bottomSite);
    }

    /**
     * Unit test
     * @param args
     */
    public static void main(String[] args) {
        Percolation p = new Percolation(2);
        p.open(0, 0); p.open(1, 1); p.open(0, 1);
        System.out.println(p.percolates());
    }
}