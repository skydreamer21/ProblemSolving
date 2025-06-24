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

    static class Graph {
        final int NOT_YET = 0;

        List<Integer>[] edges;
        int[] dp;

        public Graph(int n) {
            edges = new List[n+1];
            for (int i = 1; i<=n; i++) {
                edges[i] = new ArrayList<>();
            }
            dp = new int[n + 1];
        }

        public void addEdge(int target, int pre) {
            edges[target].add(pre);
        }

        public int findSem(int target) {
            if (dp[target] != NOT_YET) {
                return dp[target];
            }

            int max = 0;
            for (int pre : edges[target]) {
                max = Math.max(max, findSem(pre));
            }
            dp[target] = max+1;
            return dp[target];
        }

        public void find() {
            for (int i = 1; i<=N; i++) {
                findSem(i);
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
        Graph g = new Graph(N);
        for (int i = 0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int pre = Integer.parseInt(st.nextToken());
            int sub = Integer.parseInt(st.nextToken());
            g.addEdge(sub, pre);
        }

        g.find();
        for (int i = 1; i<=N; i++) {
            sb.append(g.dp[i]).append(" ");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
