import java.util.Arrays;
import java.util.LinkedList;

/**
 * The {@code BruteCollinearPoints} class that examines 4 points at a time and
 * checks whether they all lie on the same line segment and returning all such
 * line segments.To check whether the 4 points p, q, r, and s are collinear, 
 * check whether the three slopes between p and q, between p and r, and between 
 * p and s are all equal.
 * 
 * @author Karthikeyan
 */
public class BruteCollinearPoints {
    // LineSegment array to store the collinear points line
    private LineSegment[] segments;

    /**
     * Finds all the line segments containing 4 points
     * @param points the points array 
     * @throws IllegalArgumentException if points[] is null or contains null
     * reference or duplicatepoints
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null || checkForNull(points)) throw new IllegalArgumentException();
        points = points.clone();
        Arrays.sort(points);
        if (checkDuplicates(points)) throw new IllegalArgumentException();
        
        int n = points.length;
        LinkedList<LineSegment> lineSegments = new LinkedList<>();

        for (int i = 0; i < n; i++) { 
            for (int j = i + 1; j < n; j++) {
                double slope = points[i].slopeTo(points[j]);

                for (int k = j + 1; k < n; k++) {
                    
                    if (slope == points[i].slopeTo(points[k])) {
                        for (int m = k + 1; m < n; m++) {
                            if (slope == points[i].slopeTo(points[m])) {
                                lineSegments.add(new LineSegment(points[i], points[m]));
                                break;
                            }
                        }                 
                    }
                }
            }
        }

        segments = lineSegments.toArray(new LineSegment[0]);
    }

    /**
     * Returns the number of line segments containing 4 points
     * @return {@code segments.length}
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * Returns the line segments containing 4 points
     * @return {@code segments}
     */
    public LineSegment[] segments() {
        return segments.clone();
    }

    // Returns true if points[] is null or contain null reference
    private boolean checkForNull(Point[] points) {
        for (int i = 0; i < points.length; i++) 
            if (points[i] == null) return true;
        return false;
    }

    // Returns true if points[] contain duplicate points
    private boolean checkDuplicates(Point[] points) {
        for (int i = 1; i < points.length; i++) 
            if (points[i].compareTo(points[i-1]) == 0) return true;
        return false;
    }

    // Unit tests the code
    public static void main(String[] args) { }
}
