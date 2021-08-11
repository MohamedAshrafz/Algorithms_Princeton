/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] data;
    private int N;

    public RandomizedQueue() {

    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return N;
    }


    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("the item cannot be nulled");

        if (data == null) {
            data = (Item[]) new Object[1];
            data[N++] = item;
        }
        else {
            if (N > 0 && N == data.length)
                resize(2 * data.length);

            data[N++] = item;
        }
    }

    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("the queue is empty");

        int randomIndex = StdRandom.uniform(0, N);
        while (data[randomIndex] == null)
            randomIndex = StdRandom.uniform(0, N);

        //save the data to be returned
        Item item = data[randomIndex];
        //replace the randomly chosen element with the last element in the array
        data[randomIndex] = data[N - 1];
        data[N - 1] = null;
        N--;

        //resizing if N is equal to 1/4 the size of the array
        if (N > 0 && N == (data.length / 4))
            resize(data.length / 2);

        return item;
    }


    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    private class RQIterator implements Iterator<Item> {
        private int i;

        private RQIterator() {
            StdRandom.shuffle(data, 0, N - 1);
        }

        public boolean hasNext() {
            return (i < N);
        }

        public Item next() {
            if (isEmpty())
                throw new java.util.NoSuchElementException("the queue is empty");

            Item item = data[i++];
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("unsupported operation");
        }
    }

    public void resize(int newCapacity) {

        Item[] copy = (Item[]) new Object[newCapacity];

        for (int i = 0; i < N; i++)
            copy[i] = data[i];
        data = null;
        data = copy;
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        rq.enqueue("ss");
        rq.enqueue("bb");
        rq.enqueue("mm");
        rq.enqueue("qq");
        rq.enqueue("ii");

        for (String s : rq)
            StdOut.println(s);

        StdOut.println();

        StdOut.println(rq.isEmpty());
        StdOut.println(rq.size());

        rq.dequeue();

        StdOut.println();

        for (String s : rq)
            StdOut.println(s);

        StdOut.println();
    }
}
