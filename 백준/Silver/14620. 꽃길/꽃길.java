import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static final int TOTAL = 3;
    private static final int IMPOSSIBLE = -1;
    static int N, size;
    static int[][] map;
    static boolean[][] isPossible;
    static int min = Integer.MAX_VALUE;
    
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
        
        N = Integer.parseInt(br.readLine());
        size = N * (N-1) - 1;
        map = new int[N][N];
        isPossible = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(isPossible[i], true);
        }
        
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        combi(0, N+1, 0);
        sb.append(min);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static void combi(int cnt, int start, int price) {
        if (cnt == TOTAL) {
            min = Math.min(min, price);
            return;
        }
        
        if (start == IMPOSSIBLE) {
            return;
        }
        
        for (int i = start; i < size; i++) {
            int[] coor = lineToPlane(i);
            if (coor[1] == 0 || coor[1] == N-1) continue;
            
            if (isPossible[coor[0]][coor[1]]) {
                int priceForFlower = bfs(coor[0], coor[1], false);
                if (priceForFlower == IMPOSSIBLE) continue;
                combi(cnt+1, findNext(i), price + priceForFlower);
                bfs(coor[0], coor[1], true);
            }
        }
    }
    
    private static int findNext(int lineIdx) {
        for (int i = lineIdx + 1; i < size; i++) {
            int[] coor = lineToPlane(i);
            if (coor[1] == 0 || coor[1] == N-1) continue;
            
            if (isPossible[coor[0]][coor[1]]) return i;
        }
        return IMPOSSIBLE;
    }
    
    private static int bfs(int x, int y, boolean onOrOff) {
        Deque<Point> q = new ArrayDeque<>();
        q.add(new Point(x, y));
        isPossible[x][y] = onOrOff;
        int price = map[x][y];
        
        for (int i=0; i<4; i++) {
            int nextX = x + DIR[i][0];
            int nextY = y + DIR[i][1];
            if(isInRange(nextX, nextY)) {
                if (onOrOff == false && !isPossible[nextX][nextY]) {
                    isPossible[x][y] = true;
                    for (int j = 0; j < i; j++) { // 되돌리기
                        isPossible[x + DIR[j][0]][y + DIR[j][1]] = true;
                    }
                    return IMPOSSIBLE;
                }
                price += map[nextX][nextY];
                
                isPossible[nextX][nextY] = onOrOff;
                q.add(new Point(nextX, nextY));
            }
        }
        return price;
    }
    
    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N;
    }
    
    private static int[] lineToPlane(int idx) {
        return new int[]{idx / N, idx % N};
    }
}