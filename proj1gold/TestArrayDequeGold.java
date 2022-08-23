import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

public class TestArrayDequeGold {
    @Test
    public void testArrayDeque() {
        Deque<Integer> solutionDeque = new LinkedList<>();
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        String info = "";
        for (int i = 0; i < 1000; ++i) {
            for (int j = 0; j < 5; ++j){
                Integer randomNum = StdRandom.uniform(10);
                solutionDeque.addFirst(randomNum); studentDeque.addFirst(randomNum);
                info = info + "addFirst" + "(" + randomNum + ")" + "\n";
            }

            for (int j = 0; j < 2; ++j){
                Integer randomNum = StdRandom.uniform(10);
                solutionDeque.addLast(randomNum); studentDeque.addLast(randomNum);
                info = info + "addLast" + "(" + randomNum + ")" + "\n";
            }

            info = info + "removeLast()" + "\n";
            assertEquals(info, solutionDeque.removeLast(), studentDeque.removeLast());
            info = info + "removeFirst()" + "\n";
            assertEquals(info, solutionDeque.removeFirst(), studentDeque.removeFirst());
        }
    }
}
