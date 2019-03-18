package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;

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
        for (int i = 0; i < 10000; i++) {
            temp.add(i, rand.nextInt(100) + 1);
        }
        for (int y = 0; y < 10000; y++) {
            temp.changePriority(y, rand.nextInt(100) + 1);
        }
        assertEquals(10000, temp.size());
    }

    @Test
    public void changePrioritySanityTest() {
        ArrayHeapMinPQ<Integer> temp = new ArrayHeapMinPQ<>();
        Random rand = new Random();
        for (int i = 0; i < 200000; i++) {
            temp.add(i, rand.nextInt(100) + 1);
        }
        for (int y = 0; y < 1000; y++) {
            temp.changePriority(rand.nextInt(200000) + 1, rand.nextInt(100) + 1);
        }
        assertEquals(200000, temp.size());
    }

    /*
    @Test
    public void addLogTest() {
         Creates heaps of larger and larger size and then does add a constant number of times. This second set
        of add is what is timed.
        Constant = 10,000


        System.out.println("Log Test for add:");
        ArrayHeapMinPQ<Integer> temp = new ArrayHeapMinPQ<>();
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            temp.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer = new Stopwatch();
        for (int i = 10000; i < 20000; i++) {
            temp.add(i, rand.nextInt(100) + 1);
        }
        System.out.println("Total time elapsed (Ten Thousand): " + timer.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp1 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100000; i++) {
            temp1.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer1 = new Stopwatch();
        for (int i = 100000; i < 110000; i++) {
            temp1.add(i, rand.nextInt(100) + 1);
        }
        System.out.println("Total time elapsed (Hundred Thousand): " + timer1.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp2 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000000; i++) {
            temp2.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer2 = new Stopwatch();
        for (int i = 1000000; i < 1010000; i++) {
            temp2.add(i, rand.nextInt(100) + 1);
        }
        System.out.println("Total time elapsed (One Million): " + timer2.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp3 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10000000; i++) {
            temp3.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer3 = new Stopwatch();
        for (int i = 10000000; i < 10010000; i++) {
            temp3.add(i, rand.nextInt(100) + 1);
        }
        System.out.println("Total time elapsed (Ten Million): " + timer3.elapsedTime() +  " seconds.");

    }

    @Test
    public void changePriorityLogTest() {
         Creates heaps of larger and larger size and then does add a constant number of times. This second set
        of add is what is timed.
        Constant = 10,000

        System.out.println("Log Test for changePriority:");
        ArrayHeapMinPQ<Integer> temp = new ArrayHeapMinPQ<>();
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            temp.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            temp.changePriority(i, rand.nextInt(100) + 1);
        }
        System.out.println("Total time elapsed (Ten Thousand): " + timer.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp1 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100000; i++) {
            temp1.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer1 = new Stopwatch();
        for (int i = 90000; i < 100000; i++) {
            temp1.changePriority(i, rand.nextInt(100) + 1);
        }
        System.out.println("Total time elapsed (Hundred Thousand): " + timer1.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp2 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000000; i++) {
            temp2.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer2 = new Stopwatch();
        for (int i = 990000; i < 1000000; i++) {
            temp2.changePriority(i, rand.nextInt(100) + 1);
        }
        System.out.println("Total time elapsed (One Million): " + timer2.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp3 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10000000; i++) {
            temp3.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer3 = new Stopwatch();
        for (int i = 9990000; i < 10000000; i++) {
            temp3.changePriority(i, rand.nextInt(100) + 1);
        }
        System.out.println("Total time elapsed (Ten Million): " + timer3.elapsedTime() +  " seconds.");

    }

    @Test
    public void removeSmallestLogTest() {
         Creates heaps of larger and larger size and then does add a constant number of times. This second set
        of add is what is timed.
        Constant = 10,000

        System.out.println("Log Test for removeSmallest:");
        ArrayHeapMinPQ<Integer> temp = new ArrayHeapMinPQ<>();
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            temp.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            temp.removeSmallest();
        }
        System.out.println("Total time elapsed (Ten Thousand): " + timer.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp1 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100000; i++) {
            temp1.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer1 = new Stopwatch();
        for (int i = 90000; i < 100000; i++) {
            temp1.removeSmallest();
        }
        System.out.println("Total time elapsed (Hundred Thousand): " + timer1.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp2 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000000; i++) {
            temp2.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer2 = new Stopwatch();
        for (int i = 990000; i < 1000000; i++) {
            temp2.removeSmallest();
        }
        System.out.println("Total time elapsed (One Million): " + timer2.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp3 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10000000; i++) {
            temp3.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer3 = new Stopwatch();
        for (int i = 9990000; i < 10000000; i++) {
            temp3.removeSmallest();
        }
        System.out.println("Total time elapsed (Ten Million): " + timer3.elapsedTime() +  " seconds.");

    }

    @Test
    public void getSmallestLogTest() {
         Creates heaps of larger and larger size and then does add a constant number of times. This second set
        of add is what is timed.
        Constant = 10,000

        System.out.println("Log Test for getSmallest:");
        ArrayHeapMinPQ<Integer> temp = new ArrayHeapMinPQ<>();
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            temp.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            temp.getSmallest();
        }
        System.out.println("Total time elapsed (Ten Thousand): " + timer.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp1 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100000; i++) {
            temp1.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer1 = new Stopwatch();
        for (int i = 90000; i < 100000; i++) {
            temp1.getSmallest();
        }
        System.out.println("Total time elapsed (Hundred Thousand): " + timer1.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp2 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000000; i++) {
            temp2.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer2 = new Stopwatch();
        for (int i = 990000; i < 1000000; i++) {
            temp2.getSmallest();
        }
        System.out.println("Total time elapsed (One Million): " + timer2.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp3 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10000000; i++) {
            temp3.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer3 = new Stopwatch();
        for (int i = 9990000; i < 10000000; i++) {
            temp3.getSmallest();
        }
        System.out.println("Total time elapsed (Ten Million): " + timer3.elapsedTime() +  " seconds.");

    }

    @Test
    public void containsLogTest() {
        Creates heaps of larger and larger size and then does add a constant number of times. This second set
        of add is what is timed.
        Constant = 10,000

        System.out.println("Log Test for contains:");
        ArrayHeapMinPQ<Integer> temp = new ArrayHeapMinPQ<>();
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            temp.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            temp.contains(i);
        }
        System.out.println("Total time elapsed (Ten Thousand): " + timer.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp1 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100000; i++) {
            temp1.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer1 = new Stopwatch();
        for (int i = 90000; i < 100000; i++) {
            temp1.contains(i);
        }
        System.out.println("Total time elapsed (Hundred Thousand): " + timer1.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp2 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000000; i++) {
            temp2.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer2 = new Stopwatch();
        for (int i = 990000; i < 1000000; i++) {
            temp2.contains(i);
        }
        System.out.println("Total time elapsed (One Million): " + timer2.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp3 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10000000; i++) {
            temp3.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer3 = new Stopwatch();
        for (int i = 9990000; i < 10000000; i++) {
            temp3.contains(i);
        }
        System.out.println("Total time elapsed (Ten Million): " + timer3.elapsedTime() +  " seconds.");

    }

    @Test
    public void sizeLogTest() {
        Creates heaps of larger and larger size and then does add a constant number of times. This second set
        of add is what is timed.
        Constant = 10,000

        System.out.println("Log Test for size:");
        ArrayHeapMinPQ<Integer> temp = new ArrayHeapMinPQ<>();
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            temp.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            temp.size();
        }
        System.out.println("Total time elapsed (Ten Thousand): " + timer.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp1 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100000; i++) {
            temp1.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer1 = new Stopwatch();
        for (int i = 90000; i < 100000; i++) {
            temp1.size();
        }
        System.out.println("Total time elapsed (Hundred Thousand): " + timer1.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp2 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000000; i++) {
            temp2.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer2 = new Stopwatch();
        for (int i = 990000; i < 1000000; i++) {
            temp2.size();
        }
        System.out.println("Total time elapsed (One Million): " + timer2.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> temp3 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10000000; i++) {
            temp3.add(i, rand.nextInt(100) + 1);
        }
        Stopwatch timer3 = new Stopwatch();
        for (int i = 9990000; i < 10000000; i++) {
            temp3.size();
        }
        System.out.println("Total time elapsed (Ten Million): " + timer3.elapsedTime() +  " seconds.");

    }

    */

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
