import static org.junit.Assert.*;

import org.junit.Test;

public class TestArrayDequeGold {


    @Test
    public void testUpdateArray() {
        StudentArrayDeque<Integer> test = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> actual = new ArrayDequeSolution<>();
        int repeater = 0;
        int correct = 0;
        int wrong = 0;
        String message = "";
        while (repeater < 500) {
            int checker = StdRandom.uniform(1, 3);
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
            repeater += 1;
        }


        while (repeater > 50) {
            int checker = StdRandom.uniform(1, 3);
            if (checker == 1) {
                correct = actual.removeFirst();
                wrong = test.removeFirst();
                message = stringer(correct, actual, false);
                assertEquals(message, correct, wrong);
            }

            if (checker == 2) {
                correct = actual.removeLast();
                wrong = test.removeLast();
                message = stringer(correct, actual, true);
                assertEquals(message, correct, wrong);
            }
            repeater -= 1;
        }

    }


    public String stringer(int a, ArrayDequeSolution<Integer> b, boolean c) {
        String messaging = "";
        int index = 0;
        while (index < b.size()) {
            messaging += "\naddLast(" + b.removeFirst() + ")";
            index += 1;
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
