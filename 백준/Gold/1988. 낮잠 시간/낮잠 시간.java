import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static final int IMPOSSIBLE = -1;
    static final int NOT_YET = -2;
    static final int SLEEP = 1;
    static final int NOT_SLEEP = 0;

    static int N, B;
    static int[] arr;
    static int[][][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        arr = new int[N+1];
        for (int i=1; i<=N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        // db[n][b][sleep] = value : n번 구간까지 b번자고, n번구간에서 잘때/안잘때 피로회복 최댓값
        dp = new int[N+1][B+1][2];

        // init
        for (int i=0; i<=N; i++) {
            dp[i][0][SLEEP] = IMPOSSIBLE;
            dp[i][0][NOT_SLEEP] = 0;
        }
        for (int i=1; i<=N; i++) {
            for (int b=1; b<=B; b++) {
                dp[i][b][SLEEP] = NOT_YET;
                dp[i][b][NOT_SLEEP] = NOT_YET;
            }
        }

        sb.append(Math.max(solveDP(N, B, 0), solveDP(N, B, 1)));

//        for (int b=0; b<=B; b++) {
//                System.out.printf("%s ", b);
//            for (int i=0; i<=N; i++) {
//                System.out.printf("(%3d/%3d) ", dp[i][b][NOT_SLEEP], dp[i][b][SLEEP]);
//            }
//            System.out.println();
//        }


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static int solveDP(int n, int b, int isSleep) {
        if (dp[n][b][isSleep] != NOT_YET) {
            return dp[n][b][isSleep];
        }

        if (n < b) {
            return IMPOSSIBLE;
        }

        if (n == b && isSleep == NOT_SLEEP) {
            return IMPOSSIBLE;
        }

        // isSleep = true 일때
        if (isSleep == SLEEP) {
            // 직전 구간에서 잤을 때
            int v1 = solveDP(n-1, b-1, SLEEP);
            int option1 = v1 == IMPOSSIBLE ? IMPOSSIBLE : v1 + arr[n];

            // 직전 구간에서 안잤을 때,
            int option2 = solveDP(n-1, b-1, NOT_SLEEP);
            dp[n][b][isSleep] = Math.max(option1, option2);
        } else {
            dp[n][b][isSleep] = Math.max(solveDP(n - 1, b, SLEEP), solveDP(n - 1, b, NOT_SLEEP));
        }

        return dp[n][b][isSleep];
    }

}
