import java.util.*;
import java.io.*;

public class Main {
    private static final int DIV = 1_000_000_007;
    private static final int DEC_2 = 0;
    private static final int DEC_1 = 1;
    private static final int INC_1 = 2;
    private static final int INC_2 = 3;
    private static final int NOT_YET = -1;

    static int N;
    static int[][][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        dp = new int[N+1][10][4];

        for (int i=0; i<=N; i++) {
            for (int j=0; j<10; j++) {
                for (int k=0; k<4; k++) {
                    dp[i][j][k] = NOT_YET;
                }
            }
        }

        // n = 1 초기값 설정
        for (int k=0; k<10; k++) {
            dp[1][k][DEC_2] = 0;
            dp[1][k][INC_2] = 0;
            dp[1][k][INC_1] = 1;
            dp[1][k][DEC_1] = 1;
        }

        int ans = 0;
        if (N>=2) {
            for (int i=0; i<10; i++) {
                for (int j=0; j<4; j++) {
                    ans = (ans + solveDP(N, i, j)) % DIV;
                }
            }
        } else {
            ans = 10;
        }

        sb.append(ans);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static int solveDP(int n, int k, int u) {
        if (dp[n][k][u] != NOT_YET) {
            return dp[n][k][u];
        }

        dp[n][k][u] = 0;
        if (n >= 3 && k != 8 && k != 9 && u == DEC_2) { // 현재 감소 2번째. 
            dp[n][k][u] = (solveDP(n-2, k+2, INC_1) + solveDP(n-2, k+2, INC_2)) % DIV;
        } else if ( k!= 9 && u == DEC_1) { // 현재 감소 1번째.
            dp[n][k][u] = (solveDP(n-1, k+1, INC_1) + solveDP(n-1, k+1, INC_2)) % DIV;
        } else if ( k != 0 && u == INC_1) { // 현재 증가 1번째.
            dp[n][k][u] = (solveDP(n-1, k-1, DEC_1) + solveDP(n-1, k-1, DEC_2)) % DIV;
        } else if (n >= 3 && k!=0 && k!=1 && u == INC_2) {
            dp[n][k][u] = (solveDP(n-2, k-2, DEC_1) + solveDP(n-2, k-2, DEC_2)) % DIV;
        }
        return dp[n][k][u];
    }

}