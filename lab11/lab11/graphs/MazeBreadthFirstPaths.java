package lab11.graphs;

import edu.princeton.cs.algs4.In;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    int s;
    int t;
    boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        if (targetFound) { announce();return;}
        marked[s] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (int n: maze.adj(vertex))
                if (!marked[n]) {
                    marked[n] = true;
                    edgeTo[n] = vertex;
                    distTo[n] = distTo[vertex] + 1;
                    queue.add(n);
                    announce();
                    if (n == t) {
                        targetFound = true;
                        return;
                    }
                }
        }
    }


    @Override
    public void solve() {
         bfs();
    }
}

