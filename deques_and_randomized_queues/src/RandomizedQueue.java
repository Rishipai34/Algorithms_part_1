import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int head = 0, tail = 1;
    private Item[] item;
    private int N = 0;

    public Iterator<Item> iterator() {
        return (new listIterator());
    }

    //Iterator
    private class listIterator implements Iterator<Item> {
        private int i = tail - head;

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            i--;
            return item[StdRandom.uniform(head, tail)];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported in this API");
        }
    }

    //Creates a randomized queue
    public RandomizedQueue() {
        Item[] item = (Item[]) new Object[1];
    }

    //Checks if the queue is empty
    public boolean isEmpty() {
        return (head == tail);
    }

    //Adds to the queue
    public void enqueue(Item item2) {
        if (item2 == null) {
            throw new IllegalArgumentException("Argument item cannot be null ");
        }
        if (item.length == (tail - head)) resize(2 * item.length);
        int i = StdRandom.uniform(head, tail);
        tail++;
        item[tail] = item[i];
        item[i] = item2;

    }

    //Randomly removes an item from the queue
    public Item dequeue() {
        if (head == tail) {
            throw new NoSuchElementException("The queue is empty ");
        }
        if ((tail - head) <= 0.25 * item.length) {
            resize((int) (0.25 * item.length));
        }
        int i = StdRandom.uniform(head, tail);
        Item temp = item[i];
        item[i] = item[tail];
        tail--;
        return temp;
    }

    //resize the array
    private void resize(int n) {
        int i, j;
        Item[] copy = (Item[]) new Object[n];
        for (i = 0, j = head; i < n; i++, j++) {
            copy[i] = item[j];
        }
        item = copy;
        head = 0;
        tail = i;
    }

    //Returns the size of the queue( The remaining elements )
    public int size() {
        return tail - head;
    }

    //Returns a sample element without removing it
    public Item sample() {
        if (tail == head) {
            throw new NoSuchElementException("The queue is empty ");
        }
        return item[StdRandom.uniform(head, tail)];
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> newObject = new RandomizedQueue<Integer>();

        int[] a = {1, 2, 3, 4, 5, 5, 6, 7, 7};
        for (int i = 0; i <= 8; i++) {
            newObject.enqueue(a[i]);
        }
        StdOut.println("Test character is " + newObject.sample());
        StdOut.println("The characters are (for size)" + newObject.size() + ":");
        for (int s : a) {
            StdOut.print(newObject.dequeue());
        }
    }
}
