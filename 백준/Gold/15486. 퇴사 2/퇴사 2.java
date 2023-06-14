import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int NOT_YET = -1;
    static int N;
    static Consult[] consults;
    static int[] dp;
    
    static class Consult {
        int time, price;
        
        public Consult(int time, int price) {
            this.time = time;
            this.price = price;
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        consults = new Consult[N+1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            consults[i] = new Consult(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        dp = new int[N+1];
        Arrays.fill(dp, NOT_YET);
        
        solveDP();
        sb.append(dp[1]);
        
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static void solveDP() {
        dp[N] = consults[N].time == 1 ? consults[N].price : 0;
        
        for (int i = N-1; i >= 1; i--) {
            dp[i] = 0;
            if (i + consults[i].time - 1 <= N) {
                dp[i] = consults[i].price;
                if (i + consults[i].time <= N) {
                    dp[i] += dp[i + consults[i].time];
                }
            }
            dp[i] = Math.max(dp[i], dp[i + 1]);
        }
    }
}