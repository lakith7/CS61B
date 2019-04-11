import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class TestSortAlgs {

    /* Enqueue is addLast and Dequeue is removeFirst */

    @Test
    public void testQuickSort() {

    }

    @Test
    public void testMergeSort() {
        Queue<String> tester = new Queue<>();
        tester.enqueue("Joe");
        tester.enqueue("Omar");
        tester.enqueue("Itai");
        tester = MergeSort.mergeSort(tester);
        assertEquals(tester.dequeue(), "Itai");
        assertEquals(tester.dequeue(), "Joe");
        assertEquals(tester.dequeue(), "Omar");
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
