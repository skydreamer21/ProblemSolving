import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static final char EMPTY = '.';
    static final char VIRUS = '*';
    static final char BUILDING = '#';
    static final int[][] DIR = {{1,0}, {0,1}, {-1,0}, {0,-1}};

    static int N, M, P, D, X, B;
    static char[][] map;
    static PriorityQueue<Node> pq;

    static class Node implements Comparable<Node> {
        int r, c, time;

        public Node(int r, int c, int time) {
            this.r = r;
            this.c = c;
            this.time = time;
        }

        public Node(int r, int c) {
            this.r = r;
            this.c = c;
            this.time = 0;
        }

        public boolean hasDelay() {
            return time > 0;
        }

        public void decreaseDelay() {
            time--;
        }

        public String toString() {
            return String.format("Node(%d, %d, %d)", r, c, time);
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.time, o.time);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        P = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());


        boolean[][] visited = new boolean[N][M];
        map = new char[N][M];
        pq = new PriorityQueue<>();
        for (int i=0; i<N; i++) {
            char[] row = br.readLine().toCharArray();
            for (int j=0; j<M; j++) {
                map[i][j] = row[j];
                if (map[i][j] == VIRUS) {
                    pq.add(new Node(i, j));
                    visited[i][j] = true;
                }
            }
        }


        // bfs
        while (!pq.isEmpty() && pq.peek().time <= P) {
//            System.out.printf("time : %d\n", time);
//            printMap();
//            System.out.println(pq);
            Node now = pq.poll();
//            System.out.println("now = " + now);
            map[now.r][now.c] = VIRUS;
//            printMap();

            for (int[] d : DIR) {
                int nextX = now.r + d[0];
                int nextY = now.c + d[1];

                if (isInRange(nextX, nextY) && !visited[nextX][nextY] && map[nextX][nextY] != VIRUS) {
                    int delay = 1;
                    if (map[nextX][nextY] == BUILDING) {
                        delay += D;
                    }
                    visited[nextX][nextY] = true;
                    pq.add(new Node(nextX, nextY, now.time+delay));
                }
            }


        }

//        System.out.println("FINAL");
//        printMap();

        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                if (map[i][j] != VIRUS) {
                    sb.append(i+1).append(" ").append(j+1).append("\n");
                }
            }
        }
        if (sb.length()==0) {
            sb.append(-1);
        }



        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static boolean isInRange(int r, int c) {
        return r >= 0 && c >= 0 && r < N && c < M;
    }

    public static void printMap() {
        for (int i=0; i<N; i++) {
            System.out.println(Arrays.toString(map[i]));
        }
    }
}

