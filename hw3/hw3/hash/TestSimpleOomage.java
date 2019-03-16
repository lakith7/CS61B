package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;

<<<<<<< HEAD
=======
import java.util.Set;
>>>>>>> f6ac002c3d915b067db1761ce0317473193544ab
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
<<<<<<< HEAD
        SimpleOomage test1 = new SimpleOomage(0, 5, 10);
        SimpleOomage test2 = new SimpleOomage(0, 5, 10);
        HashSet<SimpleOomage> testing = new HashSet<SimpleOomage>();
        testing.add(test1);
        assertTrue(testing.contains(test2));

        ArrayList<SimpleOomage> test = new ArrayList<>();

        for (int r = 0; r < 256; r += 5) {
            for (int g = 0; g < 256; g += 5) {
                for (int b = 0; b < 256; b += 5) {
                    SimpleOomage tester = new SimpleOomage(r, g, b);
                    assertFalse(test.contains(tester));
                    test.add(tester);
                }
            }
        }
=======
        /* TODO: Write a test that ensures the hashCode is perfect,
          meaning no two SimpleOomages should EVER have the same
          hashCode UNLESS they have the same red, blue, and green values!
         */
>>>>>>> f6ac002c3d915b067db1761ce0317473193544ab
    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }

    /*
    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }*/

<<<<<<< HEAD
    @Test
=======
    /* TODO: Uncomment this test after you finish haveNiceHashCodeSpread in OomageTestUtility */
    /*@Test
>>>>>>> f6ac002c3d915b067db1761ce0317473193544ab
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
<<<<<<< HEAD
    }
=======
    }*/
>>>>>>> f6ac002c3d915b067db1761ce0317473193544ab

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }
}
