import java.util.TreeSet;
import java.util.LinkedList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * The class PointSET represents a set of points in a unit square.
 * 
 * @author Karthikeyan
 */
public class PointSET {
    private TreeSet<Point2D> points;  // points set

    /**
     * Constructs an empty set of points.
     */
    public PointSET() {
        points = new TreeSet<>();
    }

    /**
     * Returns true if the set if empty; false otherwise.
     * @return returns true if the set is empty; false otherwise
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * Returns the number of points in the set.
     * @return returns the number of points in the set
     */
    public int size() {
        return points.size();
    }

    /**
     * Adds the point to the set (if it is not already in the set).
     * @param p the point to be added
     * @throws IllegalArgumentException if the point is null
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null values not allowed");
        points.add(p);
    }

    /**
     * Returns true if the point in the set, else false.
     * @param p the point to be added
     * @throws IllegalArgumentException if the point is null
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null values not allowed");
        return points.contains(p);
    }

    /**
     * Draws all points to the standard draw.
     */
    public void draw() {
        for (Point2D point : points)
            point.draw();
    }

    /**
     * Returns all the points that are inside the rectangle.
     * @param rect the rectangle to check
     * @throws IllegalArgumentException if the rect is null
     * @return returns an iterable 
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null values not allowed");
        LinkedList<Point2D> ls = new LinkedList<>();
        Point2D lo = new Point2D(rect.xmin(), rect.ymin());
        Point2D hi = new Point2D(rect.xmax(), rect.ymax());
        for (Point2D point : points.subSet(lo, true, hi, true)) {
            if (point.x() >= lo.x() && point.x() <= hi.x())
                ls.add(point);
        }
        return ls;
    }

    /**
     * Returns the point that is nearest to the given point.
     * @param p the point to check
     * @throws IllegalArgumentException if the given point is null
     * @return the point that is nearest to the given point
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null values not allowed");
        if (isEmpty()) return null;
        Point2D floor = points.floor(p);
        Point2D ceil = points.ceiling(p);
        Point2D nearest = floor == null ? ceil : floor;

        double distanceToFloor = floor == null ? Double.POSITIVE_INFINITY : p.distanceTo(floor);
        double distanceToCeil = ceil == null ? Double.POSITIVE_INFINITY : p.distanceTo(ceil);
        double distance = Math.min(distanceToCeil, distanceToFloor);

        Point2D lo = new Point2D(p.x(), p.y() - distance);
        Point2D hi = new Point2D(p.x(), p.y() + distance);

        for (Point2D point : points.subSet(lo, true, hi, true))
            if (p.distanceTo(nearest) > p.distanceTo(point))
                nearest = point;
        return nearest;
    }

    // unit tests the code (optional)
    public static void main(String[] args) {

    }
}