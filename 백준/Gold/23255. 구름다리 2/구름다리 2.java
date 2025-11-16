import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static Graph g;

    static class Graph {
        int N;
        Deque<Integer>[] edges;
        Set<Integer>[] adjColors;

        public Graph(int n) {
            this.N = n;
            edges = new ArrayDeque[N+1];
            adjColors = new HashSet[N+1];
            for (int i=1; i<=N; i++) {
                edges[i] = new ArrayDeque<>();
                adjColors[i] = new HashSet<>();
            }
        }

        public void addEdge(int v1, int v2) {
            edges[v1].add(v2);
            edges[v2].add(v1);
        }

        public void visit(int node, int color) {
            for (int next : edges[node]) {
                adjColors[next].add(color);
            }
        }

        public int getMinColor(int node) {
            int c = 1;
            boolean find = false;
            while(!find) {
                if (!adjColors[node].contains(c)) {
                    find = true;
                    continue;
                }
                c++;
            }
            return c;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        g = new Graph(N);
        for (int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            g.addEdge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        for (int i=1; i<=N; i++) {
            int minColor = g.getMinColor(i);
            sb.append(minColor).append(" ");
            g.visit(i, minColor);
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
