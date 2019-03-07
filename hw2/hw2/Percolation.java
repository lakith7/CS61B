package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private int openSites;
    private int size;
    private WeightedQuickUnionUF connector;

    public Percolation(int N) {
        if (N < 1) {
            throw new java.lang.IllegalArgumentException();
        }
        connector = new WeightedQuickUnionUF((N * N) + 2);
        int q = 1;
        int last = (N * N) + 1;
        while (q <= N) {
            connector.union(0, q);
            connector.union(last, last - q);
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
        return ((row*size) + col + 1);
    }

    public void open(int row, int col) {
        confirmer(row, col);
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
            }
        }
        if (leftRow < size && leftCol < size && leftRow > -1 && leftCol > -1) {
            if (isOpen(leftRow,leftCol)) {
                connector.union(holder, converter(leftRow, leftCol));
            }
        }
        if (bottomRow < size && bottomCol < size && bottomRow > -1 && bottomCol > -1) {
            if (isOpen(bottomRow, bottomCol)) {
                connector.union(holder, converter(bottomRow, bottomCol));
            }
        }
        if (rightRow < size && rightCol < size && rightRow > -1 && rightCol > -1) {
            if (isOpen(rightRow,rightCol)) {
                connector.union(holder, converter(rightRow, rightCol));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        confirmer(row, col);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        confirmer(row, col);
        return connector.connected(converter(row, col), 0);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return connector.connected(0, (size * size) + 1);
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
    }

}
