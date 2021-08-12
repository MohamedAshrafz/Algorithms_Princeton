/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] data;
    private int noElem;

    public RandomizedQueue() {

    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return noElem;
    }


    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("the item cannot be nulled");

        if (data == null) {
            data = (Item[]) new Object[1];
            data[noElem++] = item;
        }
        else {
            if (noElem > 0 && noElem == data.length)
                resize(2 * data.length);

            data[noElem++] = item;
        }
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("the queue is empty");

        int randomIndex = StdRandom.uniform(0, noElem);

        //save the data to be returned
        Item item = data[randomIndex];
        //replace the randomly chosen element with the last element in the array
        data[randomIndex] = data[noElem - 1];
        data[noElem - 1] = null;
        noElem--;

        //resizing if N is equal to 1/4 the size of the array
        if (noElem > 0 && noElem == (data.length / 4))
            resize(data.length / 2);

        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("the queue is empty");

        int randomIndex = StdRandom.uniform(0, noElem);
        return data[randomIndex];
    }

    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    private class RQIterator implements Iterator<Item> {
        int i = 0;
        Item[] copy;

        private RQIterator() {
            if (!isEmpty()) {
                copy = (Item[]) new Object[size()];

                for (int i = 0; i < noElem; i++)
                    copy[i] = data[i];

                StdRandom.shuffle(copy);
            }
        }

        public boolean hasNext() {
            return (i < size() && !isEmpty());
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("there is no more elements to be returned");

            Item item = copy[i++];
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("unsupported operation");
        }
    }


    private void resize(int newCapacity) {

        Item[] copy = (Item[]) new Object[newCapacity];

        for (int i = 0; i < noElem; i++)
            copy[i] = data[i];

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

        rq.dequeue();

        for (String s : rq)
            StdOut.println(s);

        StdOut.println();

        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());

        StdOut.println();

        for (String s : rq)
            for (String ss : rq)
                StdOut.println(ss);

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
