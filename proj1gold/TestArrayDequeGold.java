import static org.junit.Assert.*;

import org.junit.Test;

public class TestArrayDequeGold {
/*
    public static void main(String[] args) {
        StudentArrayDeque<Integer> test = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> actual = new ArrayDequeSolution<>();
    } */

    @Test
    public void testUpdateArray() {
        StudentArrayDeque<Integer> test = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> actual = new ArrayDequeSolution<>();

        int repeater = 0;
        int checker = 0;
        int input = 0;
        String message = "\n";

        while (repeater < 1000) {
            checker = StdRandom.uniform(1, 5);
            input = StdRandom.uniform(1000);

            if (checker == 1) {
                test.addLast(input);
                actual.addLast(input);
                message += "addLast(" + input + ")\n";
            }

            if (checker == 2) {
                test.addFirst(input);
                actual.addFirst(input);
                message += "addFirst(" + input + ")\n";
            }

            if (checker == 3) {
                if (test.isEmpty()) {
                    continue;
                }
                if (actual.isEmpty()) {
                    continue;
                }
                message += ("removeLast()\n");
                assertEquals(message, actual.removeLast(), test.removeLast());
            }

            if (checker == 4) {
                if (test.isEmpty()) {
                    continue;
                }
                if (actual.isEmpty()) {
                    continue;
                }
                message += ("removeFirst()\n");
                assertEquals(message, actual.removeFirst(), test.removeFirst());
            }

            repeater += 1;
        }

    }

}

