import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static final char LAND = 'L';
    static final char MID = 'M';
    static final char WATER = 'W';
    
    static int N, M;
    static char[][] map;
    
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
        
        map = new char[N][M];
        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
        }
        
        int maxMinDiff = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == LAND) {
                    int dist = bfs(i, j);
                    maxMinDiff = Math.max(maxMinDiff, dist);
                }
            }
        }
        
        sb.append(maxMinDiff);
        
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static int bfs(int x, int y) {
        Deque<Point> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][M];
        q.add(new Point(x, y));
        visited[x][y] =true;
        int dist = 0;
        
        while (!q.isEmpty()) {
            dist++;
            int size = q.size();
            while (size-- > 0) {
                Point now = q.poll();
                for (int[] d : DIR) {
                    int nextX = now.x + d[0];
                    int nextY = now.y + d[1];
                    if (isInRange(nextX, nextY) && !visited[nextX][nextY] && map[nextX][nextY] == LAND) {
                        visited[nextX][nextY] = true;
                        q.add(new Point(nextX, nextY));
                    }
                }
            }
        }
        return dist - 1;
    }
    
    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < M;
    }
}