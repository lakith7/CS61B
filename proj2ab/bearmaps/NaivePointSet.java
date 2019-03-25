package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet{

    private List<Point> listOfPoints;

    public NaivePointSet(List<Point> points) {
        listOfPoints = points;
    }

    public Point nearest(double x, double y) {
        int size = listOfPoints.size();
        Point comparison = new Point(x, y);
        Point actualPoint = listOfPoints.get(0);
        double distance = comparison.distance(comparison, listOfPoints.get(0));
        for (int i = 1; i < size; i++) {
            double actualDistance = comparison.distance(comparison, listOfPoints.get(i));
            if (actualDistance < distance) {
                distance = actualDistance;
                actualPoint = listOfPoints.get(i);
            }
        }
        return actualPoint;
    }
}
