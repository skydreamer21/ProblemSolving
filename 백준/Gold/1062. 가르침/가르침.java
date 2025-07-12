import java.io.*;
import java.util.StringTokenizer;
import java.util.HashSet;
import java.util.Arrays;

public class Main {
    static int N, K;
    static int status = 0;
    static int[] words;
    static int maxReadableWord = 0;
    static HashSet<Integer> learnedIdx = new HashSet<>(Arrays.asList(0, 2, 8, 13 ,19));

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        // ******************** 입력 & 초기화 ********************
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        if ( K < 5 ) {
            System.out.println("0");
            return;
        }
        words = new int[N];
        for (int i=0; i<N; i++) words[i] = wordToStatus(br.readLine());

        for (int idx : learnedIdx) status = edit(status, idx, true);

        // ******************** 메인 로직 ********************
        dfs(0, 1);


        // ******************** 정답 출력 ********************
        sb.append(maxReadableWord);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    public static void dfs(int depth, int startIdx) { // startIdx 건드리기
//        System.out.printf("[IN] depth : %d\n",depth);
//        printStatus(status);
        if (depth == K-5) {
            int numOfReadableWord = 0;
            for (int word : words) {
                if (canRead(word)) numOfReadableWord++;
            }
            maxReadableWord = Math.max(maxReadableWord, numOfReadableWord);
            return;
        }

        for (int i=startIdx; i<26; i++) {
            if (learnedIdx.contains(i)) continue;
            status = edit(status, i, true);
            dfs(depth+1, i+1);
            status = edit(status, i, false);
        }
    }

    public static boolean canRead(int word) {
        return (status & word) == word;
    }

    public static int wordToStatus(String word) {
        int len = word.length();
        int wordStatus = 0;
        for (int i=0; i<len; i++) {
            wordStatus = edit(wordStatus, word.charAt(i)-'a', true);
        }
        return wordStatus;
    }

    public static int edit(int status, int idx, boolean mode) {
        int v = 1 << idx;
        if (mode) status |= v;
        else status &= ~v;
        return status;
    }

    public static int get(int status, int idx) {
        return (status >> idx) & 1;
    }

    public static void printStatus(int status) {
        System.out.println("-- status --");
        for (int i=0; i<26; i++) {
            System.out.printf("%d ", get(status, i));
        }
        System.out.println();
    }
}