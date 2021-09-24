/* *****************************************************************************
 *  Name: mohamed ashraf farouk
 *  Date: 24/9/2021
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

import java.util.ArrayList;

public class WordNet {
    // ST connects every string with its occurrences (ids)
    private final ST<String, ArrayList<Integer>> stringsToIndexST;
    // Array of strings for every id
    private final ArrayList<String[]> indexToStringArr;
    // Mapping digraph of nouns
    private Digraph digraph;
    // num of vertices
    private final int V;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();
        // initialization of DSs
        indexToStringArr = new ArrayList<>();
        stringsToIndexST = new ST<String, ArrayList<Integer>>();

        synsetsConfig(synsets);

        // initialization of digraph
        V = indexToStringArr.size();
        digraph = new Digraph(V);

        // configuration of the edges
        hypernymsConfig(hypernyms);
    }

    // first make the synset array, ST
    private void synsetsConfig(String synsets) {

        In inSynsets = new In(synsets);

        while (inSynsets.hasNextLine()) {
            String[] line = inSynsets.readLine().split(",");
            int id = Integer.parseInt(line[0]);

            String[] nouns = line[1].split(" ");
            indexToStringArr.add(id, nouns);
        }

        for (int i = 0; i < indexToStringArr.size(); i++) {
            for (int j = 0; j < indexToStringArr.get(i).length; j++) {
                String noun = (indexToStringArr.get(i))[j];

                ArrayList<Integer> intArr = stringsToIndexST.get(noun);
                if (intArr != null) {
                    intArr.add(i);
                }
                else {
                    intArr = new ArrayList<Integer>();
                    intArr.add(i);
                    stringsToIndexST.put(noun, intArr);
                }
            }
        }
    }

    // second make graph edges
    private void hypernymsConfig(String hypernyms) {
        In inHypernyms = new In(hypernyms);
        while (inHypernyms.hasNextLine()) {
            String[] line = inHypernyms.readLine().split(",");
            int id = Integer.parseInt(line[0]);

            for (int i = 1; i < line.length; i++) {
                digraph.addEdge(id, i);
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return stringsToIndexST.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        return stringsToIndexST.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();
        return "hello, world";
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");

        // for (String[] stringArr : wn.stringsArrays) {
        //     for (String str : stringArr)
        //         StdOut.print(str + "\t");
        //     StdOut.println();
        // }

        // ST<String, ArrayList<Integer>> st = wn.stringsST;

        // for (String str : st.keys()){
        //     if (str.equals("bird")) {
        //         StdOut.print(str + "\t");
        //         ArrayList<Integer> arr = st.get((String) str);
        //         for (int i : arr) {
        //             StdOut.print(i + "  ");
        //         }
        //         StdOut.println();
        //     }
        // }

        // int i = 0;
        // for (String str : wn.nouns()) {
        //     if (str.equals("Word")) {
        //         StdOut.println(i + "\t" + str);
        //     }
        //     i++;
        // }
        // StdOut.println(wn.stringsToIndexST.size());


        // ArrayList<Integer> arr = wn.stringsToIndexST.get("bird");
        //
        // for (int i : arr){
        //     StdOut.print(i + "\t");
        //     String[] strArr = wn.indexToStringArr.get(i);
        //
        //     for (String str : strArr)
        //         StdOut.print(str + "  ");
        //     StdOut.println();
        // }

        // StdOut.println(wn.V);
        // int i = 0;
        // for (String str : wn.nouns()) {
        //     StdOut.println(i++ + "  " + str);
        // }

        // String[] strArr = wn.indexToStringArr.get(82191);
        //
        // for (String str : strArr)
        //     StdOut.print(str + "  ");
        // StdOut.println();
    }
}
