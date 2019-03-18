package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {

    @Test
    public void basicTest() {
        ArrayHeapMinPQ<Integer> temp = new ArrayHeapMinPQ<>();
        temp.add(10, 1);
        temp.add(20, 2);
        temp.add(15, 3);
        assertEquals((int) temp.removeSmallest(), 10);
        assertEquals((int) temp.getSmallest(), 20);
        temp.changePriority(15, 1);
        assertEquals((int) temp.getSmallest(), 15);
    }
}
