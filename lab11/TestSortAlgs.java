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
        Queue<String> tester1 = MergeSort.mergeSort(tester);
        assertEquals(tester1.dequeue(), "Itai");
        assertEquals(tester1.dequeue(), "Joe");
        assertEquals(tester1.dequeue(), "Omar");
        tester.dequeue();
        tester.dequeue();
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
