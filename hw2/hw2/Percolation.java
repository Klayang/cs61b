package hw2;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation 
{
    private boolean [][] open;
    private final int n;
    private int NumberOfOpenSites = 0;
    private final WeightedQuickUnionUF wquf;
    private final WeightedQuickUnionUF WQUF;
    public Percolation(int n)
    {
        if(n <= 0) throw new IllegalArgumentException();
        wquf = new WeightedQuickUnionUF(n*n + 2);
        WQUF = new WeightedQuickUnionUF(n*n + 1);
        this.n = n;
        open = new boolean[n+2][n+2];
        for(int i = 0; i <= n + 1; i++)
            for(int j = 0; j <= n + 1; j++)
                open[i][j] = false;
    }
    public void open(int row, int col)
    {
        exception(row, col);
        if(isOpen(row, col)) return;
        open[row][col] = true;
        if(row == 1 && n == 1) 
        {
            wquf.union(1, 0);
            wquf.union(1, 2);
            WQUF.union(1, 0);
        }
        if(row == 1) 
        {
            wquf.union(col, 0);
            if(open[row + 1][col]) wquf.union(col, n + col);
            WQUF.union(col, 0);
            if(open[row + 1][col]) WQUF.union(col, n + col);
        }
        else if(row == n) 
        {
            wquf.union(n*row + col - n, n*n + 1);
            if(open[row - 1][col]) wquf.union(n*row + col - n, n*row + col - 2*n);
            if(open[row - 1][col]) WQUF.union(n*row + col - n, n*row + col - 2*n);
        }
        else
        {
            if(open[row - 1][col]) wquf.union(n*row + col - n, n*row + col - 2*n);
            if(open[row + 1][col]) wquf.union(n*row + col - n, n*row + col);
            if(open[row - 1][col]) WQUF.union(n*row + col - n, n*row + col - 2*n);
            if(open[row + 1][col]) WQUF.union(n*row + col - n, n*row + col);
        }
        if(open[row][col - 1]) wquf.union(n*row + col - n, n*row + col - n - 1);
        if(open[row][col + 1]) wquf.union(n*row + col - n, n*row + col - n + 1);
        if(open[row][col - 1]) WQUF.union(n*row + col - n, n*row + col - n - 1);
        if(open[row][col + 1]) WQUF.union(n*row + col - n, n*row + col - n + 1);
        NumberOfOpenSites++;
    }
    public boolean isOpen(int row, int col)
    {
        exception(row, col);
        return open[row][col] == true;
    }
    public int numberOfOpenSites()
    {
        return NumberOfOpenSites;
    }
    public boolean isFull(int row, int col)
    {
        exception(row, col);
        return WQUF.find(0) == WQUF.find(n*row + col - n);
    }
    public boolean percolates()
    {
        return wquf.find(0) == wquf.find(n*n + 1);
    }
    private void exception(int row, int col)
    {
        if(row <= 0 || row >= n + 1 || col <= 0 || col >= n + 1) throw new IllegalArgumentException();
    }
    public static void main(String[] argv)
    {
        Percolation per = new Percolation(3);
        per.open(1, 3);per.open(2, 3);per.open(3, 3);per.open(3, 1);
        StdOut.println(per.isFull(3, 1));
    }
}
