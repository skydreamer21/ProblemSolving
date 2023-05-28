import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int IMPOSSIBLE = -1;
    static final int USE = 0;
    static final int NOT_USE = 1;
    
    static int N;
    static int[][] dp;
    static int[] times;
    static int[] prices;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        times = new int[N+1];
        prices = new int[N+1];
        dp = new int[2][N+1];
        
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            times[i] = Integer.parseInt(st.nextToken());
            prices[i] = Integer.parseInt(st.nextToken());
        }
        
        initDp();
        sb.append(Math.max(solveDP(USE, 1), solveDP(NOT_USE, 1)));
        
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static int solveDP(int isUsed, int n) {
        if (n > N) {
            return 0;
        }
        
        if (dp[isUsed][n] != IMPOSSIBLE) {
            return dp[isUsed][n];
        }
        
        dp[isUsed][n] = 0;
        
        if (isUsed == USE) { // 해당일을 한다면
            if (n + times[n] - 1 <= N) {
                dp[isUsed][n] = prices[n] + Math.max(solveDP(USE, n+times[n]), solveDP(NOT_USE, n+times[n]));
            }
        } else { // 해당일의 일을 하지 않는다면
            dp[isUsed][n] = Math.max(solveDP(USE, n+1), solveDP(NOT_USE, n+1));
        }
        return dp[isUsed][n];
    }
    
    public static void initDp() {
        for (int i = 0; i < 2; i++) {
            Arrays.fill(dp[i], IMPOSSIBLE);
        }
    }
}