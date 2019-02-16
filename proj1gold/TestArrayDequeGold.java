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
            Integer h = 0;
            String messaging = "";
            while (tracking[index] != actual) {
                messaging += "\naddLast(" + tracking[index] + ")";
                h = tracking[index];
                index += 1;
            }
            messaging += "\naddLast(" + actual + ")";
            messaging += "\nremoveLast()";

            assertEquals(messaging, actual, test);
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
