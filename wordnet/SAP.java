/* *****************************************************************************
 *  Name: mohamed ashraf farouk
 *  Date: 24/9/2021
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final MyBFS myBFS;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("digraph can not be nulled");

        //copy the digraph (defensive copy)
        Digraph GCopy = new Digraph(G);

        // initializing instance of myBFS class
        // one is enough for every graph
        myBFS = new MyBFS(GCopy);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        myBFS.bfs(v, w);
        return myBFS.getShortestDis();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        myBFS.bfs(v, w);
        return myBFS.getAncestor();
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
