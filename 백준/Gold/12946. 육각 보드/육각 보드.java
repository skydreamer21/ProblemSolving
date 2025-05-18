import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    static final int[][] DIR = { { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 1 }, { 1, -1 } };
    static final int  WALL = 0;
    static final int EMPTY = 2;

    static int N;
    static int[][] map;

    static class Pair {

        int x, y;

        public Pair(int x, int y) {
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
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            char[] tmp = br.readLine().toCharArray();
            for (int j=0; j<N; j++) {
                if (tmp[j] == '-') {
                    map[i][j] = WALL;
                } else if (tmp[j] == 'X') {
                    map[i][j] = EMPTY;
                }
            }
        }

        // bfs 탐색
        int max = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == EMPTY) {
                    int colorNeed = bfs(i, j);
                    max = Math.max(max, colorNeed);
//                    System.out.printf("(%d, %d) -> %d\n", i, j, colorNeed);
                }
            }
        }

        sb.append(max);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    static public int bfs(int x, int y) {
//        System.out.printf("start bfs at (%d, %d)\n", x, y);
        Deque<Pair> q = new ArrayDeque<>();
        boolean needThree = false;

        q.add(new Pair(x, y));
        map[x][y] = 1;
        int cnt = 0;

        while (!q.isEmpty()) {
            Pair now = q.poll();
            cnt++;

            for (int[] d : DIR) {
                int nextX = now.x + d[0];
                int nextY = now.y + d[1];
                if (isInRange(nextX, nextY)  && map[nextX][nextY] != WALL) {
                    if (map[nextX][nextY] == EMPTY) {
                        map[nextX][nextY] = -map[now.x][now.y];
                        q.add(new Pair(nextX, nextY));
                    } else if (map[now.x][now.y] == map[nextX][nextY]) {
                        needThree = true;
                    }

                }
            }
        }
//        System.out.printf("cnt : %d\n", cnt);

        if (needThree) {
            return 3;
        } else if (cnt == 1) {
            return 1;
        } else {
            return 2;
        }
    }

    static public boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N;
    }

}
