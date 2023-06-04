import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static final int[][] DIR = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
    static final int SHARK = -1;
    static final int EMPTY = 0;

    static int N, M;
    static int[][] map;
    static Deque<Point> q = new ArrayDeque<>();

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
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
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 1) {
                    q.add(new Point(i, j));
                    map[i][j] = SHARK;
                }
            }
        }

        sb.append(bfs());

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static int bfs() {
        int dist = 0;

        while (!q.isEmpty()) {
            dist++;
            int size = q.size();
            while (size-- > 0) {
                Point now = q.poll();
                for (int[] d : DIR) {
                    int nextX = now.x + d[0];
                    int nextY = now.y + d[1];
                    if (isInRange(nextX, nextY) && map[nextX][nextY] == EMPTY) {
                        map[nextX][nextY] = dist;
                        q.add(new Point(nextX, nextY));
                    }
                }
            }
        }
        return dist-1;
    }

    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < M;
    }
}