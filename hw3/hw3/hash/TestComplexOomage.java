package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
<<<<<<< HEAD
    @Test
=======
    /*@Test
>>>>>>> f6ac002c3d915b067db1761ce0317473193544ab
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
<<<<<<< HEAD
    }

=======
    }*/

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */
    /*
>>>>>>> f6ac002c3d915b067db1761ce0317473193544ab
    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

<<<<<<< HEAD
        ArrayList<Integer> params = new ArrayList<>();

        int n = 0;

        while (n < 100) {
            params.clear();
            params.add(1 + n);
            params.add(1);
            params.add(2);
            params.add(3);
            params.add(4);
            deadlyList.add(new ComplexOomage(params));
            n += 1;
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }
=======
        // Your code here.

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    } */
>>>>>>> f6ac002c3d915b067db1761ce0317473193544ab

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
