import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] dp;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        int ans;
        String[] input1;
        String[] input2;
        while (T-->0) {
            N = Integer.parseInt(br.readLine());
            dp = new int[3][N+1];
            input1 = br.readLine().split(" ");
            input2 = br.readLine().split(" ");
            for (int i=1;i<=N;i++) {
                dp[1][i]=Integer.parseInt(input1[i-1]);
                dp[2][i]=Integer.parseInt(input2[i-1]);
            }
            solveDP();
            ans = Math.max(dp[1][N],dp[2][N]);
            sb.append(ans).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    public static void solveDP() {
        for (int i=2;i<=N;i++) {
            dp[1][i]=Math.max(dp[2][i-1],dp[2][i-2])+dp[1][i];
            dp[2][i]=Math.max(dp[1][i-1],dp[1][i-2])+dp[2][i];
        }
    }
}