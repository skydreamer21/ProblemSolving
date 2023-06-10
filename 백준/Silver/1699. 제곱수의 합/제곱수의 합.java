import java.io.*;

public class Main {
    static int N;
    static int[] dp;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        N = Integer.parseInt(br.readLine());
        int sqrtValue = (int) Math.sqrt(N);
        
        if (sqrtValue * sqrtValue == N) {
            System.out.println(1);
            return;
        }
        
        dp = new int[N+1];
        for (int i = 1; i <= N; i++) {
            dp[i] = i;
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        
        sb.append(dp[N]);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}