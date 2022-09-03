// TODO: Make sure to make this class a part of the synthesizer package
 package synthesizer;
import org.junit.Test;

import java.util.AbstractList;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    private class ArrayRingBufferIterator implements Iterator{
        private int current;
        private boolean hasStarted;
        public ArrayRingBufferIterator() {
            current = first;
            hasStarted = false;
        }
        @Override
        public boolean hasNext() {
            if (current == last && hasStarted) return false;
            return true;
        }

        @Override
        public Object next() {
            if (!hasNext()) throw new RuntimeException("No next item");
            if (current == last) hasStarted = true;
            T res = rb[current];
            current = (current + 1) % capacity;
            return res;
        }
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
       this.capacity = capacity;
       rb = (T[]) new Object[capacity];
       first = 0; last = 0; fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Test
    public void enqueue(T x) {
        // handle exception
        if (isFull()) throw new RuntimeException("Ring buffer overflow");
        // enqueue operation
        rb[last] = x;
        // update count variables
        last = (last + 1) % capacity;
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // handle exception
        if (isEmpty()) throw new RuntimeException("Ring buffer underflow");
        // get the element that needs to be dequeued
        T res = rb[first];
        rb[first] = null;
        // update count variables
        first = (first + 1) % capacity;
        fillCount--;
        // return the dequeued item
        return res;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if (isEmpty()) throw new RuntimeException("No peek item");
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
}
