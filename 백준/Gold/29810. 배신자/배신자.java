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
    static int N, M;
    static int cnt = 0;
    static boolean hasCycle = false;

    static class Graph {
        List<Integer>[] g;
        boolean[] visited;
        int bet;
        int max = 0;

        public Graph() {
            g = new List[N+1];
            for (int i = 1; i<=N; i++) {
                g[i] = new ArrayList<>();
            }
            visited = new boolean[N + 1];
        }

        public void addEdge(int v1, int v2) {
            g[v1].add(v2);
            g[v2].add(v1);
        }

        private void init() {
            hasCycle = false;
            cnt =  0;
        }

        private void searchBet() {
            visited[bet] = true;
            for (int next : g[bet]) {
                if (visited[next]) continue;
                init();
                dfs(next);

                if (!hasCycle) cnt++;
//                System.out.printf("bet next : %d, cnt : %d, hasCycle: %b\n", next, cnt, hasCycle);
                max = Math.max(max, cnt);
            }
        }

        public void search() {
            searchBet();
            for (int i = 1; i<=N; i++) {
                if (visited[i]) continue;
                init();
                dfs(i);
//                System.out.printf("start from %d -> cnt : %d\n", i, cnt);
                max = Math.max(max, cnt);
            }
        }

        private void dfs(int start) {
            Deque<Integer> stack = new ArrayDeque<>();
            visited[start] = true;
            cnt++;

            for (int next : g[start]) {
                if (next == bet) continue;

                if (!visited[next]) {
                    visited[next] = true;
                    stack.addFirst(next);
                    cnt++;
                }
            }

            int now;
            while (!stack.isEmpty()) {
                now = stack.poll();

                for (int next : g[now]) {
                    if (next == bet) {
                        hasCycle = true;
                        continue;
                    }

                    if (!visited[next]) {
                        visited[next] = true;
                        stack.addFirst(next);
                        cnt++;
                    }
                }
            }
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
        Graph graph = new Graph();
        for (int i = 0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            graph.addEdge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        graph.bet = Integer.parseInt(br.readLine());

        graph.search();

        sb.append(graph.max);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
