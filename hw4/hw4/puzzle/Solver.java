package hw4.puzzle;


import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;
import java.util.List;


public class Solver {
    MinPQ<Node> pq;
    int moves;
    List<WorldState> sequence;
    public Solver(WorldState initial) {
        pq = new MinPQ<>();
        Node root = new Node(initial, null, 0);
        pq.insert(root);
        Node target;
        while (true) {
            Node node = pq.delMin();
            WorldState currentState = node.curr;
            if (currentState.isGoal()) {
                target = node;
                break;
            }
            for (WorldState neighbourState: currentState.neighbors()) {
                if (node.prev == null || !neighbourState.equals(node.prev.curr)) {
                    Node newNode = new Node(neighbourState, node, node.numOfMoves + 1);
                    pq.insert(newNode);
                }
            }
        }
        moves = 0;
        sequence = new LinkedList<>();
        while (target != null) {
            sequence.add(0, target.curr);
            moves++;
            target = target.prev;
        }
        moves--;
    }
    public int moves() {
        return moves;
    }
    public Iterable<WorldState> solution() {
        return sequence;
    }
}
