import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static final String POSSIBLE = "Defend the CTP";
    static final String IMPOSSIBLE = "Destroyed the CTP";

    static int N, M;
    static Graph g;

    static class Graph {
        List<Integer>[] g; // original graph
        List<Integer>[] rg; // reverse graph
        boolean[] fromOne;
        boolean[] fromN;

        public Graph() {
            g = new List[N+1];
            rg = new List[N+1];
            for (int i=1; i<=N; i++) {
                g[i] = new ArrayList<>();
                rg[i] = new ArrayList<>();
            }
            fromOne = new boolean[N+1];
            fromN = new boolean[N+1];
        }

        public void addEdge(int from, int to) {
            g[from].add(to);
            rg[to].add(from);
        }

        public void setGraph() {
            bfs(1, g, fromOne);
            bfs(N, rg, fromN);
        }

        private void bfs(int start, List<Integer>[] graph, boolean[] visited) {
            // start에서 출발해서 각 곳에 방문여부를 채우는 함수
            Deque<Integer> q = new ArrayDeque<>();
            q.add(start);
            visited[start] = true;

            while(!q.isEmpty()) {
                int now = q.poll();

                for (int next : graph[now]) {
                    if (!visited[next]) {
                        visited[next] = true;
                        q.add(next);
                    }
                }
            }
        }

        public boolean isPossible(int target) {
            return fromOne[target] && fromN[target];
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

        g = new Graph();

        for (int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            g.addEdge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        g.setGraph();

        int T = Integer.parseInt(br.readLine());
        for (int i=0; i<T; i++) {
            int target = Integer.parseInt(br.readLine());
            sb.append(g.isPossible(target) ? POSSIBLE : IMPOSSIBLE)
              .append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
