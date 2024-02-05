import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static final char GT = '>';
    static final char LT = '<';
    static final char PADDING = '0';

    static int N;
    static long min = 10_000_000_000L;
    static long max = 0;
    static int[] numbers;
    static boolean[] visited = new boolean[10];
    static char[] signs;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        numbers = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        signs = new char[N+1];
        for (int i = 1; i <= N; i++) {
            signs[i] = st.nextToken().charAt(0);
        }

        for (int i = 0; i < 10; i++) {
            visited[i] = true;
            numbers[0] = i;
            dfs(1);
            visited[i] = false;
        }
        String zeroPaddingFormat = String.format("%%%c%dd", PADDING, N + 1);
        sb.append(String.format(zeroPaddingFormat, max)).append("\n")
                .append(String.format(zeroPaddingFormat, min));


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    static void dfs(int depth) {
//        System.out.printf("[IN] depth: %d, status : %s\n", depth, getStatus(depth));

        if (depth == N+1) {
            long num = makeNum();
//            System.out.printf("MAX DEPTH!! num : %d\n", num);

            max = Math.max(max, num);
            min = Math.min(min, num);
//            System.out.printf("[OUT - max depth] depth: %d, status : %s\n", depth, getStatus(depth));
            return;
        }

        for (int i = 0; i < 10; i++) {
            if (visited[i]) continue;

            if (signs[depth] == GT && numbers[depth-1] > i) {
                numbers[depth] = i;
            } else if (signs[depth] == LT && numbers[depth-1] < i) {
                numbers[depth] = i;
            } else {
                continue;
            }

            visited[i] = true;
            dfs(depth + 1);
            visited[i] = false;
        }
//        System.out.printf("[OUT - all searched!] depth: %d, status : %s\n", depth, getStatus(depth));
    }

    static long makeNum() {
        long num = 0;
        for (int i = 0; i < N+1; i++) {
            num *= 10;
            num += numbers[i];
        }
        return num;
    }

    public static String getStatus(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= N; i++) {
            if (i < depth) {
                sb.append(numbers[N - i]).append(" ");
            } else {
                sb.append("_").append(" ");
            }
        }
        return sb.toString();
    }

}