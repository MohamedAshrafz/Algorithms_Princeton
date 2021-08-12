/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

//support iterable interface (implements iterable method)
public class Deque<Item> implements Iterable<Item> {

    private class Node<Item> {
        private Item data;
        private Node<Item> next;
        private Node<Item> prev;

    }

    private Node<Item> head;
    private Node<Item> tail;
    private int size;

    public Deque() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item data) {
        if (data == null)
            throw new IllegalArgumentException("the data cannot be nulled");

        Node<Item> node = new Node<Item>();
        node.data = data;

        if (isEmpty()) {
            head = node;
            tail = head;
        }
        else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    public void addLast(Item data) {
        if (data == null)
            throw new IllegalArgumentException("the data cannot be nulled");

        Node<Item> node = new Node<Item>();
        node.data = data;

        if (isEmpty()) {
            head = node;
            tail = head;
        }
        else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("the Deque is empty");

        Item item;
        if (size() == 1) {
            item = head.data;
            head = tail = null;
        }
        else {
            item = head.data;
            head = head.next;
            head.prev = null;
        }
        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("the Deque is empty");

        Item item;
        if (size() == 1) {
            item = head.data;
            head = tail = null;
        }
        else {
            item = tail.data;
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return item;
    }

    //iterator method returns iterator item (implements iterable interface)
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = head;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("the Deque is empty");

            Item item = current.data;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("unsupporetted operation");
        }
    }

    public static void main(String[] args) {

        Deque<Integer> dq = new Deque<Integer>();

        dq.addLast(5);
        dq.removeFirst();

        for (Integer i : dq) {
            StdOut.println(i);
        }
        //dq.addLast(10);
        //dq.addLast(30);

        dq.addFirst(70);
        dq.removeLast();
        for (Integer i : dq) {
            StdOut.println(i);
        }

        // dq.addFirst(60);
        // dq.addFirst(50);
        //
        // for (Integer i : dq) {
        //     StdOut.println(i);
        // }
        // StdOut.println();
        //
        // dq.removeFirst();
        // for (Integer i : dq) {
        //     StdOut.println(i);
        // }
        // StdOut.println();
        //
        // dq.removeLast();
        // for (Integer i : dq) {
        //     StdOut.println(i);
        // }
        // StdOut.println();
        //
        // StdOut.println(dq.size);
        //
        // StdOut.println(dq.isEmpty());

    }
}

