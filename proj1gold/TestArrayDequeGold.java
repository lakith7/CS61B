import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testingArray() {
        StudentArrayDeque<Integer> tester = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> actual = new ArrayDequeSolution<>();
        int tracker = 0;
        int m = 0;
        int n = 0;
        while (tracker < 5) {
            n = StdRandom.uniform(-9999999, 9999999);
            tester.addFirst(n);
            actual.addFirst(n);
            tester.addLast(n*2);
            actual.addLast(n*2);
            tracker += 1;
        }
        while (m < 2) {
            Integer holding = actual.removeFirst();
            Integer holdingEnd = actual.removeLast();
            assertEquals("\naddFirst(" + holding + ")\nremoveFirst()" , tester.removeFirst(), holding);
            assertEquals("\naddLast(" + holdingEnd + ")\nremoveLast()" , tester.removeLast(), holdingEnd);
            m += 1;
        }

    }

}
