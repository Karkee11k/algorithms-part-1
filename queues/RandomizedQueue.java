import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * The {@code RandomizedQueue} class represents a randomized queue of generic items. 
 * It is similar to stack or queue, except that the item removed is chosen uniformly 
 * at random among the items in the queue. It supports the usual enqueue and dequeue 
 * along with other methods of sampling getting a random item, testing if the queue is 
 * empty, getting the number of items in the queue, and iterating over the items  in 
 * uniformly random order.
 *
 * @param <Item> the generic type of each item in the queue
 * @author Karthikeyan
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    // initial capacity of the underlying array
    private static final int INIT_CAPACITY = 8; 
    private Item[] q;  // queue elements
    private int n;     // number of items and the end of the queue
	
    /**
     * Initialises the queue with the initial capacity
     */
    public RandomizedQueue() {
	q = (Item[]) new Object[INIT_CAPACITY]; 
	n = 0; 
    } 
	
    /**
     * Returns true if the queue is empty
     *
     * @return {@code true} if empty; otherwise {@code false}
     */
    public boolean isEmpty() 
    {   return n == 0;     } 
	
    /**
     * Returns the size of the queue
     *
     * @return {@code n} 
     */
    public int size() 
    {   return n;   }
	
    /**
     * Adds the item to the queue and resizes the array if it is full 
     *
     * @param item the item added to the queue
     * @throws IllegalArgumentException if the item is null 
     */
    public void enqueue(Item item) {
	if (item == null) throw new IllegalArgumentException();
	if (n == q.length) resize(2*n); 
	q[n++] = item; 
    } 
	
    /**
     * Removes and returns a random item and resizes the array if 
     * it is one - quarter full
     *
     * @throws NoSuchElementException if queue is empty
     * @return the removed random item
     */
    public Item dequeue() {
	if (isEmpty()) throw new NoSuchElementException(); 
	int i = StdRandom.uniformInt(n);
	Item item = q[i]; 
	q[i] = q[--n];
	q[n] = null;  // to avoid loitering
	if (n > 0 && n == q.length / 4) resize(q.length / 2);
	return item; 
    } 
	
    /** 
     * Returns a random item in the queue 
     * 
     * @throws NoSuchElementException if queue is empty 
     * @return a random item in the queue 
     */
    public Item sample() {
	if (isEmpty()) throw new NoSuchElementException(); 
	return q[StdRandom.uniformInt(n)]; 
    } 
	
    /** 
     * Returns an iterator that iterates over the item in random order 
     *
     * @return an iterator that iterates over the item in random order
     */
    public Iterator<Item> iterator()
    {	return new RQIterator();   } 
	
    /**
     * Resizes the underlying array 
     */
    private void resize(int capacity) {
	Item[] copy = (Item[]) new Object[capacity]; 
	for (int i = 0; i < n; i++) copy[i] = q[i]; 
	q = copy; 
    }
	
    // the queue iterator, for iterating over the queue in random order
    private class RQIterator implements Iterator<Item> {
	private Item[] q1; 
	private int i; 
		
	public RQIterator() {
	    q1 = (Item[]) new Object[n]; 
	    for (i = 0; i < n; i++) q1[i] = q[i]; 
	}
		 
	public boolean hasNext() 
	{   return i > 0;      }
		
	public void remove() 
	{ throw new UnsupportedOperationException(); }
		
	public Item next() {
	    if (!hasNext()) throw new NoSuchElementException(); 
	    int k = StdRandom.uniformInt(i); 
	    Item item = q1[k]; 
	    q1[k] = q1[--i]; 
	    q1[i] = null; 
	    return item; 
	}
    }		
	
    /**
     * Unit tests the code 
     */
    public static void main(String[] args) {
	RandomizedQueue<Integer> q = new RandomizedQueue<>();
	StdOut.println("Test - 1 empty queue: " + q.isEmpty()); 
	q.enqueue(1); 
	q.enqueue(2); 
	q.enqueue(3);
	StdOut.println("Test - 2 size: " + q.size()); 
	StdOut.println("Test - 3 dequeue: " + q.dequeue()); 
	StdOut.println("Test - 4 sample: " + q.sample()); 
	Iterator<Integer> iterator = q.iterator(); 
	StdOut.println("Test - 5A iterator hasNext: " + iterator.hasNext()); 
	StdOut.println("Test - 5B iterator next: " + iterator.next() + ", " + iterator.next()); 
    }	
}
