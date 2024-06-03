import java.util.*;

class Solution {
    static int N, M;
    static int[][] maps;
    static final int[][] DIR = {{1,0}, {0,1}, {-1,0}, {0, -1}};
    static final int WALL = 0;
    static final int PATH = 1;
    static final int NOT_VISITED = -2;
    static final int IMPOSSIBLE = -1;
    
    static class Point {
        int x, y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public int solution(int[][] m) {
        N = m.length;
        M = m[0].length;
        maps = m;
        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                if (maps[i][j] == PATH) {
                    maps[i][j] = NOT_VISITED;
                }
            }
        }
        maps[0][0] = PATH;
        
        int answer = bfs();
        return answer;
    }
    
    public int bfs() {
        Deque<Point> q = new ArrayDeque<>();
        q.add(new Point(0, 0));
        Point now;
        
        while (!q.isEmpty()) {
            now = q.poll();
            
            for (int[] d : DIR) {
                int nextX = now.x + d[0];
                int nextY = now.y + d[1];
                if (isInRange(nextX, nextY) && maps[nextX][nextY] != WALL && maps[nextX][nextY] == NOT_VISITED) {
                    maps[nextX][nextY] = maps[now.x][now.y] + 1;
                    if (nextX == N-1 && nextY == M-1) {
                        return maps[nextX][nextY];
                    }
                    q.add(new Point(nextX, nextY));
                }
            }
            
        }
        return IMPOSSIBLE;
    }
    
    public boolean isInRange(int x, int y) {
        return x>=0 && y>=0 && x < N && y < M;
    }
}