import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code Point} class represents an immutable data type that represents a
 * the plane.
 * 
 * @author Karthikeyan
 */
public class Point implements Comparable<Point> {
    private final int x;  // x-coordinate of this point
    private final int y;  // y-coordinate of this point
    
    /**
     * Constructs a new point.
     * 
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to the standard draw
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * 
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns a string representation of the point
     * @return a string representation of the point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    /**
     * Compares two point by y-coordinates, breaking ties by x-coordinate
     * @param that the other point to be compared
     * @return 0 if the points are equal, negative integer if this point is 
     * less than that point, positive integer if this point is greater than
     * that point 
     */
    public int compareTo(Point that) {
        if (this.y == that.y) return this.x - that.x;
        return this.y - that.y; 
    }

    /**
     * Returns the slope between this point and the specified point
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            return this.y != that.y ? Double.POSITIVE_INFINITY : 
            Double.NEGATIVE_INFINITY;
        }
        if (this.y == that.y) return 0;
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    /**
     * Returns a comparator that compares two points by the slope they make with
     * this point
     * @return the comparator 
     */
    public Comparator<Point> slopeOrder() {
        return new PointComparator(this);
    }

    // Comparator to compare two points by the slope they make with this point
    private  class PointComparator implements Comparator<Point> {
        private Point point;

        public PointComparator(Point point) {
            this.point = point;
        }

        public int compare(Point v, Point w) {
            double slope1 = point.slopeTo(v);
            double slope2 = point.slopeTo(w);
            if (slope1 < slope2) return -1;
            if (slope1 == slope2) return 0;
            return 1;
        } 
    }

    /**
     * Unit tests the code 
     */
    public static void main(String[] args) {
        Point p = new Point(4, 4);
        Point q = new Point(5, 4);
        StdOut.println(p);
        StdOut.println(q);
        StdOut.println(p.compareTo(q));
    }
    
}
