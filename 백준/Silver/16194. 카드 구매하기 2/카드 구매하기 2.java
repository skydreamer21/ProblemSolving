import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] arr;
    static int[] dp;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        arr = new int[N+1];
        dp = new int[N+1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        for (int i = 1; i <= N; i++) {
            dp[i] = arr[i];
            for (int j = 1; j < i; j++) {
                dp[i] = Math.min(dp[i], arr[j] + dp[i - j]);
            }
        }
        
        sb.append(dp[N]);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}