import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] score;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            N = Integer.parseInt(br.readLine());
            score = new int[N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                score[Integer.parseInt(st.nextToken())-1] = Integer.parseInt(st.nextToken());
            }
            
            int answer = 0;
            int interviewMax = N + 1;
            for (int interview : score) {
                if (interview < interviewMax) {
                    answer++;
                    interviewMax = interview;
                }
            }
            
            sb.append(answer).append("\n");
        }
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}