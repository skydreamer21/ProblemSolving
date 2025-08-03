import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static final int LEFT = 0;
    static final int RIGHT = 1;
    static final int NOT_CLOSE = 2;
    static final int INF = Integer.MAX_VALUE;
    static final int NOT_YET = -2;

    static int N, M;
    static int[][] map;
    static int[][][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][2];
        dp = new int[N][M+1][3];
        for (int i=0; i<N; i++) {
            for (int j=0; j<=M; j++) {
                for (int k=0; k<3; k++) {
                    dp[i][j][k] = NOT_YET;
                }
            }
        }

        int total = 0;
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<2; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                total += map[i][j];
            }
        }
        
        if (M == 0) {
            System.out.println(total);
            return;
        }

        // 초기값 설정 =
        dp[0][0][LEFT] = INF;
        dp[0][0][RIGHT] = INF;
        dp[0][0][NOT_CLOSE] = 0;
        dp[0][1][LEFT] = map[0][0];
        dp[0][1][RIGHT] = map[0][1];
        dp[0][1][NOT_CLOSE] = INF;

        // 1. 첫 M개의 줄
        sb.append(total - min(
                dfs(N - 1, M, LEFT),
                dfs(N - 1, M, RIGHT),
                dfs(N - 1, M, NOT_CLOSE)
        ));

//        for (int i=0; i<N; i++) {
//            System.out.printf("[N = %d]\n", i+1);
//            for (int j=0; j<=M; j++) {
//                for (int k=-1; k<3; k++) {
//                    if (k == -1) {
//                        System.out.printf("%10s", String.format("%d개 닫을 때 : ", j));
//                        continue;
//                    }
//                    System.out.printf("%10d ", dp[i][j][k]);
//                }
//                System.out.println();
//            }
//        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    /**
     *
     * @param n 몇번째 줄을 탐색하는지
     * @param l 현재 줄까지 몇개를 닫아야 하는지
     * @param c 현재 줄에서 닫는 문 설정
     * @return
     */
    static int dfs(int n, int l, int c) {
//        System.out.printf("[IN] n = %d, l = %d, c = %d\n", n, l, c);
        if (l > n+1) {
//            System.out.printf("[OUT - impossible] n = %d, l = %d, c = %d, res = %d\n", n, l, c, INF);
            return INF;
//            throw new IllegalArgumentException("이러한 조건은 들어올 수 없습니다.");
        }

        if (dp[n][l][c] != NOT_YET) {
//            System.out.printf("[OUT - already] n = %d, l = %d, c = %d, res = %d\n", n, l, c, dp[n][l][c]);
            return dp[n][l][c];
        }

        if (l == 0) {
            if (c == LEFT || c == RIGHT) {
//                System.out.printf("[OUT - lc impossible] n = %d, l = %d, c = %d, res = %d\n", n, l, c, INF);
                return INF;
            }
            // 닫아야 하는 문의 개수가 0이라면 현재는 무조건 오픈
            dp[n][l][c] = dfs(n - 1, l, NOT_CLOSE);
//            System.out.printf("[OUT - no close] n = %d, l = %d, c = %d, res = %d\n", n, l, c, INF);
            return dp[n][l][c];
        }

        if(c == LEFT) {
            // 현재 줄에서 왼쪽 문을 닫는다면,
            int before = min(dfs(n - 1, l - 1, LEFT), dfs(n - 1, l - 1, NOT_CLOSE));
            dp[n][l][c] = before != INF ? before + map[n][LEFT] : INF;
        } else if (c == RIGHT) {
            // 현재 줄에서 오른쪽 문을 닫는다면
            int before = min(dfs(n - 1, l - 1, RIGHT), dfs(n - 1, l - 1, NOT_CLOSE));
            dp[n][l][c] = before != INF ? before + map[n][RIGHT] : INF;
        } else { // 현재 줄에서 아무 문도 닫지 않을 때
            dp[n][l][c] = min(
                    dfs(n - 1, l, LEFT),
                    dfs(n - 1, l, RIGHT),
                    dfs(n - 1, l, NOT_CLOSE)
            );
        }

//        System.out.printf("[OUT] n = %d, l = %d, c = %d, res = %d\n", n, l, c, dp[n][l][c]);
        return dp[n][l][c];

    }

    static int min(int... numbers) {
        int min = INF;
        for (int num : numbers) {
            min = Math.min(min, num);
        }
        return min;
    }

}
