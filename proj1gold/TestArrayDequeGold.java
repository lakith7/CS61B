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
        int[] tracking = new int[10];
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
            tracking[tracker] = m;
            tracker += 1;
        }

        while (tracker > 0) {
            Integer actual = actuality.removeLast();
            Integer test = tester.removeLast();
            String message = stringer(actual, tracking);
            assertEquals(message, actual, test);
            tracker -= 1;
        }


    }

    public String stringer(int a, int[] b) {
        int index = 0;
        String messaging = "";
        while (b[index] != a) {
            messaging += "\naddLast(" + b[index] + ")";
            index += 1;
        }
        messaging += "\nremoveLast()";
        return messaging;

    }

}
