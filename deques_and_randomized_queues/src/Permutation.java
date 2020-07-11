import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int i = Integer.parseInt(args[0]);
        RandomizedQueue<String> ob1 = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            ob1.enqueue(s);
        }
        for (int j = 0; j < i; j++) {
            String s = ob1.dequeue();
            if (s == null) {
                j--;
                continue;
            }
            StdOut.println(s);
        }
    }
}
