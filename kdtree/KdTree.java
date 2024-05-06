import java.util.LinkedList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * The class KdTree uses 2d-tree to support efficient range search (find all 
 * the points contained in a query rectangle) and nearest neighbor search (find
 * a point closest to a query point). A 2d-tree is a generalization of a BST 
 * with points in the nodes, using the x- and y- coordinates of the points as 
 * keys in strictly alternating sequence.
 * 
 * @author Karthikeyan
 */
public class KdTree {
    private static final boolean RED  = false; // x-coordinate color
    private static final boolean BLUE = true;  // y-coordinate color
    private Node root;                         // root of the tree

    /**
     * Returns true if empty; false otherwise.
     * @return returns true if empty; false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns number of points in the tree.
     * @return returns number of points in the tree
     */
    public int size() {
        return size(root);
    }

    // returns the size of the given subtree
    private int size(Node x) {
        return x == null ? 0 : x.count;
    }

    /**
     * Adds the given point in the tree if not already present.
     * @param p the point to be added
     * @throws IllegalArgumentException if the point is null
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null values not allowed");
        root = insert(root, null, p);
    }
    
    // adds the point to the tree
    private Node insert(Node x, Node prev, Point2D p) {
        if (x == null) return new Node(p, createRect(prev, p), 1);
        int cmp = compare(x, p);
        if (cmp < 0) {
            x.right = insert(x.right, x, p);
            x.right.line = !x.line;
        }
        else if (cmp > 0) {
            x.left = insert(x.left, x, p);
            x.left.line = !x.line;
        }
        x.count = size(x.left) + size(x.right) + 1;
        return x;
    }

    // creates and returns the rectangle for the given point
    private RectHV createRect(Node x, Point2D p) {
        if (x == null) return new RectHV(0, 0, 1, 1);
        return compare(x, p) < 0 ? x.rightRect() : x.leftRect();
    }

    /**
     * Returns true if the given point in the tree; false otherwise.
     * @param p the point to check
     * @throws IllegalArgumentException if the point is null
     * @return true if contains; false otherwise
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null values not allowed");
        Node x = root;
        while (x != null) {
            int cmp = compare(x, p);
            if (cmp == 0) return true;
            x = cmp < 0 ? x.right : x.left;
        }
        return false;
    }

    /**
     * Draws the points to the standard draw.
     */
    public void draw() {
        draw(root);
    }

    // draws the points to the standard draw
    private void draw(Node x) {
        if (x == null) return;
        draw(x.left);
        x.draw();
        draw(x.right);
    }

    /**
     * Returns an iterable with the points in the range.
     * @param rect the rectangle to check
     * @throws IllegalArgumentException if rect is null
     * @return an iterable
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null values not allowed");
        LinkedList<Point2D> ls = new LinkedList<>();
        add(root, ls, rect);
        return ls;
    }

    // adds the points within the rectangle
    private void add(Node x, LinkedList<Point2D> ls, RectHV rect) {
        if (x == null || !x.rect.intersects(rect)) return;
        if (rect.contains(x.point)) ls.add(x.point);
        add(x.left, ls, rect);
        add(x.right, ls, rect);
    }

    /**
     * Returns the nearest point of the given point.
     * @param p the point to check
     * @throws IllegalArgumentException if p is null
     * @return the rearest point
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null values are not allowed");
        if (isEmpty()) return null;
        return nearest(root, root.point, p);
    }

    // returns the nearest point
    private Point2D nearest(Node x, Point2D champion, Point2D query) {
        if (x == null || x.rect.distanceTo(query) >= champion.distanceTo(query)) 
            return champion;

        if (query.distanceTo(x.point) < query.distanceTo(champion))
            champion = x.point;
        if (compare(x, query) < 0) {
            champion = nearest(x.right, champion, query);
            champion = nearest(x.left, champion, query);
        }
        else {
            champion = nearest(x.left, champion, query);
            champion = nearest(x.right, champion, query);
        }
        return champion;
    }

    // compares the node with the point according to our kdtree
    private static int compare(Node node, Point2D point) {
        if (node.point.equals(point)) return 0;
        if (node.line != BLUE) 
            return node.point.x() < point.x() ? -1 : 1;
        return node.point.y() < point.y() ? -1 : 1;
    }
    
    // node of the tree
    private class Node {
        Point2D point;
        RectHV rect;
        Node left, right;
        int count;
        boolean line;

        Node(Point2D point, RectHV rect, int count) {
            this.point = point;
            this.count = count;
            this.rect  = rect;
        }

        // returns the left or bottom point's rectangle
        RectHV leftRect() {
            if (line == RED)
                return new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax());
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y());
        }

        // returns the right or top point's rectangle
        RectHV rightRect() {
            if (line == RED)
                return new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax());
            return new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax());
        }

        // draws the point in black and the subdivision (red for vertical | blue for horizontal) 
        void draw() {

            // drawing the line
            StdDraw.setPenRadius();
            RectHV rectHV = leftRect();
            double xmin, ymin;

            // settings for vertical line 
            if (line == RED) {
                StdDraw.setPenColor(StdDraw.RED);
                xmin = rectHV.xmax();
                ymin = rectHV.ymin();
            }

            // settings for horizontal line
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                xmin = rectHV.xmin();
                ymin = rectHV.ymax();
            }
            StdDraw.line(xmin, ymin, rectHV.xmax(), rectHV.ymax());

            // drawing the point
            StdDraw.setPenColor();
            StdDraw.setPenRadius(0.02);
            point.draw();
        }
    }

    // unit tests the code (optional)
    public static void main(String[] args) {

    }

    private static void inorder(Node x) {
        if (x == null) return;
        inorder(x.left);
        StdOut.println(x.point + " " + x.line);
        inorder(x.right);
    }
}