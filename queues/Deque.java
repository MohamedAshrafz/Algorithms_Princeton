/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;

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
        Node<Item> node = new Node<Item>();
        node.data = data;

        if (data == null)
            throw new IllegalArgumentException("the data cannot be nulled");

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
        Node<Item> node = new Node<Item>();
        node.data = data;

        if (data == null)
            throw new IllegalArgumentException("the data cannot be nulled");

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

    public void removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("the Deque is empty");

        if (size() == 1) {
            head = tail = null;
        }
        else {
            head = head.next;
            head.prev = null;
        }
        size--;
    }

    public void removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("the Deque is empty");

        if (size() == 1) {
            head = tail = null;
        }
        else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    class DequeIterator implements Iterator<Item> {
        public boolean hasNext() {
            return false;
        }

        public Item next() {
            return null;
        }
    }

    public void show (){
        Node node = head;
        while (node != null){
            System.out.println(node.data);
            node = node.next;
        }
    }

    public static void main(String[] args) {
        Deque dq = new Deque<Integer>();

        System.out.println(dq.size());

        dq.addLast(5);
        dq.addLast(10);
        dq.addLast(30);

        dq.addFirst(5);
        dq.addFirst(10);
        dq.addFirst(30);

        dq.show();
        dq.removeFirst();
        //dq.removeLast();
        System.out.println("\n");

        dq.show();
    }

}

