import org.junit.Test;
import static org.junit.Assert.*;

public class UnionFindTest {
//Change size of root when you connect elements together//
    @Test
    public void testCreate() {
        UnionFind tester = new UnionFind(8);
        tester.union(1, 0);
        tester.union(2, 1);
        tester.union(7, 6);
        tester.union(5, 4);
        int size = tester.sizeOf(1);
        assertEquals(size, 3);
    }
}
