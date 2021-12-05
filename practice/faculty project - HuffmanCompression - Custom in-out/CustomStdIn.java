import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

public class CustomStdIn {
    private static final int EOF = -1;

    private static BufferedInputStream in;      // buffered input stream to avoid multiple calling for sys in
    private static int buffer;                  // one char buffer
    private static int n;                       // number of bits remained in the buffer
    private static boolean isInitialized;       // StdIn already called

    private CustomStdIn() {
    }

    private static void initialize() {
        in = new BufferedInputStream(System.in);
        buffer = 0;
        n = 0;
        fillBuffer();
        isInitialized = true;
    }

    private static void fillBuffer() {
        try {
            buffer = in.read();
            n = 8;
        } catch (IOException e) {
            System.out.println("end of file");
            buffer = EOF;
            n = -1;
        }
    }

    public static void close() {
        if (!isInitialized) initialize();
        try {
            in.close();
            isInitialized = false;
        } catch (IOException ioe) {
            throw new IllegalStateException("could not close the input stream", ioe);
        }
    }

    private static boolean isEmpty() {
        if (!isInitialized) initialize();
        return buffer == EOF;
    }

    public static boolean readBoolean() {
        if (isEmpty()) throw new NoSuchElementException("The input stream is empty");
        n--;
        boolean bit = ((buffer >> n) & 1) == 1;
        if (n == 0) fillBuffer();
        return bit;
    }

    public static char readChar() {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");
        if (n == 8) {
            int x = buffer;
            fillBuffer();
            return (char) (x & 0xff);
        }

        int x = (buffer <<= (8 - n));
        int preN = n;
        fillBuffer();
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");
        n = preN;
        x |= (buffer >>> n);
        return (char) (x & 0xff);
    }

    public static String readString() {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");

        StringBuilder strBuilder = new StringBuilder();
        while (!isEmpty()) {
            char c = readChar();
            strBuilder.append(c);
        }
        return strBuilder.toString();
    }

    public static int readInt() {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");

        int x = 0;
        for (int i = 0; i < 4; i++) {
            x <<= 8;
            x |= readChar();
        }
        return x;
    }


}
