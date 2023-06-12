import java.io.*;
import java.util.*;

public class Main {
    static int N, M;
    static Graph graph;
    static int[] hackableComps;
    static int max = -1;
    
    static class Graph {
        List<Integer>[] adjList;
        
        public Graph(int size) {
            adjList = new List[size + 1];
            
            for (int i = 1; i <= size; i++) {
                adjList[i] = new ArrayList<>();
            }
        }
        
        public void addEdge(int from, int to) {
            adjList[from].add(to);
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        graph = new Graph(N);
        hackableComps = new int[N + 1];
        
        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            graph.addEdge(from, to);
        }
        
        for (int i = 1; i <= N ; i++) {
            bfs(i);
        }
        
        for (int i = 1; i <= N; i++) {
            if (hackableComps[i] > max) {
                max = hackableComps[i];
            }
        }
        
        for (int i = 1; i <= N; i++) {
            if (hackableComps[i] == max) {
                sb.append(i).append(" ");
            }
        }
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static void bfs(int computer) {
        Deque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[N + 1];
        q.add(computer);
        visited[computer] = true;
        hackableComps[computer]++;
        
        while (!q.isEmpty()) {
            int now = q.poll();
            
            for (int next : graph.adjList[now]) {
                if (visited[next]) continue;
                hackableComps[next]++;
                visited[next] = true;
                q.add(next);
            }
        }
    }
}