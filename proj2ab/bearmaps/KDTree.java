package bearmaps;

import java.util.List;
import java.util.ArrayList;

public class KDTree implements PointSet {

    private Node rootNode;

    private class Node {
        private Point point;
        private Node leftChild;
        private Node rightChild;
        /* If true, it is an up/down (compare yVal) node.
        If it is false, it is an left/right (compare xVal) node*/
        private boolean orientation;

        public Node(Point actualPoint, Node left, Node right) {
            point = actualPoint;
            leftChild = left;
            rightChild = right;
        }

        public Point getPoint() {
            return point;
        }

        public double getXVal() {
            return point.getX();
        }

        public double getYVal() {
            return point.getY();
        }

        public boolean getOrientation() {
            return orientation;
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public Node getRightChild() {
            return rightChild;
        }

        /* Returns the square distance, not the sqrt version. */
        public double distance(Point comparison) {
            return Point.distance(comparison, point);
        }

        public void changePoint(Point newPoint) {
            point = newPoint;
        }

        public void changeOrientation(boolean y) {
            orientation = y;
        }

    }

    /* Insert is the node being inserted, orient is a boolean that determines whether
    * to use the left/right case or the up/down case. Start is the node that is being
    * compared to. Start is supposed to change until the insert node sets properly.*/
    private void orientTheNode(Node insert, boolean orient, Node start) {
        if (orient) {
            if ((insert.getXVal() == start.getXVal()) && (insert.getYVal() == start.getYVal())) {
                return;
            } else if (insert.getXVal() < start.getXVal()) {
                /* What if the node is already taken? */
                if (start.leftChild == null) {
                    start.leftChild = insert;
                    insert.changeOrientation(orient);
                } else {
                    orientTheNode(insert, !orient, start.leftChild);
                }
            } else {
                if (start.rightChild == null) {
                    start.rightChild = insert;
                    insert.changeOrientation(orient);
                } else {
                    orientTheNode(insert, !orient, start.rightChild);
                }
            }
        } else {
            if ((insert.getXVal() == start.getXVal()) && (insert.getYVal() == start.getYVal())) {
                return;
            } else if (insert.getYVal() < start.getYVal()) {
                if (start.leftChild == null) {
                    start.leftChild = insert;
                    insert.changeOrientation(orient);
                } else {
                    orientTheNode(insert, !orient, start.leftChild);
                }
            } else {
                if (start.rightChild == null) {
                    start.rightChild = insert;
                    insert.changeOrientation(orient);
                } else {
                    orientTheNode(insert, !orient, start.rightChild);
                }
            }
        }
    }

    /* Assume points had at least one point */
    /* Should create the initial KDTree with leftChildren and rightChildren */
    public KDTree(List<Point> points) {
        ArrayList<Point> holder = new ArrayList<>();
        int size = points.size();
        for (int i = 0; i < size; i++) {
            holder.add(i, points.get(i));
        }
        points = holder;
        rootNode = new Node(points.get(0), null, null);
        for (int i = 1; i < points.size(); i++) {
            Node insertion = new Node(points.get(i), null, null);
            orientTheNode(insertion, true, rootNode);
        }
    }

    /* Should take logN time, where N is the number of points */
    public Point nearest(double x, double y) {
        Point comparison = new Point(x, y);
        /* Starts by assuming the input Node and the closest Node are the
        * rootNode. */
        return nearest(rootNode, comparison, rootNode).getPoint();
    }

    /* Used the help given to us in the pseudo walkthrough video */
    private Node nearest(Node input, Point comparison, Node closest) {
        if (input == null) {
            return closest;
        }
        if (input.distance(comparison) < closest.distance(comparison)) {
            closest = input;
        }
        /* Should the above be an if/else clause? */
        Node goodSide;
        Node badSide;
        /* If node is a left/right node. */
        if (!input.getOrientation()) {
            if (comparison.getX() < input.getXVal()) {
                goodSide = input.getLeftChild();
                badSide = input.getRightChild();
            } else {
                goodSide = input.getRightChild();
                badSide = input.getLeftChild();
            }
        /* If node is an up/down node. */
        } else {
            if (comparison.getY() < input.getYVal()) {
                goodSide = input.getLeftChild();
                badSide = input.getRightChild();
            } else {
                goodSide = input.getRightChild();
                badSide = input.getLeftChild();
            }
        }
        closest = nearest(goodSide, comparison, closest);
        /* If node is a left/right node.
        * Checks if badSide can have the closest node. */
        if (!input.getOrientation()) {
            if (((comparison.getX() - input.getXVal()) * (comparison.getX() - input.getXVal()))
                    < closest.distance(comparison)) {
                closest = nearest(badSide, comparison, closest);
            }
        /* If node is a up/down node.
        * Checks if badSide can have the closest node. */
        } else {
            if (((comparison.getY() - input.getYVal()) * (comparison.getY() - input.getYVal()))
                    < closest.distance(comparison)) {
                closest = nearest(badSide, comparison, closest);
            }
        }
        return closest;
    }

    public static void main(String args[]) {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(1, 5);
        Point p6 = new Point(4, 4);
        KDTree tester = new KDTree(List.of(p1, p2, p3, p4, p5, p6));
        Point answer = tester.nearest(0, 7);
        int x = 0;
    }

}
