import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private node first, last;

    private class node {
        Item item;
        node next;
    }

    public Iterator<Item> iterator() {
        return new listIterator();
    }

    private class listIterator implements Iterator<Item> {
        private node current = first;

        public boolean hasNext() {
            return (current != null);
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;

        }
    }

    //Create an empty queue
    public Deque() {
        first = null;
        last = null;
    }

    //Check if the queue is empty
    public boolean isEmpty() {
        return ((first == null) || (last == null));
    }

    //Adds new element to the first of the queue
    public void addFirst(Item item) {
        node newfirst = first;
        newfirst.item = item;
        newfirst.next = first;
        first = newfirst;
        if (last == null) last = first;
    }

    //Adds a new element to the last of the queue
    public void addLast(Item item) {
        node newlast = last;
        last.next = newlast;
        newlast.item = item;
        newlast.next = null;
        if (first == null) first = last;
        last = newlast;
    }

    //Removes an item from the end
    public node removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty ");
        }
        node oldfirst = first;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        return oldfirst;
    }

    //Finds the second to last element in the queue
    private node findlast() {
        node current = first;
        node last2 = first.next;
        while (current.next.next != null) {
            current = current.next;
        }
        return current;
    }

    //Removes an item from the last
    public node removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty ");
        } else {
            node oldlast = last;
            last = findlast();
            last.next = null;
            if (first.next == null) first = null;
            return oldlast;
        }
    }

    //Returns the size of the linked list
    public int size() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty ");
        }
        int i = 0;
        node current = first;
        while (current != null) {
            current = current.next;
            i++;
        }
        return i;
    }

    //Test client
    public static void main(String[] args) {

    }
}
