import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] item;
    private int head, tail;

    // Creates a randomized queue
    public RandomizedQueue() {
        item = (Item[]) new Object[4];
        head = 0;
        tail = 0;
    }

    public Iterator<Item> iterator() {
        return (new ListIterator());
    }

    // Iterator
    private class ListIterator implements Iterator<Item> {
        private int j = tail - head - 1;

        public boolean hasNext() {
            return ((tail - j) > 0);
        }

        public Item next() {
            if (head == tail) {
                throw new NoSuchElementException("The queue is empty ");
            }
            return item[head + ++j];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported in this API");
        }
    }

    // Checks if the queue is empty
    public boolean isEmpty() {
        return (head == tail);
    }

    // Adds to the queue
    public void enqueue(Item item2) {
        if (item2 == null) {
            throw new IllegalArgumentException("Argument item cannot be null ");
        }
        if (item.length == tail - head) resize(2 * item.length);
        if (tail != head) {
            int i = StdRandom.uniform(head, tail);
            tail++;
            item[tail - 1] = item[i];
            item[i] = item2;
        } else {
            tail++;
            item[head] = item2;
        }
    }

    // Randomly removes an item from the queue
    public Item dequeue() {
        if (head == tail) {
            throw new NoSuchElementException("The queue is empty ");
        }
        int i = head;
        Item temp = item[i];
        item[i] = item[tail - 1];
        item[tail - 1] = null;
        tail--;
        if (((tail - head) <= (int) (0.25 * item.length)) && (item.length >= 4)) {
            resize((item.length) / 4);
        }
        return temp;
    }

    // resize the array
    private void resize(int n2) {
        int i, j;
        Item[] copy = (Item[]) new Object[n2];
        for (i = 0, j = head; j < tail; i++, j++) {
            copy[i] = item[j];
        }
        item = copy;
        head = 0;
        tail = i;

    }

    // Returns the size of the queue( The remaining elements )
    public int size() {
        return (tail - head);
    }

    // Returns a sample element without removing it
    public Item sample() {
        if (tail == head) {
            throw new NoSuchElementException("The queue is empty ");
        }
        return item[head];
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> newObject = new RandomizedQueue<Integer>();

        int[] a = {1, 2, 3, 4, 5, 5, 6, 12, 45, 77, 3, 45, 657, 6788, 890, 45, 6, 0};
        for (int i = 0; i < 18; i++) {
            newObject.enqueue(a[i]);
        }
        StdOut.println("Test character is " + newObject.sample());
        StdOut.println("The characters are (for size)" + newObject.size() + ":");
        for (int s : a) {
            if (!newObject.isEmpty()) StdOut.print(newObject.sample() + " ");
        }
        StdOut.println("Dequeued element is " + newObject.dequeue());
    }
}
