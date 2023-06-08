import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[] arr;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        int max = 0;
        for (int i = 0; i < M; i++) {
            max += arr[i];
        }
        
        int sum = max;
        for (int i = M; i < N; i++) {
            sum -= arr[i - M];
            sum += arr[i];
            max = Math.max(max, sum);
        }
        
        sb.append(max);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}