import java.io.*;
import java.util.*;

public class Main {
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static final char EMPTY = '.';
    static final char WALL = '*';
    static final char DOOR = '#';
    static final int INF = Integer.MAX_VALUE;

    static int N, M;
    static char[][] map;
    static int[][][] dist;
    static Point[] starts;

    static class Point {
        int x, y, p;

        public Point(int x, int y, int p) {
            this.x = x;
            this.y = y;
            this.p = p;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            char[][] nowMap = new char[n][m];
            for (int i=0; i<n; i++) {
                nowMap[i] = br.readLine().toCharArray();
            }
            sb.append(solution(n, m, nowMap)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static int solution(int n, int m, char[][] nowMap) {
        N = n;
        M = m;
        init(nowMap);
        
        bfs();
        int ans = INF;
        for (int i=1; i<N+1; i++) {
            for (int j=1; j<M+1; j++) {
                int sum = 0;
                for (int k=0; k<3; k++) {
                    sum += dist[i][j][k];
                }
                if (map[i][j] == DOOR) {
                    sum -= 2;
                }
                ans = Math.min(ans, sum);
            }
        }

        ans = Math.min(ans, dist[0][0][0] + dist[0][0][1] + dist[0][0][2]);
        return ans;

    }

    public static void bfs() {
        Deque<Point> q = new ArrayDeque<>();
        for (int i=0; i<3; i++) {
            q.add(starts[i]);
        }
        Point now;

        while (!q.isEmpty()) {
            now = q.poll();
            int person = now.p;

            for (int[] d : DIR) {
                int nextX = now.x + d[0];
                int nextY = now.y + d[1];

                if (isImpossible(nextX, nextY)) continue;
                int cost = map[nextX][nextY] == DOOR ? 1 : 0;

                if (dist[now.x][now.y][person] + cost < dist[nextX][nextY][person]) {
                    dist[nextX][nextY][person] = dist[now.x][now.y][person] + cost;
                    Point next = new Point(nextX, nextY, person);
                    if (cost == 0) {
                        q.addFirst(next);
                    } else {
                        q.addLast(next);
                    }
                }
            }
        }
    }
    private static boolean isImpossible(int x, int y) {
        return isNotInRange(x, y) || map[x][y] == WALL;
    }
    private static boolean isNotInRange(int x, int y) {
        return x<0 || y<0 || x>=N+2 || y>=M+2;
    }

    public static void init(char[][] nowMap) {
        map = new char[N+2][M+2];
        dist = new int[N+2][M+2][3];
        starts = new Point[3];
        starts[0] = new Point(0,0,0);
        int startCnt = 1;

        // start 저장
        // map 복사
        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                if (nowMap[i][j] == '$') {
                    starts[startCnt] = new Point(i+1, j+1, startCnt++);
                    nowMap[i][j] = EMPTY;
                }
                map[i + 1][j + 1] = nowMap[i][j];
            }
        }

        // map padding
        for (int i=0; i<N+2; i++) {
            map[i][0] = EMPTY;
            map[i][M+1] = EMPTY;
        }

        for (int i=0; i<M+2; i++) {
            map[0][i] = EMPTY;
            map[N+1][i] = EMPTY;
        }

        // dist Inf
        for (int k=0; k<3; k++) {
            for (int i=0; i<N+2; i++) {
                for (int j=0; j<M+2; j++) {
                    dist[i][j][k] = INF;
                }
            }
        }

        // start dist -> 0
        for (int i=0; i<3; i++) {
            dist[starts[i].x][starts[i].y][starts[i].p] = 0;
        }
    }
}