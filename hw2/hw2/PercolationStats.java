package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] probList;
    private double mean;
    private double stdDev;
    private int experimentTimes;
    /**
     * perform T independent experiments on an N-by-N grid
     * @param N
     * @param T
     * @param pf
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        probList = new double[T];
        mean = -1; stdDev = -1;
        experimentTimes = T;

        for (int i = 0; i < T; ++i) {
            Percolation curSys = pf.make(N);
            while (!curSys.percolates()) {
                int x = StdRandom.uniform(N), y = StdRandom.uniform(N);
                while (curSys.isOpen(x, y)) {
                    x = StdRandom.uniform(N);
                    y = StdRandom.uniform(N);
                }
                curSys.open(x, y);
            }
            double curProb = (double) curSys.numberOfOpenSites() / (double) (N * N);
            probList[i] = curProb;
        }
    }

    /**
     * sample mean of percolation threshold
     * @return
     */
    public double mean() {
        if (mean == -1) mean = StdStats.mean(probList);
        return mean;
    }

    /**
     * sample standard deviation of percolation threshold
     * @return
     */
    public double stddev() {
        if (stdDev == -1) stdDev = StdStats.stddev(probList);
        return stdDev;
    }

    /**
     * low endpoint of 95% confidence interval
     * @return
     */
    public double confidenceLow() {
        double res = mean() - 1.96 * stddev() / Math.sqrt(experimentTimes);
        return res;
    }

    /**
     * high endpoint of 95% confidence interval
     * @return
     */
    public double confidenceHigh() {
        double res = mean() + 1.96 * stddev() / Math.sqrt(experimentTimes);
        return res;
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(500, 1000, pf);
        System.out.println(ps.mean());
    }
}
