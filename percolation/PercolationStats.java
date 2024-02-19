import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

/**
 * PercolationStats - performs a series of computation to estimate the percolation 
 * threshold, consider the following computational experiment:
 *      • Initialise all sites to be blocked.
 *	• Repeat the following until the system percolates:
 *	    - Choose a site uniformly at randomly among the blocked sites.
 *          - Open the site.
 *	• The fraction of the site that are opened when the system percolates provides 
 *        an estimation of the percolation threshold.
 * By repeating this experiment T times and averaging the results, we obtain a more 
 * accurate estimate of the percolation threshold.
 *
 * @author Karthikeyan
 */
public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private double mean;      //sample mean
    private double stddev;    //smaple standard deviation
    private int t;            //no of experiments 
		
    /**
     * Performs independent trials on an n-by-n grid.
     *
     * @param n size of the grid
     * @param trials number of experiments 
     * @throws IllegalArgumentException if n or trials less than 1
     */
    public PercolationStats(int n, int trials) {
	if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
	double[] results = new double[trials]; 
	t = trials;	
	for (int i = 0; i < trials; i++) { 
	    Percolation percolation = new Percolation(n); 		
	    while (!percolation.percolates()) {
	        int p = StdRandom.uniformInt(1, n + 1); 
		int q = StdRandom.uniformInt(1, n + 1); 
		percolation.open(p, q); 
	    }  			
	    results[i] = percolation.numberOfOpenSites() * 1.0 / (n * n);
	} 		
	mean   = StdStats.mean(results); 
	stddev = StdStats.stddev(results); 
    } 
	
    /**
     * Returns the sample mean of the percolation threshold.
     *
     * @return {@code mean} 
     */
    public double mean() 
    {   return mean;   } 
	
    /**
     * Returns the sample standard deviation of the percolation threshold.
     *
     * @return {@code stddev} 
     */	
    public double stddev() 
    {	return stddev;	 }  
	
    /**
     * Returns the low end point of 95% confidence interval.
     *
     * @return the low end point 
     */
    public double confidenceLo() 
    {	return mean -  CONFIDENCE_95 * stddev / Math.sqrt(t); } 
	
    /**
     * Returns the high end point of 95% confidence interval. 
     *
     * @return the high end point 
     */
    public double confidenceHi() 
    {	return mean + CONFIDENCE_95 * stddev / Math.sqrt(t); }
	
    /**
     * Test client (see below).
     */	
    public static void main(String[] args) {  
	int n = Integer.parseInt(args[0]); 
	int trials = Integer.parseInt(args[1]); 
	PercolationStats stats = new PercolationStats(n, trials);
	double lo = stats.confidenceLo(); 
	double hi = stats.confidenceHi();
		
	StdOut.println("mean                       = " + stats.mean()); 
	StdOut.println("stddev                     = " + stats.stddev()); 
	StdOut.println("95% of confidence interval = [" + lo + ", " + hi + "]"); 	
    }
}
