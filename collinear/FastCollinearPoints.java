import java.util.Arrays;
import java.util.LinkedList;

/**
 * The {@code FastCollinearPoints} class checks collinear points and returning 
 * all the line segments. Given a point p as the origin, sort the points
 * according to the slopes they makes with p. Check if any 3 or more adjacent
 * points in the sorted order have equal slopes with respect to p. If so these
 * points, together with p are collinear.
 * 
 * @author Karthikeyan 
 */
public class FastCollinearPoints {
    // LineSegment array to store the line segments
    private LineSegment[] segments;

    /**
     * Finds all the lines containing 4 or more collinear points.
     * @param points the points array
     * @throws IllegalArgumentException if points is null or points has 
     * any null reference or has any duplicates.
     */
    public FastCollinearPoints(Point[] points) {
        if (checkNull(points)) {
            throw new IllegalArgumentException("Contains null reference");
        }

        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);

        if (checkDuplicates(sortedPoints)) {
            throw new IllegalArgumentException("Duplicate points are not allowed");
        }

        int n = points.length;
        LinkedList<LineSegment> ls = new LinkedList<>(); 

        for (int i = 0; i < n; i++) {
            Point[] slobeSortedPoints = sortedPoints.clone();
            Point start = sortedPoints[i];
            Arrays.sort(slobeSortedPoints, start.slopeOrder());

            for (int j = 0; j < n;) {
                double slope = start.slopeTo(slobeSortedPoints[j]);
                LinkedList<Point> pts = new LinkedList<>();

                while (j < n && start.slopeTo(slobeSortedPoints[j]) == slope) {
                    pts.add(slobeSortedPoints[j++]);
                }

                if (pts.size() >= 3 && start.compareTo(pts.get(0)) < 0) {
                    ls.add(new LineSegment(start, pts.getLast()));
                }
            }
        }
        segments = ls.toArray(new LineSegment[0]);
    }

    /**
     * Returns the number of line segments
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * Returns the line segments
     * @return {@code segments}
     */
    public LineSegment[] segments() {
        return segments.clone();
    }

    // Returns true if points[] is null or has any null reference
    private boolean checkNull(Point[] points) {
        if (points == null) return true;
        for (int i = 0; i < points.length; i++) 
            if (points[i] == null) return true;
        return false;
    }

    // Returns true if points[] has any duplicate points
    private boolean checkDuplicates(Point[] points) {
        for (int i = 1; i < points.length; i++) 
            if (points[i].compareTo(points[i-1]) == 0) 
                return true;
        return false;
    }

    public static void main(String[] args) {
        
    }
}
