/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }


    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {

        int[] distances = new int[nouns.length];

        int sum;
        for (int i = 0; i < nouns.length; i++) {
            sum = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i != j)
                    sum += wordnet.distance(nouns[i], nouns[j]);
            }
            distances[i] = sum;
        }

        int farthestIndex = 0;
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < nouns.length; i++) {
            if (distances[i] > maxSum) {
                maxSum = distances[i];
                farthestIndex = i;
            }
        }

        return nouns[farthestIndex];
    }


    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}

