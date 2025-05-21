import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static class Processor {
        final int TOTAL = 54;
        final int HALF = 26;

        char[] str;
        int[] charMap;

        public Processor(String str) {
            this.str = str.toCharArray();
            charMap = new int[TOTAL];
            for (int i = 0; i<TOTAL; i++) {
                charMap[i] = i;
            }
        }

        public void change(char fromC, char toC) {
            if (fromC == toC) return;
            int from = charToInt(fromC);
            int to = charToInt(toC);

            for (int i = 0; i<TOTAL; i++) {
                if (charMap[i] == from) {
                    charMap[i] = to;
                }
            }

        }

        public void show(StringBuilder sb) {
            for (char c : str) {
                sb.append(convert(c));
            }
            sb.append("\n");
        }

        private char convert(char fromC) {
            int from = charToInt(fromC);
            return intToChar(charMap[from]);
        }

        private int charToInt(char c) {
            if(Character.isUpperCase(c)) {
                return c - 'A' + (HALF);
            }
            return c - 'a';
        }

        private char intToChar(int n) {
            if (n >= HALF) {
                return (char) ('A' + (n - (HALF)));
            }
            return (char) ('a' + n);
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        Processor processor = new Processor(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        for (int i = 0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            if (command == 1) {
                char from = st.nextToken().charAt(0);
                char to = st.nextToken().charAt(0);
                processor.change(from, to);
            } else {
                processor.show(sb);
            }
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}
