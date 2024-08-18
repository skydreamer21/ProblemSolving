import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static final int DIV = 987_654_321;

    static int N;
    static long[] dp;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        N = n/2;
        dp = new long[N+1];

        // 초기값
        dp[0] = 1;
        dp[1] = 1;

        int l,r;
        for (int i=2; i<=N; i++) {
            l=0;
            r=i-1;
            while (l < r) {
                dp[i] += (2*dp[l]*dp[r]) % DIV;
                dp[i] %= DIV;
                l++;
                r--;
            }

            if (i % 2 ==1) {
                if (l != r ) {
                    throw new IllegalArgumentException("홀수 항에서 마지막 l과 r이 같지 않습니다.");
                }
                dp[i] += (dp[l]*dp[r]) % DIV;
            }
        }
        
        sb.append(dp[N] % DIV);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}