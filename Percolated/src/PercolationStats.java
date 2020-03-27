import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats {
    private static int trials2,n2;
    private double x[];
    private Percolation[] ob2 = new Percolation[trials2];
    // Performs independent trials on an n by n grid
    public PercolationStats(int n, int trials){
        int k,h;
        n2=n;
        trials2 = trials ;
        x = new double[trials];
        int open=0;
        if((n<=0)||(trials<=0)){
            throw new IllegalArgumentException("n and Trials should not be less tha 0");
        }
        for(int i = 0 ; i < trials ; i++){
            ob2[i] = new Percolation(n);
            do{
                k = StdRandom.uniform(1,n+1);
                h = StdRandom.uniform(1,n+1);
               if(!ob2[i].isopen(k,h)){
                   ob2[i].open(k,h);
                   open++;
               }
            }while(!ob2[i].Percolates());
            x[i]=((double)(ob2[i].NumberOfOpenSites())/(double)(n*n));
        }
    }
    //Finds the mean of the independent trials performed
    public double mean(){
        return StdStats.mean(x);
    }
    //Finds the standard deviation
    public double stddev(){
        return StdStats.stddev(x);
    }
    //Lower limit of confidence interval
    public double confidenceLo(){
        double lo ;
        double temp;

        double sr = trials2 / 2;

        do {
            temp = sr;
            sr = (temp + (trials2 / temp)) / 2;
        } while ((temp - sr) != 0);

        lo = mean()-((1.96*stddev())/sr);
        return lo;
    }
    //Upper limit of confidence interval
    public double confidenceHi(){
        double lo ;
        double temp;

        double sr = trials2 / 2;

        do {
            temp = sr;
            sr = (temp + (trials2 / temp)) / 2;
        } while ((temp - sr) != 0);

        lo = mean()+((1.96*stddev())/sr);
        return lo;
    }
    //Test client
    public static void main(String[] args){
        StdOut output = null ;
        StdIn input = null;
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        trials2 = m;
        PercolationStats last = new PercolationStats(n,m);
        StdOut.println("The mean                       =  " + last.mean());
        StdOut.println("The standard deviation         =  " + last.stddev());
        StdOut.println("The confidence interval        =  " + last.confidenceLo() + " , " + last.confidenceHi() );
    }
}
