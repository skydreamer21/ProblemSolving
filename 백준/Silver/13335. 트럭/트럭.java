import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N, M, K;
    static int[] trucks;
    static int[] enterTime;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        trucks = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            trucks[i] = Integer.parseInt(st.nextToken());
        }
        enterTime = new int[N];
        
        int left = 0;
        int right = 1;
        enterTime[0] = 0;
        int w = trucks[0];
        int beforeTime = 0;
        while (right < N) {
            if (w + trucks[right] <= K) {
                enterTime[right] = beforeTime + 1;
                beforeTime += 1;
            } else {
                while (w + trucks[right] > K) {
                    w -= trucks[left];
                    beforeTime = enterTime[left] + M;
                    left++;
                }
                enterTime[right] = beforeTime;
            }
            w += trucks[right++];
            while (enterTime[left] + M <= beforeTime) {
                w -= trucks[left++];
            }
        }
        
        sb.append(beforeTime + M + 1);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}