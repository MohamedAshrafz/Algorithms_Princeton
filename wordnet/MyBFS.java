/* *****************************************************************************
 *  Name: mohamed ashraf farouk
 *  Date: 24/9/2021
 *  Description: my own custom implementation of breadth first search algorithm
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;

public class MyBFS {

    private final Digraph G;
    // marked array for each vertex
    private boolean[] markedS;
    private boolean[] markedW;
    // distance to array for each vertex
    private int[] disToS;
    private int[] disToW;

    // edgeTo array for each vertex
    // private int[] edgeToS;
    // private int[] edgeToW;

    // ancestor and shortest distance
    private int ancestor;
    private int shortestDis;
    // path queue
    // private LinkedList<Integer> path;

    public MyBFS(Digraph G) {
        this.G = G;
        markedS = new boolean[G.V()];
        markedW = new boolean[G.V()];
        // edgeToS = new int[G.V()];
        // edgeToW = new int[G.V()];
        disToS = new int[G.V()];
        disToW = new int[G.V()];

        for (int i = 0; i < G.V(); i++) {
            disToS[i] = Integer.MAX_VALUE;
            disToW[i] = Integer.MAX_VALUE;
        }
    }


    public void bfs(int s, int w) {
        validateVertex(s);
        validateVertex(w);

        // if the two argument is the same vertex
        if (s == w) {
            ancestor = s;
            shortestDis = 0;
            return;
        }

        Queue<Integer> quS = new Queue<Integer>();
        Queue<Integer> quW = new Queue<Integer>();

        ArrayList<Integer> resetS = new ArrayList<Integer>();
        ArrayList<Integer> resetW = new ArrayList<Integer>();

        ancestor = -1;
        shortestDis = -1;

        markedS[s] = true;
        markedW[w] = true;
        disToS[s] = 0;
        disToW[w] = 0;
        quS.enqueue(s);
        quW.enqueue(w);

        resetS.add(s);
        resetW.add(w);
        // no more adding to queue (in k and k+1 required)
        boolean noQueueingS = false;
        boolean noQueueingW = false;
        // add vertices with distances k and k+1 to array
        ArrayList<Integer> arrS = new ArrayList<Integer>();
        ArrayList<Integer> arrW = new ArrayList<Integer>();

        int shortestSoFar = Integer.MAX_VALUE;

        while (!quS.isEmpty() || !quW.isEmpty()) {
            // S vertex search
            if (!quS.isEmpty()) {
                int vertexS = quS.dequeue();
                for (int ad : G.adj(vertexS)) {
                    if (!markedS[ad]) {
                        markedS[ad] = true;
                        disToS[ad] = disToS[vertexS] + 1;
                        resetS.add(ad);

                        if (!noQueueingS)
                            quS.enqueue(ad);
                        else if (disToS[ad] < shortestSoFar)
                            quS.enqueue(ad);

                        // edgeToS[ad] = vertexS;

                        if (markedW[ad])
                            noQueueingS = true;

                        if (markedW[ad] && disToS[ad] + disToW[ad] < shortestSoFar) {
                            shortestSoFar = disToS[ad] + disToW[ad];
                            ancestor = ad;
                        }
                    }
                }
            }

            // W vertex search
            if (!quW.isEmpty()) {
                int vertexW = quW.dequeue();
                for (int ad : G.adj(vertexW)) {
                    if (!markedW[ad]) {
                        markedW[ad] = true;
                        disToW[ad] = disToW[vertexW] + 1;
                        resetW.add(ad);

                        if (!noQueueingW)
                            quW.enqueue(ad);
                        else if (disToW[ad] < shortestSoFar)
                            quW.enqueue(ad);
                        // edgeToW[ad] = vertexW;

                        if (markedS[ad])
                            noQueueingW = true;

                        if (markedS[ad] && disToS[ad] + disToW[ad] < shortestSoFar) {
                            shortestSoFar = disToS[ad] + disToW[ad];
                            ancestor = ad;
                        }
                    }
                }
            }
        }
        if (shortestSoFar != Integer.MAX_VALUE)
            shortestDis = shortestSoFar;

        reset(resetS, resetW);
    }

    // bfs for subset
    public void bfs(Iterable<Integer> s, Iterable<Integer> w) {
        validateIteratorVertex(s);
        validateIteratorVertex(w);

        // if any vertex in s is the same vertex in w
        for (int i : s) {
            for (int j : w)
                if (i == j) {
                    ancestor = i;
                    shortestDis = 0;
                    return;
                }
        }

        Queue<Integer> quS = new Queue<Integer>();
        Queue<Integer> quW = new Queue<Integer>();

        ArrayList<Integer> resetS = new ArrayList<Integer>();
        ArrayList<Integer> resetW = new ArrayList<Integer>();

        // reset the ancestor
        ancestor = -1;
        shortestDis = -1;

        for (int i : s) {
            quS.enqueue(i);
            markedS[i] = true;
            disToS[i] = 0;
            resetS.add(i);
        }
        for (int i : w) {
            quW.enqueue(i);
            markedW[i] = true;
            disToW[i] = 0;
            resetW.add(i);
        }

        // no more adding to queue (in k and k+1 required)
        boolean noQueueingS = false;
        boolean noQueueingW = false;
        // add vertices with distances k and k+1 to array
        ArrayList<Integer> arrS = new ArrayList<Integer>();
        ArrayList<Integer> arrW = new ArrayList<Integer>();

        int shortestSoFar = Integer.MAX_VALUE;

        while (!quS.isEmpty() || !quW.isEmpty()) {

            // S vertex search
            if (!quS.isEmpty()) {
                int vertexS = quS.dequeue();
                for (int ad : G.adj(vertexS)) {
                    if (!markedS[ad]) {
                        markedS[ad] = true;
                        disToS[ad] = disToS[vertexS] + 1;
                        resetS.add(ad);

                        if (!noQueueingS)
                            quS.enqueue(ad);
                        else if (disToS[ad] < shortestSoFar)
                            quS.enqueue(ad);

                        // edgeToS[ad] = vertexS;

                        if (markedW[ad])
                            noQueueingS = true;

                        if (markedW[ad] && disToS[ad] + disToW[ad] < shortestSoFar) {
                            shortestSoFar = disToS[ad] + disToW[ad];
                            ancestor = ad;
                        }
                    }
                }
            }

            // W vertex search
            if (!quW.isEmpty()) {
                int vertexW = quW.dequeue();
                for (int ad : G.adj(vertexW)) {
                    if (!markedW[ad]) {
                        markedW[ad] = true;
                        disToW[ad] = disToW[vertexW] + 1;
                        resetW.add(ad);

                        if (!noQueueingW)
                            quW.enqueue(ad);
                        else if (disToW[ad] < shortestSoFar)
                            quW.enqueue(ad);
                        // edgeToW[ad] = vertexW;

                        if (markedS[ad])
                            noQueueingW = true;

                        if (markedS[ad] && disToS[ad] + disToW[ad] < shortestSoFar) {
                            shortestSoFar = disToS[ad] + disToW[ad];
                            ancestor = ad;
                        }
                    }
                }
            }
        }
        if (shortestSoFar != Integer.MAX_VALUE)
            shortestDis = shortestSoFar;

        reset(resetS, resetW);
    }

    // return shortest distance method
    public int getShortestDis() {
        return shortestDis;
    }

    // return common ancestor method
    public int getAncestor() {
        return ancestor;
    }

    // resetting the arrays
    private void reset(ArrayList<Integer> resetS, ArrayList<Integer> resetw) {
        for (int i : resetS) {
            markedS[i] = false;
            disToS[i] = Integer.MAX_VALUE;
        }
        for (int i : resetw) {
            markedW[i] = false;
            disToW[i] = Integer.MAX_VALUE;
        }
    }

    private void validateVertex(Integer v) {
        if (v == null)
            throw new IllegalArgumentException("vertex is null");
        if (v < 0 || v >= G.V())
            throw new IllegalArgumentException("out of range vertex");
    }

    private void validateIteratorVertex(Iterable<Integer> iter) {
        if (iter == null)
            throw new IllegalArgumentException("iterator is null");

        for (Integer v : iter) {
            if (v == null)
                throw new IllegalArgumentException("vertex is null");

            if (v < 0 || v >= G.V())
                throw new IllegalArgumentException("out of range vertex");
        }
    }

    public static void main(String[] args) {

    }
}
