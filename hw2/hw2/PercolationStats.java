package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private double[] testing;
    private double amount;

    public PercolationStats(int N, int T, PercolationFactory pF) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        amount = (double) T;
        testing = new double[T];
        int i = 0;
        while (i < T) {
            int y = 0;
            int[] randRow = new int[N];
            int[] randCol = new int[N];
            while (y < N) {
                randRow[y] = y;
                randCol[y] = y;
                y += 1;
            }
            StdRandom.shuffle(randRow);
            StdRandom.shuffle(randCol);
            Percolation tester = pF.make(N);
            int opened = 0;
            while (!tester.percolates()) {
                int x = randRow[StdRandom.uniform(20)];
                int z = randCol[StdRandom.uniform(20)];
                if (!tester.isOpen(x, z)) {
                    tester.open(x, z);
                    opened += 1;
                }
            }
            testing[i] = (((double) opened) / ((double) (N * N)));
            i += 1;
        }

    }

    public double mean() {
        return StdStats.mean(testing);
    }

    public double stddev() {
        return StdStats.stddev(testing);
    }

    public double confidenceLow() {
        return (mean() - ((1.96 * stddev())/(java.lang.Math.sqrt(amount))));
    }

    public double confidenceHigh() {
        return (mean() + ((1.96 * stddev())/(java.lang.Math.sqrt(amount))));
    }

    public static void main(String[] args) {
        PercolationStats tester = new PercolationStats(20, 10, new PercolationFactory());
        System.out.println(tester.mean());
    }

}
