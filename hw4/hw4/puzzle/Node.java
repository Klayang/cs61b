package hw4.puzzle;

public class Node implements Comparable<Node> {
    WorldState curr;
    Node prev;
    int numOfMoves;
    public Node(WorldState curr, Node prev, int numOfMoves) {
        this.curr = curr;
        this.prev = prev;
        this.numOfMoves = numOfMoves;
    }

    @Override
    public int compareTo(Node o) {
        return (numOfMoves + curr.estimatedDistanceToGoal()) - (o.numOfMoves + o.curr.estimatedDistanceToGoal());
    }
}
