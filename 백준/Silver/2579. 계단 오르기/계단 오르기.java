import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static final int ONE = 0;
    static final int TWO = 1;
    static final int IMPOSSIBLE = -1;

    static int N;
    static int[] stairs;
    static int[][] dp;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        stairs = new int[N];
        for (int i=0; i<N; i++) {
            stairs[i] = Integer.parseInt(br.readLine());
        }

        if (N == 1) {
            System.out.println(stairs[0]);
            return;
        }

        dp = new int[N][2];

        // 1. 초기 설정
        dp[N-1][ONE] = stairs[N - 1];
        dp[N-1][TWO] = stairs[N - 1];
        dp[N - 2][ONE] = stairs[N - 2] + dp[N - 1][TWO];
        dp[N - 2][TWO] = IMPOSSIBLE;

        for (int i=N-3; i>=0; i--) {
            // 1칸 뛸때
            dp[i][ONE] = IMPOSSIBLE;
            if (dp[i+1][TWO] != IMPOSSIBLE) {
                dp[i][ONE] = stairs[i] + dp[i+1][TWO];
            }

            // 2칸 뛸때
            dp[i][TWO] = stairs[i] + getMaxScoreByStair(i+2);
        }

        sb.append(Math.max(getMaxScoreByStair(0), getMaxScoreByStair(1)));

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static int getMaxScoreByStair(int stair) {
        return Math.max(dp[stair][ONE], dp[stair][TWO]);
    }
}