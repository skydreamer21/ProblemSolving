import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    private static final int INF = Integer.MAX_VALUE;
    static int N, M;
    static int start;
    static Graph g;
    static int[] dist;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        start = Integer.parseInt(br.readLine());
        g = new Graph(N);
        dist = new int[N + 1];
        visited = new boolean[N + 1];

        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            g.addEdge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        initDist();
        dijkstra();

        for (int i=1; i<=N; i++) {
            sb.append(dist[i] == INF ? "INF" : dist[i]).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static void dijkstra() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(start, 0));
        int checked = 0;

        while (checked < N && !pq.isEmpty()) {
            Edge now = pq.poll();
            if (visited[now.v]) continue;
            visited[now.v] = true;
            checked++;

            for (Edge next : g.adjList[now.v]) {
                if (now.w + next.w < dist[next.v]) {
                    dist[next.v] = now.w + next.w;
                    pq.add(new Edge(next.v, dist[next.v]));
                }
            }
        }

    }

    private static void initDist() {
        for (int i = 1; i <= N; i++) {
            dist[i] = INF;
        }
        dist[start] = 0;
    }

    static class Graph {
        List<Edge>[] adjList;

        public Graph(int size) {
            adjList = new ArrayList[size + 1];
            for (int i = 1; i <= size; i++) {
                adjList[i] = new ArrayList<>();
            }
        }

        public void addEdge(int v1, int v2, int w) {
            adjList[v1].add(new Edge(v2, w));
        }
    }

    static class Edge implements Comparable<Edge>{
        int v, w;

        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge o) {
            return this.w - o.w;
        }
    }
}