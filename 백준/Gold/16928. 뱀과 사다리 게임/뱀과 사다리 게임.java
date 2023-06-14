import java.io.*;
import java.util.*;

public class Main {
    static final int SIZE = 10;
    private static final int IMPOSSIBLE = -1;
    static int N, M;
    static Map<Integer, Integer> specialPath = new HashMap<>();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int total = N + M;
        while (total-- > 0) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            specialPath.put(from, to);
        }
        
        int answer = bfs();
        if (answer == IMPOSSIBLE) {
            System.out.println("xxxxxxxxxxxxx");
            System.out.println("비정상적인 실행");
            System.out.println("xxxxxxxxxxxxx");
            return;
        }
        sb.append(answer);
        
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static int bfs() {
        Deque<Integer> q = new ArrayDeque<>();
        q.add(1);
        boolean[] visited = new boolean[101];
        visited[1] = true;
        int cnt = 0;
        
        while (!q.isEmpty()) {
            cnt++;
            int size = q.size();
            while (size-- > 0) {
                int now = q.poll();
                for (int i = 1; i <= 6; i++) {
                    int next = now + i;
                    if (next > 100 || visited[next]) continue;
                    visited[next] = true;
                    if (specialPath.containsKey(next)) {
                        next = specialPath.get(next);
                        if (visited[next]) continue;
                        visited[next] = true;
                    }
                    if (next == 100) {
                        return cnt;
                    }
                    q.add(next);
                }
            }
        }
        return IMPOSSIBLE;
    }
}