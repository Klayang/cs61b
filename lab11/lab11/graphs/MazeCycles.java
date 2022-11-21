package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean targetFound = false;
    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        if (targetFound) announce();
        else findCycle(0, 0);
    }

    // Helper methods go here
    private int findCycle(int v, int parent) {
        marked[v] = true;
        for (int n: maze.adj(v)) {
            if (!marked[n]) {
                announce();
                int res = findCycle(n, v);
                if (res < 0) continue;
                else if (res == 0) return 0;
                else if (res == v) {
                    edgeTo[n] = v;
                    announce();
                    return 0;
                }
                else {
                    edgeTo[n] = v;
                    announce();
                    return res;
                }
            }
            else if (n != parent) {
                edgeTo[n] = v;
                targetFound = true;
                announce();
                return n;
            }
        }
        return -1;
    }
}

