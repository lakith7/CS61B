import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testingArray() {
        StudentArrayDeque<Integer> tester = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> actuality = new ArrayDequeSolution<>();
        int tracker = 0;
        int m = 0;
        int n = 0;
        while (tracker < 10) {
            m = StdRandom.uniform(100);
            tester.addFirst(m);
            actuality.addFirst(m);
            tracker += 1;
        }

        while (tracker > 0) {
            Integer actual = actuality.removeFirst();
            Integer test = tester.removeFirst();
            assertEquals("\naddFirst(" + actual + ")\nremoveFirst()", actual, test);
            tracker -= 1;
        }

        while (tracker < 10) {
            m = StdRandom.uniform(100);
            tester.addLast(m);
            actuality.addLast(m);
            tracker += 1;
        }

        while (tracker > 0) {
            Integer actual = actuality.removeLast();
            Integer test = tester.removeLast();
            assertEquals("\naddLast(" + actual + ")\nremoveLast()", actual, test);
            tracker -= 1;
        }


    }

}
