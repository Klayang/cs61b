package lab11.graphs;

import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    class Vertex implements Comparable {
        public int n;
        public int dist;
        public Vertex(int n, int dist) {
            this.n = n;
            this.dist = dist;
        }

        @Override
        public int compareTo(Object o) {
            Vertex vertex = (Vertex) o;
            return this.dist - vertex.dist;
        }
    }

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int vX = maze.toX(v), vY = maze.toY(v);
        int targetX = maze.toX(t), targetY = maze.toY(t);
        return Math.abs(vX - targetX) + Math.abs(vY - targetY);
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        marked[s] = true;
        pq.add(new Vertex(s, distTo[s] + h(s)));
        while (!pq.isEmpty()) {
            Vertex vertex = pq.poll();
            int v = vertex.n;
            for (int n: maze.adj(v)) {
                if (!marked[n]) {
                    marked[n] = true;
                    edgeTo[n] = v;
                    distTo[n] = distTo[v] + 1;
                    announce();
                    if (n == t) return;
                    Vertex curV = new Vertex(n, distTo[n] + h(n));
                    pq.add(curV);
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

