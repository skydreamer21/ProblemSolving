import java.io.*;
import java.util.*;

public class Main {
    static int N, M, K;
    static Deque<Integer> dq = new ArrayDeque<>();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        for (int i = 1; i <= N; i++) {
            dq.add(i);
        }
        
        boolean dir = true;
        int cnt = 0;
        while (!dq.isEmpty()) {
            if (dir) {
                for (int i = 0; i < M - 1; i++) {
                    dq.add(dq.poll());
                }
                sb.append(dq.poll()).append("\n");
            } else {
                for (int i = 0; i < M - 1; i++) {
                    dq.addFirst(dq.pollLast());
                }
                sb.append(dq.pollLast()).append("\n");
            }
            cnt++;
            if (cnt == K) {
                dir = !dir;
                cnt = 0;
            }
        }
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}