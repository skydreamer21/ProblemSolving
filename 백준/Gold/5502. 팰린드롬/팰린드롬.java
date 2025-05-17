import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static char[] str;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        str = br.readLine().toCharArray();
        dp = new int[N+1][N+1];
        int min = N-1;
        for (int i = 1; i<N; i++) {
            int end = N - i;
            for (int j=1; j<=end; j++) {
                if (str[i-1] == str[N-j]) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
            int rowMin = Math.min(N - (2*dp[i][end]), N - 1 - (2*dp[i][end-1]));
            min = Math.min(min, rowMin);
        }

        sb.append(min);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
