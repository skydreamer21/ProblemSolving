import java.io.*;
import java.util.*;

public class Main {
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static int N, min, max;
    static int[][] map;
    static boolean[][] visited;
    
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
        min = Integer.parseInt(st.nextToken());
        max = Integer.parseInt(st.nextToken());
        
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        boolean moved = movePopulation();
        int day = 0;
        while (moved) {
            day++;
            moved = movePopulation();
        }
        
        sb.append(day);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static boolean movePopulation() {
        boolean moved = false;
        visited = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (visited[i][j]) continue;
                moved |= bfs(i, j);
            }
        }
        return moved;
    }
    
    private static boolean bfs(int x, int y) {
        List<Point> mergeList = new ArrayList<>();
        Deque<Point> q = new ArrayDeque<>();
        q.add(new Point(x, y));
        visited[x][y] = true;
        mergeList.add(new Point(x, y));
        int sum = map[x][y];
        
        while (!q.isEmpty()) {
            Point now = q.poll();
            for (int[] d : DIR) {
                Point next = new Point(now.x + d[0], now.y + d[1]);
                if (isInRange(next.x, next.y) && !visited[next.x][next.y] && isPossible(now, next)) {
                    visited[next.x][next.y] = true;
                    sum += map[next.x][next.y];
                    mergeList.add(next);
                    q.add(next);
                }
            }
        }
        
        int size = mergeList.size();
        for (Point point : mergeList) {
            map[point.x][point.y] = sum / size;
        }
        return size > 1;
    }
    
    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N;
    }
    
    private static boolean isPossible(Point p1, Point p2) {
        int diff = Math.abs(map[p1.x][p1.y] - map[p2.x][p2.y]);
        return diff >= min && diff <= max;
    }
    
    private static void printMap() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
    }
}