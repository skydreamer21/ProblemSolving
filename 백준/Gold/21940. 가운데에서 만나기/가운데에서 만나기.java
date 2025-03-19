import java.io.*;
import java.util.*;

public class Main {
    private static final int INF = Integer.MAX_VALUE;

    static int N, M;
    static Graph g;
    static int min = INF;
    static List<Integer> nodes;

    static class Graph {
        int[][] g;

        public Graph(int[][] graphData) {
            this.g = graphData;
            fw();
        }

        private void fw() {
            for (int k=0; k<N; k++) {
                for (int i=0; i<N; i++) {
                    for (int j=0; j<N; j++) {
                        if (i != j && g[i][k] != INF && g[k][j] != INF) {
                            g[i][j] = Math.min(g[i][j], g[i][k] + g[k][j]);
                        }
                    }
                }
            }
        }

        public int dist(int s, int e) {
            return g[s][e] + g[e][s];
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<N; i++) {
                for (int j=0; j<N; j++) {
                    sb.append(g[i][j]).append(" ");
                }
                sb.append("\n");
            }
            return sb.toString();
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

        int[][] data = new int[N][N];
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                data[i][j] = INF;
            }
        }

        for (int i=0; i<N; i++) {
            data[i][i] = 0;
        }

        for (int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            data[s-1][e-1] = d;
        }

        g = new Graph(data);
//        System.out.println(g);

        int T = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] p = new int[T];
        for (int i=0; i<T; i++) {
            p[i] = Integer.parseInt(st.nextToken())-1;
        }

        for (int end=0; end<N; end++) {
            // i 지점이 가운데 일때
            int max = 0;
            for (int start : p) {
                int d = g.dist(start, end);
                max = Math.max(max, d);
            }

            if (max < min) {
                min = max;
                nodes = new ArrayList<>();
                nodes.add(end);
            } else if (max == min) {
                nodes.add(end);
            }
        }

        Collections.sort(nodes);

        for (int n : nodes) {
            sb.append(n+1).append(" ");
        }


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}
