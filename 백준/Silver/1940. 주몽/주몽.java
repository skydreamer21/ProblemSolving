import java.io.*;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static HashSet<Integer> numbers;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        numbers = new HashSet<>();
        
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int answer = 0;
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());
            int target = M - num;
            if (numbers.contains(target)) {
                answer++;
            }
            numbers.add(num);
        }
        
        sb.append(answer);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}