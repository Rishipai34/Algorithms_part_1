import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int open[];
    private WeightedQuickUnionUF ob1;
    private int open_num ;
    private int n2;
    /* Creates a grid of n by n staring from 0 to n^2 +1
    where 0 and n^2 +1 are reserved for uppermost and lowermost element
     */
    public Percolation(int n ){
        n2 =n;
        open_num=0;
        ob1 = new WeightedQuickUnionUF(n*n+2);
        open = new int[n*n+2];
        for(int i=0; i < n; i++){
            ob1.union(i+1,0);
            ob1.union(n*n - i , n*n + 1);
        }
    }
    //Opens a given cell
    public void open( int row, int col){
        int k;
        k = (row-1)*n2 + col ;
        if(k>(n2*n2)||(k<1)){
            throw new IllegalArgumentException("Argument out of range ");
        }
        open[k]=1;
        open_num+=1;
        if(row!=n2){
            if(isopen(row+1,col)) ob1.union(k+n2,k);
        }
        if(col != 1 ){
            if(isopen(row, col -1)) ob1.union(k,k-1);
        }
        if (row != 1){
            if(isopen(row-1,col)) ob1.union(k,k-n2);
        }
        if(col != n2 ) {
            if (isopen(row , col + 1)) ob1.union(k , k + 1);
        }

    }
    //Checks if the cell is open
    public boolean isopen(int row, int col){
        int k;
        k= (row-1)*n2 + col;
        if(k>(n2*n2)||(k<1)){
            throw new IllegalArgumentException("Argument out of range k = " + k );
        }
        return(open[k]==1);
    }
    //Checks if the cell is connected to root
    public boolean isfull(int row , int col ){
        int k;
        k= (row-1)*n2 + col;
        if(k>(n2*n2)||(k<1)){
            throw new IllegalArgumentException("Argument out of range ");
        }
        return ob1.connected(k,0);

    }
    // Used to find the number of open sites
    public int NumberOfOpenSites(){
        return open_num;
    }
    //Checks if system percolates
    public boolean Percolates(){
        return ob1.connected((n2 * n2 + 1), 0);
    }
}
