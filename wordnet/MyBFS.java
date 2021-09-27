/* *****************************************************************************
 *  Name: mohamed ashraf farouk
 *  Date: 24/9/2021
 *  Description: my own custom implementation of breadth first search algorithm
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;

public class MyBFS {

    private Digraph G;
    // marked array for each vertex
    private boolean markedS[];
    private boolean markedW[];
    // distance to array for each vertex
    private int disToS[];
    private int disToW[];
    // edgeTo array for each vertex
    // private int edgeToS[];
    // private int edgeToW[];
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
        ancestor = -1;
        shortestDis = Integer.MAX_VALUE;

        for (int v = 0; v < G.V(); v++) {
            disToS[v] = Integer.MAX_VALUE;
            disToW[v] = Integer.MAX_VALUE;
        }
    }


    public void bfs(int s, int w) {
        validateVertex(s);
        validateVertex(w);

        Queue<Integer> quS = new Queue<Integer>();
        Queue<Integer> quW = new Queue<Integer>();

        ArrayList<Integer> resetS = new ArrayList<Integer>();
        ArrayList<Integer> resetW = new ArrayList<Integer>();

        ancestor = -1;
        markedS[s] = true;
        markedW[w] = true;
        disToS[s] = 0;
        disToW[w] = 0;
        quS.enqueue(s);
        quW.enqueue(w);
        resetS.add(s);
        resetW.add(w);


        while (!quS.isEmpty() || !quW.isEmpty()) {
            // S vertex search
            if (!quS.isEmpty()) {
                int vertexS = quS.dequeue();
                for (int ad : G.adj(vertexS)) {
                    markedS[ad] = true;
                    quS.enqueue(ad);
                    // edgeToS[ad] = vertexS;
                    disToS[ad] = disToS[vertexS] + 1;
                    resetS.add(ad);

                    if (markedW[ad]) {
                        ancestor = ad;
                        break;
                    }
                }
            }
            // if you find the ancestor exit the loop
            if (ancestor != -1)
                break;

            // W vertex search
            if (!quW.isEmpty()) {
                int vertexW = quW.dequeue();
                for (int ad : G.adj(vertexW)) {
                    markedW[ad] = true;
                    quW.enqueue(ad);
                    // edgeToW[ad] = vertexW;
                    disToW[ad] = disToW[vertexW] + 1;
                    resetW.add(ad);

                    if (markedS[ad]) {
                        ancestor = ad;
                        break;
                    }
                }
            }
            // if you find the ancestor exit the loop
            if (ancestor != -1)
                break;
        }
        if (ancestor != -1)
            shortestDis = disToS[ancestor] + disToW[ancestor];
        else {
            shortestDis = -1;
        }

        reset(resetS, resetW);
    }

    // bfs for subset
    public void bfs(Iterable<Integer> s, Iterable<Integer> w) {
        validateIteratorVertex(s);
        validateIteratorVertex(w);

        Queue<Integer> quS = new Queue<Integer>();
        Queue<Integer> quW = new Queue<Integer>();

        ArrayList<Integer> resetS = new ArrayList<Integer>();
        ArrayList<Integer> resetW = new ArrayList<Integer>();

        // reset the ancestor
        ancestor = -1;
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


        while (!quS.isEmpty() || !quW.isEmpty()) {

            // S vertex search
            if (!quS.isEmpty()) {
                int vertexS = quS.dequeue();
                for (int ad : G.adj(vertexS)) {
                    markedS[ad] = true;
                    quS.enqueue(ad);
                    // edgeToS[ad] = vertexS;
                    disToS[ad] = disToS[vertexS] + 1;
                    resetS.add(ad);

                    if (markedW[ad]) {
                        ancestor = ad;
                        break;
                    }
                }
            }
            if (ancestor != -1)
                break;

            // W vertex search
            if (!quW.isEmpty()) {
                int vertexW = quW.dequeue();
                for (int ad : G.adj(vertexW)) {
                    markedW[ad] = true;
                    quW.enqueue(ad);
                    // edgeToW[ad] = vertexW;
                    disToW[ad] = disToW[vertexW] + 1;
                    resetW.add(ad);

                    if (markedS[ad]) {
                        ancestor = ad;
                        break;
                    }
                }
            }
            if (ancestor != -1)
                break;
        }
        if (ancestor != -1)
            shortestDis = disToS[ancestor] + disToW[ancestor];
        else
            shortestDis = -1;

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

    private void validateIteratorVertex(Iterable<Integer> iter){
        if (iter == null)
            throw new IllegalArgumentException("iterator is null");

        for (Integer v : iter) {
            if (v == null)
                throw new IllegalArgumentException("vertex is null");

            if (v < 0 || v > G.V())
                throw new IllegalArgumentException("out of range vertex");
        }
    }

    public static void main(String[] args) {

    }
}
