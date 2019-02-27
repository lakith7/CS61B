package es.datastructur.synthesizer;
import java.util.Iterator;


public class ArrayRingBuffer<T> implements BoundedQueue <T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    private int length;

    public class ArrayRingIterator implements Iterator<T> {
        public int pos;
        public ArrayRingIterator() {
            pos = 0;
        }
        public boolean hasNext() {
            return pos < fillCount;
        }

        public T next() {
            T returnItem = rb[pos];
            pos += 1;
            return returnItem;
        }
    }

    public Iterator<T> iterator() {
        return new ArrayRingIterator();
    }

    @Override
    public int capacity() {
        return length;
    }


    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */

    public ArrayRingBuffer(int capacity) {
        length = capacity;
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */

    @Override
    public void enqueue(T x) {
        if (fillCount == length) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last] = x;
        fillCount += 1;
        if (last + 1 == length) {
            last = 0;
        } else {
            last += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */

    @Override
    public T dequeue() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T holder = rb[first];
        fillCount -= 1;
        if (first + 1 == length) {
            first = 0;
        } else {
            first += 1;
        }
        return holder;
    }

    public boolean contains(T item) {
        for (T element: this) {
            if (element.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */

    @Override
    public T peek() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    @Override
    public boolean equals(Object o) {
        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (capacity() != other.capacity()) {
            return false;
        }
        for (T item: this) {
            if (!other.contains(item)) {
                return false;
            }
        }
        return true;
    }
}

