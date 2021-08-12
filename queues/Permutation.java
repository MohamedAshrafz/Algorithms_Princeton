/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        String[] s = new String[1];
        Deque<String> dq = new Deque<String>();

        int n = 0;
        //s[n++] = StdIn.readString();
        //boolean ie = StdIn.isEmpty();
        while (!StdIn.isEmpty()) {
            //resizing the array if the no elements == the array.length
            //and copying the elements to the newly created array
            if (n == s.length) {
                String[] copy = new String[(s.length) * 2];

                for (int i = 0; i < n; i++)
                    copy[i] = s[i];

                s = copy;
            }
            s[n++] = StdIn.readString();
        }

        if (n > 0) {
            StdRandom.shuffle(s, 0, n - 1);

            for (int i = 0; i < k; i++) {
                dq.addLast(s[i]);
            }

            for (String ss : dq)
                StdOut.println(ss);
        }
        else {
            System.out.println("the StdIn is empty");
        }
    }
}
