import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class test1 {
    public test1(int n){
        StdOut.println("New constructor"+ n);
    }
    public static void main(String[] args){
        StdOut output = null ;
        StdIn input = null;
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        test1 last = new test1(m);
        StdOut.println(" Test "+ n +" , "+ m);
    }
}
