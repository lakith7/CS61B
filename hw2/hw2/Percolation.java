package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private int openSites;
    private int size;
    private WeightedQuickUnionUF connector;
    private WeightedQuickUnionUF percolator;

    public Percolation(int N) {
        if (N < 1) {
            throw new java.lang.IllegalArgumentException();
        }
        connector = new WeightedQuickUnionUF((N * N) + 2);
        percolator = new WeightedQuickUnionUF((N * N) + 1);
        int q = 1;
        int last = (N * N) + 1;
        while (q <= N) {
            connector.union(0, q);
            connector.union(last, last - q);
            percolator.union(0, q);
            q += 1;
        }
        size = N;
        grid = new boolean[N][N];
        for (int j = 0; j < N; j += 1) {
            for (int i = 0; i < N; i += 1) {
                grid[j][i] = false;
            }
        }
    }

    public void confirmer(int row, int col) {
        if (row > (size-1) || col > (size - 1) || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public int converter(int row, int col) {
        return ((row * size) + col + 1);
    }

    public void open(int row, int col) {
        confirmer(row, col);
        if (isOpen(row, col)) {
            return;
        }
        openSites += 1;
        grid[row][col] = true;
        int holder = converter(row, col);
        int topRow = row - 1;
        int topCol = col;
        int leftRow = row;
        int leftCol = col -1;
        int rightRow = row;
        int rightCol = col + 1;
        int bottomRow = row + 1;
        int bottomCol = col;
        if (topRow < size && topCol < size && topRow > -1 && topCol > -1) {
            if (isOpen(topRow,topCol)) {
                connector.union(holder, converter(topRow, topCol));
                percolator.union(holder, converter(topRow, topCol));
            }
        }
        if (leftRow < size && leftCol < size && leftRow > -1 && leftCol > -1) {
            if (isOpen(leftRow,leftCol)) {
                connector.union(holder, converter(leftRow, leftCol));
                percolator.union(holder, converter(leftRow, leftCol));
            }
        }
        if (bottomRow < size && bottomCol < size && bottomRow > -1 && bottomCol > -1) {
            if (isOpen(bottomRow, bottomCol)) {
                connector.union(holder, converter(bottomRow, bottomCol));
                percolator.union(holder, converter(bottomRow, bottomCol));
            }
        }
        if (rightRow < size && rightCol < size && rightRow > -1 && rightCol > -1) {
            if (isOpen(rightRow,rightCol)) {
                connector.union(holder, converter(rightRow, rightCol));
                percolator.union(holder, converter(rightRow, rightCol));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        confirmer(row, col);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        confirmer(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        if (connector.connected(converter(row, col), 0)) {
            return percolator.connected(converter(row, col), 0);
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        if (size == 1) {
            if (!isOpen(0,0)) {
                return false;
            }
            return true;
        }
        return (connector.connected(0, (size * size) + 1));
    }

    public static void main(String[] args) {
        Percolation item = new Percolation(3);
        item.open(1, 1);
        item.open(1, 2);
        item.open(0, 0);
        item.open(0, 1);
        System.out.println(item.isFull(1, 2));
        item.open(2, 2);
        System.out.println(item.percolates());
        System.out.println(item.numberOfOpenSites());
        System.out.println(item.percolates());
        Percolation tester = new Percolation(1);
        System.out.println(tester.percolates());
    }

}
