package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void testEnqueue() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        for (int i = 0; i < 10; ++i)
            arb.enqueue(i);
        arb.enqueue(11);
    }
    @Test
    public void testDequeue() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; ++i)
            arb.enqueue(i);
        int[] array = new int[10];
        for (int i = 0; i < 10; ++i)
            array[i] = arb.dequeue();
        int[] expected = {0 ,1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(array, expected);
//        arb.dequeue();
    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; ++i)
            arb.enqueue(i);
        int[] array = new int[10];
        int j = 0;
        for (int i: arb){
            array[j] = i;
            j++;
        }
        int[] expected = {0 ,1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertEquals(j, 10);
        assertArrayEquals(array, expected);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
