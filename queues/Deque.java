import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code Deque} class represents a double-ended queue or 
 * deque (pronounced “deck”) is a generalization of a stack and a 
 * queue that supports adding and removing items from either 
 * the front or the back of the data structure.
 *
 * @param <Item> the generic type of each item in the queue
 * @author Karthikeyan 
 */
public class Deque<Item> implements Iterable<Item> {
	
    // helper linked list class
    private class Node {
        Item item; 
	Node prev, next; 
	Node(Item item) { this.item = item; } 
    }  
	
    private Node first, last;  // beginning and end of queue
    private int n;             // number of elements on queue
	
    /**
     * Initialises an empty deque
     */
    public Deque() {
	n = 0; 
    } 
	
    /** 
     * Returns true if the deque is empty 
     *
     * @return true if deque empty else false 
     */
    public boolean isEmpty() 
    {   return n == 0;     } 
	
    /**
     * Returns the number of items in the deque 
     *
     * @return {@code n} 
     */
    public int size() 
    {   return n;   } 
	
    /** 
     * Adds the item in the first position
     *
     * @param item the item to add
     * @throws IllegalArgumentException if item is null 
     */
    public void addFirst(Item item) {
	if (item == null) throw new IllegalArgumentException();
	if (isEmpty()) {
	    first = last = new Node(item);  
	} 
	else {
	    Node oldFirst = first; 
	    first = new Node(item); 
	    first.next = oldFirst; 
	    oldFirst.prev = first; 
	} 
	n++; 
    } 
	
    /** 
     * Adds the item in the last position
     *
     * @param item the item to add 
     * @throws IllegalArgumentException if item is null 
     */
    public void addLast(Item item) {
	if (item == null) throw new IllegalArgumentException(); 
	if (isEmpty()) {
	    first = last = new Node(item);
	} 
	else {	
	    Node oldLast = last; 
	    last = new Node(item); 
	    oldLast.next = last; 
	    last.prev = oldLast;  
	} 
	n++;
    } 
	
    /** 
     * Removes and returns the first item in the deque 
     *
     * @throws NoSuchElementException if deque is empty 
     * @return the first item in the deque 
     */
    public Item removeFirst() {
	if (isEmpty()) throw new NoSuchElementException(); 
	n--; 
	Node oldFirst = first; 
	first = first.next; 
	if (isEmpty()) first = last = null; 
	else           first.prev = oldFirst.next = null; 
	return oldFirst.item; 
    }

    /** 
     * Removes and returns the last item in the deque 
     *
     * @throws NoSuchElementException if deque is empty 
     * @return the last item in the deque 
     */	
    public Item removeLast() {
	if (isEmpty()) throw new NoSuchElementException(); 
	n--; 
	Node oldLast = last; 
	last = last.prev; 
	if (isEmpty())  first = last = null; 
	else            last.next = oldLast.prev = null; 
	return oldLast.item; 
    } 
	
    /** 
     * Returns an iterator that iterates over the deque in FIFO order 
     *
     * @return an iterator that iterates over the deque in FIFO order
     */
    public Iterator<Item> iterator() 
    {   return new DequeIterator(); }
	
    // DequeIterator, that iterates the deque in FIFO order
    private class DequeIterator implements Iterator<Item> {
	Node i = first; 
		
	public boolean hasNext() 
	{   return i != null;  } 
		
	public void remove() 
	{   throw new UnsupportedOperationException();  } 
		
	public Item next() {
	    if (!hasNext()) throw new NoSuchElementException(); 
	    Item item = i.item; 
	    i = i.next; 
	    return item; 
	} 
    }
	
    /** 
     * Unit tests the code 
     */
    public static void main(String[] args) {
	Deque<Integer> q = new Deque<>(); 
	StdOut.println("Test - 1 empty deque: " + q.isEmpty()); 
	q.addFirst(2);
	q.addLast(3); 
	q.addLast(4); 
	q.addFirst(1);
	StdOut.println("Test - 2 deque size: " + q.size()); 
	StdOut.println("Test - 3 removeFirst: " + q.removeFirst()); 
	StdOut.println("Test - 4 removeLast: " + q.removeLast()); 
	Iterator<Integer> iterator = q.iterator();
	StdOut.println("Test - 5 hasNext: " + iterator.hasNext()); 
	StdOut.println("Test - 6 next: " + iterator.next() + ", " + iterator.next()); 
	StdOut.println("Test - 5A hasNext: " + iterator.hasNext()); 
    }		
}
