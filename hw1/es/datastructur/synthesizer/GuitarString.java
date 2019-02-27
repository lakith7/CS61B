package es.datastructur.synthesizer;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR/frequency);
        buffer = new ArrayRingBuffer<>(capacity);
        int h = 0;
        while (h < capacity) {
            buffer.enqueue((double) 0);
            h += 1;
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        if (!buffer.isEmpty()) {
            int n = 0;
            int length = buffer.fillCount();
            while (n < length) {
                buffer.dequeue();
                n += 1;
            }
        }

        int index = 0;
        while (index < buffer.capacity()) {
            buffer.enqueue(Math.random() - 0.5);
            index += 1;
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double holder = buffer.dequeue();
        double next = buffer.peek();
        buffer.enqueue(((holder + next) * 0.5) * DECAY);

    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
