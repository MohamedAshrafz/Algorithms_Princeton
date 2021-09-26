/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
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
    private int edgeToS[];
    private int edgeToW[];

    MyBFS(Digraph G) {
        this.G = G;
        markedS = new boolean[G.V()];
        markedW = new boolean[G.V()];
    }

    void bfs(int s, int w) {

        Queue<Integer> quS = new Queue<Integer>();
        Queue<Integer> quW = new Queue<Integer>();

        ArrayList<Integer> resetS = new ArrayList<Integer>();
        ArrayList<Integer> resetW = new ArrayList<Integer>();

        markedS[s] = true;
        markedW[w] = true;
        quS.enqueue(s);
        quS.enqueue(w);
        resetS.add(s);
        resetW.add(w);

        int ancestor = -1;
        boolean breakCondition = false;

        while (!quS.isEmpty() || !quW.isEmpty()) {

            // S vertex search
            if (!quS.isEmpty()) {
                int vertexS = quS.dequeue();
                for (int ad : G.adj(vertexS)) {
                    markedS[ad] = true;
                    quS.enqueue(ad);
                    edgeToS[ad] = vertexS;
                    disToS[ad] = disToS[vertexS] + 1;
                    resetS.add(ad);

                    if (markedW[ad]) {
                        ancestor = ad;
                        breakCondition = true;
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
                    edgeToW[ad] = vertexW;
                    disToW[ad] = disToW[vertexW] + 1;
                    resetW.add(ad);

                    if (markedS[ad]) {
                        ancestor = ad;
                        breakCondition = true;
                    }
                }
            }
            if (breakCondition)
                break;

        }
    }

    public static void main(String[] args) {

    }
}
