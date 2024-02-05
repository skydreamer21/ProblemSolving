import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] numbers;
    static boolean findAns = false;
    static String answer;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        numbers = new int[N];

        dfs(0);

        for (int i = 0; i < N; i++) {
            sb.append(numbers[i]);
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    static void dfs(int depth) {
        if (findAns) return;

        if (depth == N) {
            findAns = true;
            return;
        }

        for (int i = 1; i <= 3; i++) {
            numbers[depth] = i;
            boolean isPossible = check(depth);
            if (isPossible) {
                dfs(depth + 1);
            }
            if (findAns) return;
        }
    }

    private static boolean check(int depth) {
//        System.out.printf("check in depth %d\n", depth);
        if (depth == 0) return true;

        int len = depth + 1;
        for (int i = 1; i <= len/2; i++) {

            boolean hasSameArr = true;
            for (int j = 0; j < i; j++) {
                if (numbers[depth - j] != numbers[depth - j - i]) {
                    hasSameArr = false;
                    break;
                }
            }

            if (hasSameArr) return false;
        }
        return true;
    }
}