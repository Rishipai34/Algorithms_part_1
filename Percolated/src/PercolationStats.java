import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats {
    private Percolation ob2[];
    private int open = 0;
    private int n2 , trials2;
    private int x[];
    private StdStats stat;
    private StdRandom rand;
    private static StdIn input;
    // Performs independent trials on an n by n grid
    public PercolationStats(int n, int trials){
        int k,h;
        n2=n;
        trials2 = trials ;
        if((n<=0)||(trials<=0)){
            throw new IllegalArgumentException("n and Trials should not be less tha 0");
        }

        for(int i = 0 ; i < trials; i++){
            ob2[i] = new Percolation(n);
            while(!ob2[i].Percolates()){
                k = rand.uniform(1,n2+1);
                h = rand.uniform(1,n2+1);
               if(!ob2[i].isopen(k,h)){
                   ob2[i].open(k,h);
                   open++;
               }
            }
            x[i]=(open/(n2*n2));
        }
    }
    //Finds the mean of the independent trials performed
    public double mean(){
        return stat.mean(x);
    }
    //Finds the standard deviation
    public double stddev(){
        return stat.stddev(x);
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
        int n = input.readInt();
        int m = input.readInt();
        PercolationStats last = new PercolationStats(n,m);
        StdOut.println("The mean                       =  " + last.mean());
        StdOut.println("The standard deviation         =  " + last.stddev());
        StdOut.println("The confidence interval        =  " + last.confidenceLo() + " , " + last.confidenceHi() );
    }
}
