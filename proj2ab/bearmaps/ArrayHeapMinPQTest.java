package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Random;

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

    @Test
    public void largeSizeTest() {
        ArrayHeapMinPQ<Integer> temp = new ArrayHeapMinPQ<>();
        Random rand = new Random();
        for (int i = 0; i < 1000000; i++) {
            temp.add(i, rand.nextInt(100) + 1);
        }
        assertEquals(1000000, temp.size());
    }

    @Test
    public void allSamePriority() {
        ArrayHeapMinPQ<Integer> temp = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10; i++) {
            temp.add(i, 2);
        }
        printFancyHeapDrawing(temp.returnMinHeap());
        assertEquals(temp.size(), 10);
        assertEquals((int) temp.removeSmallest(), 0);
    }

    @Test
    public void removeSmallestTest() {
        ArrayHeapMinPQ<Integer> temp = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000000; i++) {
            temp.add(i, i + 1);
        }
        for (int y = 0; y < 1000000; y++) {
            temp.removeSmallest();
        }
        assertEquals(temp.size(), 0);
    }

    @Test
    public void changePriorityLargeTest() {
        ArrayHeapMinPQ<Integer> temp = new ArrayHeapMinPQ<>();
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            temp.add(i, rand.nextInt(100) + 1);
        }
        for (int y = 0; y < 100; y++) {
            temp.changePriority(y,rand.nextInt(100) + 1);
        }
        assertEquals(100, temp.size());
    }

    public static void printFancyHeapDrawing(Object[] items) {
        String drawing = fancyHeapDrawingHelper(items, 1, "");
        System.out.println(drawing);
    }

    /* Recursive helper method for toString. */
    private static String fancyHeapDrawingHelper(Object[] items, int index, String soFar) {
        if (index >= items.length || items[index] == null) {
            return "";
        } else {
            String toReturn = "";
            int rightIndex = 2 * index + 1;
            toReturn += fancyHeapDrawingHelper(items, rightIndex, "        " + soFar);
            if (rightIndex < items.length && items[rightIndex] != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + items[index] + "\n";
            int leftIndex = 2 * index;
            if (leftIndex < items.length && items[leftIndex] != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += fancyHeapDrawingHelper(items, leftIndex, "        " + soFar);
            return toReturn;
        }
    }
}
