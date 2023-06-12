import java.io.*;
import java.util.Arrays;

public class Main {
    static final int EMPTY = 0;
    static final int LEFT = 1;
    static final int RIGHT = 2;
    private static final int DIV = 9901;
    
    static int N;
    static int[][] dp;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        N = Integer.parseInt(br.readLine());
        dp = new int[N][3];
        
        Arrays.fill(dp[0], 1);
        
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < 3; j++) {
                dp[i][j] = dp[i - 1][EMPTY];
                switch (j) {
                    case LEFT:
                        dp[i][j] += dp[i - 1][RIGHT];
                        break;
                    case RIGHT:
                        dp[i][j] += dp[i - 1][LEFT];
                        break;
                    case EMPTY:
                        dp[i][j] += (dp[i - 1][LEFT] + dp[i - 1][RIGHT]);
                        break;
                }
                dp[i][j] %= DIV;
            }
        }
        
        sb.append((dp[N - 1][EMPTY] + dp[N - 1][LEFT] + dp[N - 1][RIGHT]) % DIV);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}