/* *****************************************************************************
 *  Name: mohamed ashraf farouk
 *  Date: 24/9/2021
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final MyBFS myBFS;
    private final ST<CacheNode, Integer> lenCacheST;
    private final ST<CacheNode, Integer> ancCacheST;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("digraph can not be nulled");

        //copy the digraph (defensive copy)
        Digraph GCopy = new Digraph(G);

        // initializing instance of myBFS class
        // one is enough for every graph
        myBFS = new MyBFS(GCopy);

        //caching the data of queries
        lenCacheST = new ST<CacheNode, Integer>();
        ancCacheST = new ST<CacheNode, Integer>();
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        CacheNode node = new CacheNode(v, w);

        Integer len = lenCacheST.get(new CacheNode(v, w));
        if (len != null)
            return len;
        else {
            myBFS.bfs(v, w);
            int newLen = myBFS.getShortestDis();
            lenCacheST.put(node, newLen);
            ancCacheST.put(node, myBFS.getAncestor());
            return newLen;
        }
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        CacheNode node = new CacheNode(v, w);

        Integer anc = ancCacheST.get(new CacheNode(v, w));
        if (anc != null)
            return anc;
        else {
            myBFS.bfs(v, w);
            int newAnc = myBFS.getAncestor();
            ancCacheST.put(node, newAnc);
            lenCacheST.put(node, myBFS.getShortestDis());
            return newAnc;
        }
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        myBFS.bfs(v, w);
        return myBFS.getShortestDis();
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        myBFS.bfs(v, w);
        return myBFS.getAncestor();
    }

    private class CacheNode implements Comparable<CacheNode> {
        private int v;
        private int w;

        private CacheNode(int v, int w) {
            this.v = v;
            this.w = w;
        }

        public int compareTo(CacheNode cacheNode) {

            // if this.v == that.v compare with w
            if (this.v == cacheNode.v)
                return Integer.compare(this.w, cacheNode.w);
            else
                // if this.v != that.v compare with v
                return Integer.compare(this.v, cacheNode.v);
        }
    }

    public static void main(String[] args) {

        // In in = new In(args[0]);
        // Digraph G = new Digraph(in);
        // SAP sap = new SAP(G);
        //
        // while (!StdIn.isEmpty()) {
        //     Stack<Integer> v = new Stack<Integer>();
        //     Stack<Integer> w = new Stack<Integer>();
        //     v.push(StdIn.readInt());
        //     v.push(StdIn.readInt());
        //     w.push(StdIn.readInt());
        //     w.push(StdIn.readInt());
        //     int length = sap.length(v, w);
        //     int ancestor = sap.ancestor(v, w);
        //     StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        // }


        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
