package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class KDTreeTest {

    Random r = new Random(500);

    @Test
    /* Tests the lecture code with both the naive solution and the KDTree. */
    public void testLectureExample() {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(1, 5);
        Point p6 = new Point(4, 4);
        KDTree tester = new KDTree(List.of(p1, p2, p3, p4, p5, p6));
        NaivePointSet tester1 = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6));
        assertEquals(tester.nearest(0, 7), tester1.nearest(0, 7));
    }

    @Test
    /* This code was taken from the project homepage */
    public void testNaiveSolution() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        KDTree test3 = new KDTree(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        assertEquals(3.3, ret.getX(), 0); // evaluates to 3.3
        assertEquals(4.4, ret.getY(), 0); // evaluates to 4.4
        assertEquals(nn.nearest(3.0, 4.0), test3.nearest(3.0, 4.0));
    }

    @Test
    /* Tests the naive solution against the KDTree solution. */
    /* Fails sometimes when two points have the same distance
     * from the reference point. This happened only when I used
     * rand.nextInt(101). This is because, with a higher number
     * of calls, the chance that two points are equal distance from
     * the point is likely because of the bound of 101 for the int.
     * For doubles, the chances are ALOT smaller. */
    public void testKDTreeSolution() {
        LinkedList<Point> holder = new LinkedList<>();
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            Point tester = new Point(rand.nextDouble(), rand.nextDouble());
            holder.add(tester);
        }

        NaivePointSet test1 = new NaivePointSet(holder);
        KDTree test2 = new KDTree(holder);
        for (int y = 0; y < 1000; y++) {
            double xVal = rand.nextDouble();
            double yVal = rand.nextDouble();
            Point tester1 = test1.nearest(xVal, yVal);
            Point tester2 = test2.nearest(xVal, yVal);
            assertEquals(tester1, tester2);
        }
    }

    @Test
    public void testTimeKDTree() {
        /* Time test for the KDTree solution. */
        Stopwatch timer = new Stopwatch();
        ArrayList<Point> holder = new LinkedList<>();
        Random rand = new Random();
        for (int i = 0; i < 1000000; i++) {
            Point tester = new Point(rand.nextDouble(), rand.nextDouble());
            holder.add(tester);
        }
        KDTree test2 = new KDTree(holder);
        for (int y = 0; y < 100000; y++) {
            double xVal = rand.nextDouble();
            double yVal = rand.nextDouble();
            Point tester2 = test2.nearest(xVal, yVal);
        }
        System.out.println("Total time elapsed: " + timer.elapsedTime() +  " seconds.");
    }

    @Test
    public void testTimeNaivePointSet() {
        /* Time test for the KDTree solution. */
        /* This test took over 5.744 seconds. In contrast, my KDTree took 0.654 seconds. */
        Stopwatch timer = new Stopwatch();
        ArrayList<Point> holder = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 1000000; i++) {
            Point tester = new Point(rand.nextDouble(), rand.nextDouble());
            holder.add(tester);
        }
        NaivePointSet test1 = new NaivePointSet(holder);
        for (int y = 0; y < 100000; y++) {
            double xVal = rand.nextDouble();
            double yVal = rand.nextDouble();
            Point tester1 = test1.nearest(xVal, yVal);
        }
        System.out.println("Total time elapsed: " + timer.elapsedTime() +  " seconds.");
    }

    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            points.add(randomPoint());
        }
        return points;
    }

    private Point randomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);
    }

    /* This test was taken from the project walkthrough. I have other tests
    * that I wrote myself. I just wanted to make sure my code passed Hug's tests. */
    @Test
    public void testWith1000Points() {
        List<Point> points1000 = randomPoints(10000);
        NaivePointSet nps = new NaivePointSet(points1000);
        KDTree kd = new KDTree(points1000);

        List<Point> queries200 = randomPoints(2000);
        for (Point p: queries200) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }
}
