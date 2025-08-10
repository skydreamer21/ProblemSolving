import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static final int TOTAL_ALPHA = 26;

    static int N;
    static Word target;
    static Word compare;
    static Word[] words;
    static int[] prices;
    static int min = Integer.MAX_VALUE;

    static class Word {
        int[] count;

        public Word(String wordStr) {
            count = new int[TOTAL_ALPHA];
            char[] word = wordStr.toCharArray();
            for (char c : word) {
                count[c - 'A']++;
            }
        }

        public void addWord(Word word) {
            for (int i=0; i<TOTAL_ALPHA; i++) {
                this.count[i] += word.count[i];
            }
        }

        public void subWord(Word word) {
            for (int i=0; i<TOTAL_ALPHA; i++) {
                this.count[i] -= word.count[i];
                if (this.count[i] < 0) {
                    throw new IllegalArgumentException("불가능한 경우에 도달");
                }
            }
        }

        public boolean isPossible(Word word) {
            for (int i=0; i<TOTAL_ALPHA; i++) {
                if (word.count[i] < this.count[i]) return false;
            }
            return true;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        target = new Word(br.readLine());
        N = Integer.parseInt(br.readLine());
        words = new Word[N];
        prices = new int[N];
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            prices[i] = Integer.parseInt(st.nextToken());
            words[i] = new Word(st.nextToken());
        }

        compare = new Word("");
        dfs(0, 0);

        if (min == Integer.MAX_VALUE) {
            sb.append(-1);
        } else {
            sb.append(min);
        }


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static void dfs(int depth, int price) {
        if (depth == N) {
            return;
        }

        // 1. 이번 단어를 넣을 때
        compare.addWord(words[depth]);
        if (target.isPossible(compare)) {
            min = Math.min(min, price + prices[depth]);
        } else {
            dfs(depth + 1, price + prices[depth]);
        }

        // 2. 이번 단어를 넣지 않을 때
        compare.subWord(words[depth]);
        // 만약 여기서 가능했다면 애초에 이 곳을 들어오지 않았음.
        dfs(depth + 1, price);
    }

}
