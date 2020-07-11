import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private node first, last;
    private int siz;

    // Create an empty queue
    public Deque() {
        first = null;
        last = null;
        siz = 0;
    }

    private class node {
        Item item2;
        node next;
        node previous;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private node current = first;

        public boolean hasNext() {
            return (current != null);
        }

        public void remove() {
            throw new UnsupportedOperationException("This method is not supported ");
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException("The queue is empty ");
            }
            Item item = current.item2;
            current = current.next;

            return item;

        }
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return (siz == 0);
    }

    // Adds new element to the first of the queue
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The element to be added cannot be null");
        }
        node newfirst = new node();

        if (first == null) {
            newfirst.item2 = item;
            newfirst.next = null;
            newfirst.previous = null;
            first = newfirst;
            last = newfirst;
        } else {
            newfirst.item2 = item;
            newfirst.next = first;
            first.previous = newfirst;
            first = newfirst;
        }
        siz++;
    }

    // Adds a new element to the last of the queue
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The element to be added cannot be null");
        }
        node newlast = new node();
        if (siz == 0) {
            newlast.item2 = item;
            newlast.next = null;
            newlast.previous = null;
            last = newlast;
            first = newlast;
        } else {
            last.next = newlast;
            newlast.previous = last;
            newlast.item2 = item;
            last = newlast;
            last.next = null;
        }
        siz++;
    }

    // Removes an item from the end
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty ");
        }
        siz--;
        node oldfirst = first;
        first = first.next;
        if (siz != 0) first.previous = null;
        if (first == null) {
            last = null;
        }
        return oldfirst.item2;
    }

    // Removes an item from the last
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty ");
        } else {
            siz--;
            node oldlast = last;
            last = last.previous;
            if (siz != 0) {
                last.next = null;
            }
            if (last == null) {
                first = null;

            }
            return oldlast.item2;
        }

    }

    // Returns the size of the linked list
    public int size() {
        return siz;
    }

    // Test client
    public static void main(String[] args) {
        Deque<Integer> newObject = new Deque<Integer>();

        int[] a = {1, 2, 3, 4, 5, 5, 6, 12, 45, 77, 3, 45, 657, 6788, 890, 45};
        for (int i = 0; i < 8; i++) {
            newObject.addLast(a[i]);
        }
        for (int i = 8; i < 16; i++) {
            newObject.addFirst(a[i]);
        }
        StdOut.println("Test character is " + newObject.removeFirst());
        StdOut.println("Test character is " + newObject.removeFirst());
        StdOut.println("The characters are (for size)" + newObject.size() + ":");
        for (int s : a) {
            if (!newObject.isEmpty()) StdOut.println(newObject.removeLast() + " ");
        }
        StdOut.println("The queue is empty " + newObject.isEmpty());
    }
}
