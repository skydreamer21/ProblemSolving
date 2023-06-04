import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] delta;
    static long answer = 0;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            answer += Integer.parseInt(st.nextToken());
        }
        delta = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            delta[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(delta);
        
        for (int i = 0; i < N; i++) {
            answer += i * delta[i];
        }
        
        sb.append(answer);
        
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}