import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/** 
 * Client program {@code Permutation} that takes n strings and 
 * prints exactly {@code k} of them. 
 *
 * @author Karthikeyan 
 */
public class Permutation {
    public static void main(String[] args) {
	RandomizedQueue<String> q = new RandomizedQueue<>();
	int k = Integer.parseInt(args[0]); 
		
	while (!StdIn.isEmpty()) q.enqueue(StdIn.readString()); 
	while (k-- > 0) StdOut.println(q.dequeue());
    }		
}
