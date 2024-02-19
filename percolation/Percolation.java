import edu.princeton.cs.algs4.WeightedQuickUnionUF; 

/**
 * Percolation, given a composite systems comprised of randomly distributed
 * insulating and metallic materials: what fraction of the materials need to be
 * metallic so that the composite system is an electrical conductor? Given a
 * porus landscape with water on the surface (or oil below), under what conditions 
 * will the water be able to drain through to the bottom (or the oil to gush through 
 * to the surface)? Scientists have defined an abstract process known as percolation 
 * to model such situations.
 * 
 * @author Karthikeyan
 */
public class Percolation {
    private int n;                     // size of the grid
    private boolean[] sites;           // sites in the grid 
    private int openSites = 0;         // no. of open sites
    private int virtualTop; 	       // virtual top 
    private int virtualBtm;	       // virtual bottom
    private WeightedQuickUnionUF uf1;  // union find object 1
    private WeightedQuickUnionUF uf2;  // union find object 2
	
    /** 
     * Creates an n-by-n grid, with all the sites are blocked and initialises all the 
     * object and connects the virtual top to the top n sites and the virtual bottom 
     * to the bottom n sites.
     *
     * @param n size of the grid 
     * @throws IllegalArgumentException if n less than 1
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
	this.n = n; 
	sites = new boolean[n * n + 1]; 
	uf1 = new WeightedQuickUnionUF(n * n + 2); 
	uf2 = new WeightedQuickUnionUF(n * n + 1);	
	virtualTop = 0; 
	virtualBtm = n * n + 1;
	// connects the top and bottom sites to the virtual top and the virtual bottom.
	for (int i = 1; i <= n; i++) {
	    uf1.union(virtualTop, i); 
	    uf1.union(virtualBtm, n * n - i + 1);
	    uf2.union(virtualTop, i); 
	}
    } 
	
    /**
     * Opens a site if it not open and connect with adjacent opened sites.
     *
     * @param row  row index of the site (1-based)
     * @param col column index of the site (1-based)
     * @throws IllegalArgumentException if row or col are out of bounds 
     */	
    public void open(int row, int col) {
        if (isInvalid(row, col)) throw new IllegalArgumentException();
	int p = index(row, col); 
	if (sites[p]) return; 
		
	sites[p] = true; 
	openSites++;
	int left = index(row, col - 1), right = index(row, col + 1); 
	int up  = index(row - 1, col), down = index(row + 1, col);
		
	if (left != -1 && isOpen(row, col - 1))  tryUnion(p, left);
	if (right != -1 && isOpen(row, col + 1)) tryUnion(p, right);
	if (up != -1 && isOpen(row - 1, col))    tryUnion(p, up);
	if (down != -1 && isOpen(row + 1, col))  tryUnion(p, down);
    }
	
    /**
     * Returns true if the site is open. 
     *
     * @param row row index of the site (1-based) 
     * @param col column index of the site (1-based) 
     * @throws IllegalArgumentException if row or col are out of bounds 
     * @return {@code true} if the site is open; {@code false} otherwise 
     */		
    public boolean isOpen(int row, int col) {
	if (isInvalid(row, col)) throw new IllegalArgumentException(); 
	return sites[index(row, col)]; 
    }
	
    /**
     * Returns true if the given site is opened and connected to the top. 
     *
     * @param row row index of the site (1-based) 
     * @param col column index of the site (1-based)
     * @throws IllegalArgumentException if row or col are out of bounds 
     * @return {@code true} if the site is opened and connected to the top; {@code false} 
     * otherwise
     */	
    public boolean isFull(int row, int col) { 
	if (isInvalid(row, col)) throw new IllegalArgumentException();
	int p = index(row, col);
	boolean connected = uf2.find(p) == uf2.find(virtualTop);
	return isOpen(row, col) && connected;
    } 
	
    /**
     * Returns the number of open sites.
     *
     * @return {@code openSites}
     */
    public int numberOfOpenSites() 
    {   return openSites;         } 
	
    /**
     * Returns true if percolates. 
     *
     * @return {@code true} if the system percolates; {@code false} otherwise
     */	  
    public boolean percolates() {	
	if (n == 1) return isOpen(1, 1);
	return uf1.find(virtualTop) == uf1.find(virtualBtm);  
    }
	
    /**
     * Returns the 1d index for the given 2d row and columns.
     *
     * @return -1 if row or col are out of bounds; index otherwise
     */
    private int index(int row, int col) 
    {	return isInvalid(row, col) ? -1 : (row - 1) * n + col; }
     
    /**
     * Returns true if row or col is out of bounds.
     *
     * @param row row index of the site (1-based)
     * @param col column index of the site (1-based) 
     * @return {@code true} if row or col is out of bounds; {@code false} otherwise 
     */
    private boolean isInvalid(int row, int col)  
    {	return row < 1 || row > n || col < 1 || col > n;	}	
    
    /**
     * Unions two opened sites.
     * 
     * @param p opened site one 
     * @param q opened site two
     */    
    private void tryUnion(int p, int q) 
    {	uf1.union(p, q); uf2.union(p, q); }	

    public static void main(String[] args) {}		
}
