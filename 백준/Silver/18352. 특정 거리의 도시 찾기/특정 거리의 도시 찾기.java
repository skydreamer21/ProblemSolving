import java.io.*;
import java.util.*;

public class Main {
    private static final int NOT_EXIST = -1;
    static int N, M, K, X;
    static Graph graph;
    static int[] dist;
    
    static class Graph {
        List<Integer>[] adjList;
        boolean[] visited;
        
        public Graph(int size) {
            adjList = new List[size + 1];
            for (int i = 1; i <= size; i++) {
                adjList[i] = new ArrayList<>();
            }
            visited = new boolean[size + 1];
        }
        
        public void addEdge(int from, int to) {
            adjList[from].add(to);
        }
    }
    
    static class Pair implements Comparable<Pair> {
        int v, w;
        
        public Pair(int v, int w) {
            this.v = v;
            this.w = w;
        }
        
        @Override
        public int compareTo(Pair o) {
            return this.w - o.w;
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
        K = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        
        graph = new Graph(N);
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            graph.addEdge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        
        dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        dijkstra();
        for (int i = 1; i <= N; i++) {
            if (dist[i] == K) {
                sb.append(i).append("\n");
            }
        }
        if (sb.length() == 0) {
            sb.append(NOT_EXIST);
        }
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static void dijkstra() {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(X, 0));
        
        while (!pq.isEmpty()) {
            Pair now = pq.poll();
            if (graph.visited[now.v]) continue;
            
            graph.visited[now.v] = true;
            dist[now.v] = now.w;
            for (int next : graph.adjList[now.v]) {
                if (graph.visited[next] || now.w + 1 >= dist[next]) continue;
                pq.add(new Pair(next, now.w + 1));
                dist[next] = now.w + 1;
            }
        }
    }
}