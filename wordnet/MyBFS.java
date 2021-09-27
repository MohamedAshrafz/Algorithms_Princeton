/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;

class MyBFS {
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

    MyBFS(Digraph G) {
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

    void bfs(int s, int w) {

        Queue<Integer> quS = new Queue<Integer>();
        Queue<Integer> quW = new Queue<Integer>();

        ArrayList<Integer> resetS = new ArrayList<Integer>();
        ArrayList<Integer> resetW = new ArrayList<Integer>();

        markedS[s] = true;
        markedW[w] = true;
        disToS[s] = 0;
        disToW[w] = 0;
        quS.enqueue(s);
        quW.enqueue(w);
        resetS.add(s);
        resetW.add(w);

        boolean breakCondition = false;

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
                        breakCondition = true;
                        break;
                    }
                }
            }
            if (breakCondition)
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
                        breakCondition = true;
                        break;
                    }
                }
            }
            if (breakCondition)
                break;
        }
        shortestDis = disToS[ancestor] + disToW[ancestor];

        reset(resetS, resetW);
    }

    // bfs for subset
    void bfs(Iterable<Integer> s, Iterable<Integer> w) {
        Queue<Integer> quS = new Queue<Integer>();
        Queue<Integer> quW = new Queue<Integer>();

        ArrayList<Integer> resetS = new ArrayList<Integer>();
        ArrayList<Integer> resetW = new ArrayList<Integer>();

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

        boolean breakCondition = false;

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
                        breakCondition = true;
                        break;
                    }
                }
            }
            if (breakCondition)
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
                        breakCondition = true;
                        break;
                    }
                }
            }
            if (breakCondition)
                break;
        }
        shortestDis = disToS[ancestor] + disToW[ancestor];

        reset(resetS, resetW);
    }

    // return shortest distance method
    int getShortestDis() {
        return shortestDis;
    }

    // return common ancestor method
    int getAncestor() {
        return ancestor;
    }

    // resetting the arrays
    void reset(ArrayList<Integer> resetS, ArrayList<Integer> resetw) {
        for (int i : resetS) {
            markedS[i] = false;
            disToS[i] = Integer.MAX_VALUE;
        }
        for (int i : resetw) {
            markedW[i] = false;
            disToW[i] = Integer.MAX_VALUE;
        }
    }

    public static void main(String[] args) {

    }
}
