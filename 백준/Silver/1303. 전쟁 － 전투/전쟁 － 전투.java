import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static final char WHITE = 'W';
    static final char BLUE = 'B';
    static final char VISITED = 'X';
    
    static int N, M;
    static char[][] map;
    
    static class Point{
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
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        map = new char[N][M];
        
        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
        }
        
        int myPower = 0;
        int oppPower = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == VISITED) continue;
                int power = bfs(i, j);
                if (power > 0) {
                    myPower += power;
                } else {
                    oppPower -= power;
                }
            }
        }
        
        sb.append(myPower).append(" ").append(oppPower);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static int bfs(int x, int y) {
        Deque<Point> q = new ArrayDeque<>();
        q.add(new Point(x, y));
        char team = map[x][y];
        map[x][y] = VISITED;
        int cnt = 1;
        
        while (!q.isEmpty()) {
            Point now = q.poll();
            
            for (int[] d : DIR) {
                int nextX = now.x + d[0];
                int nextY = now.y + d[1];
                if (isInRange(nextX, nextY) && map[nextX][nextY] == team) {
                    map[nextX][nextY] = VISITED;
                    cnt++;
                    q.add(new Point(nextX, nextY));
                }
            }
        }
        return team == WHITE ? cnt * cnt : -cnt * cnt;
    }
    
    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < M;
    }
}