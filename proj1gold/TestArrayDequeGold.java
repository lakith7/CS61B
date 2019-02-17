import static org.junit.Assert.*;

import org.junit.Test;

public class TestArrayDequeGold {


    @Test
    public void testUpdateArray() {
        StudentArrayDeque<Integer> test = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> actual = new ArrayDequeSolution<>();
        int holder = 0;
        int correct = 0;
        int wrong = 0;
        int index = 0;
        String message = "";
        while (index < 100) {
            holder = StdRandom.uniform(1000);
            test.addLast(holder);
            actual.addLast(holder);
            index += 1;
        }

        while (index > 0) {
            message = stringer(actual);
            correct = actual.removeLast();
            wrong = test.removeLast();
            assertEquals(message, correct, wrong);
            index -= 1;
        }

    }


    public String stringer(ArrayDequeSolution<Integer> b) {
        String messaging = "";
        int indexer = 0;
        int size = b.size();
        while (indexer < size) {
            messaging += "\naddLast(" + b.get(indexer) + ")";
            indexer += 1;
        }
        messaging += "\nremoveLast()";
        return messaging;
    }


    /*
    @Test
    public void testUpdateArray() {
        StudentArrayDeque<Integer> test = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> actual = new ArrayDequeSolution<>();
        int repeater = 0;
        int correct = 0;
        int wrong = 0;
        while (repeater < 10000) {
            int checker = StdRandom.uniform(1, 4);
            if (checker == 1) {
                int holder = StdRandom.uniform(1000);
                test.addFirst(holder);
                actual.addFirst(holder);
            }
            if (checker == 2) {
                int holder = StdRandom.uniform(1000);
                test.addLast(holder);
                actual.addLast(holder);
            }
            if (checker == 3) {
                correct = actual.removeFirst();
                wrong = test.removeFirst();
                assertEquals(stringer(correct, actual, true), correct, wrong);
            }

            if (checker == 4) {
                assertEquals(test.removeLast(), actual.removeLast());
            }

            repeater += 1;
        }

    }



    public String stringer(int a, ArrayDequeSolution<Integer> b, boolean c) {
        String messaging = "";
        while (b != null) {
            messaging += "\naddLast(" + b.removeFirst() + ")";
        }
        messaging += "\naddLast(" + a + ")";
        if (c) {
            messaging += "\nremoveLast()";
        }
        if (!c) {
            messaging += "\nremoveFirst()";
        }
        return messaging;
    }

    */


    /*
    @Test
    public void testingArray() {
        StudentArrayDeque<Integer> tester = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> actuality = new ArrayDequeSolution<>();
        int tracker = 0;
        int m = 0;
        int n = 0;
        int[] tracking = new int[10];

        while (tracker < 7) {
            m = StdRandom.uniform(1000);
            tester.addLast(m);
            actuality.addLast(m);
            tracking[tracker] = m;
            tracker += 1;
        }

        tracker = 7;

        while (tracker > 0) {
            Integer actual = actuality.removeLast();
            Integer test = tester.removeLast();
            int index = 0;
            String messaging = "";

            while (tracking[index] != actual) {
                messaging += "\naddLast(" + tracking[index] + ")";
                index += 1;
            }
            messaging += "\naddLast(" + actual + ")";
            messaging += "\nremoveLast()";
            assertEquals(messaging, actual, test);
            tracker -= 1;
        }


    }
    */


}
