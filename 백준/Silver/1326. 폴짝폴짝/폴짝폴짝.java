import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static final int IMPOSSIBLE = -1;
    static int N;
    static int[] arr;
    static int start, end;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken()) - 1;
        end = Integer.parseInt(st.nextToken()) - 1;
        
        if (start == end) {
            System.out.println(0);
            return;
        }
        
        sb.append(bfs());
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static int bfs() {
        Deque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[N];
        q.add(start);
        visited[start] = true;
        int cnt = 0;
        
        while (!q.isEmpty()) {
            cnt++;
            int size = q.size();
            
            while (size-- > 0) {
                int now = q.poll();
                int step = arr[now];
                int initValue = now - (now / step) * step;
                
                for (int next = initValue; next < N; next += step) {
                    if (!visited[next]) {
                        if (next == end) {
                            return cnt;
                        }
                        visited[next] = true;
                        q.add(next);
                    }
                }
            }
        }
        return IMPOSSIBLE;
    }
}