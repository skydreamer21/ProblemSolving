import java.io.*;
import java.util.*;

public class Main {
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static int N, min, max;
    static MyInteger[][] map;
    static boolean[][] visited;
    static int cnt, sum;
    
    static class Point {
        int x, y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    static class MyInteger {
        int n;
        
        public MyInteger(int n) {
            this.n = n;
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
        
        map = new MyInteger[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = new MyInteger(Integer.parseInt(st.nextToken()));
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
                cnt = 0;
                sum = 0;
                MyInteger num = new MyInteger(0);
                dfs(i, j, num);
                num.n = sum / cnt;
                moved |= (cnt > 1);
            }
        }
        return moved;
    }
    
    private static void dfs(int x, int y, MyInteger num) {
        cnt++;
        sum += map[x][y].n;
        visited[x][y] = true;
        for (int[] d : DIR) {
            Point next = new Point(x + d[0], y + d[1]);
            if (isInRange(next.x, next.y) && !visited[next.x][next.y] && isPossible(new Point(x, y), next)) {
                dfs(next.x, next.y, num);
            }
        }
        map[x][y] = num;
    }
    
    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N;
    }
    
    private static boolean isPossible(Point p1, Point p2) {
        int diff = Math.abs(map[p1.x][p1.y].n - map[p2.x][p2.y].n);
        return diff >= min && diff <= max;
    }
}