import java.io.BufferedOutputStream;
import java.io.IOException;

public class CustomStdOut {
    private static BufferedOutputStream out;    // output stream
    private static int buffer;                  // 8-bit buffer
    private static int n;                       // number of bits remaining in buffer
    private static boolean isInitialized;       // the class is already initialized

    private CustomStdOut() {
    }

    private static void initialize() {
        out = new BufferedOutputStream(System.out);
        buffer = 0;
        n = 0;
        isInitialized = true;
    }

    private static void writeBoolean(boolean x) {
        if (!isInitialized) initialize();

        buffer <<= 1;
        if (x) buffer |= 1;

        n++;
        if (n == 8) clearBuffer();
    }

    private static void writeByte(int x) {
        if (!isInitialized) initialize();

        assert x >= 0 && x < 256;

        if (n == 0) {
            try {
                out.write(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        for (int i = 0; i < 8; i++) {
            boolean b = (x >>> (8 - 1 - i) & 1) == 1;
            writeBoolean(b);
        }
    }

    private static void clearBuffer() {
        if (!isInitialized) initialize();

        if (n == 0) return;
        if (n > 0)
            buffer <<= (8 - n);
        try {
            out.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = 0;
        n = 0;
    }

    private static void flush() {
        clearBuffer();
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        flush();
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isInitialized = false;
    }

    public static void write(boolean x) {
        writeBoolean(x);
    }

    public static void write(byte x) {
        writeByte(x & 0xff);
    }

    public static void write(int x) {
        writeByte((x >>> 24) & 0xff);
        writeByte((x >>> 16) & 0xff);
        writeByte((x >>> 8) & 0xff);
        writeByte((x >>> 0) & 0xff);
    }

    public static void write(char c) {
        writeByte((int) c);
    }

    public static void write(String s) {
        for (int i = 0; i < s.length(); i++)
            write(s.charAt(i));
    }

}
