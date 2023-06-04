import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int INF = Integer.MAX_VALUE;
    static final int IMPOSSIBLE = -1;

    static int N;
    static int[] arr;
    static int[] dp;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        dp = new int[N];
        Arrays.fill(dp, INF);
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i]  = Integer.parseInt(st.nextToken());
        }

        if (N > 1 && arr[0] == 0) {
            System.out.println(IMPOSSIBLE);
            return;
        }

        dp[N - 1] = 0;

        for (int i = N - 2; i >= 0; i--) {
            for (int j = 1; j <= arr[i]; j++) {
                if (i + j >= N || dp[i+j] == INF) continue;

                dp[i] = Math.min(dp[i], dp[i + j]);
            }
            if (dp[i] != INF) {
                dp[i]++;
            }
        }

        sb.append(dp[0] == INF ? IMPOSSIBLE : dp[0]);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}