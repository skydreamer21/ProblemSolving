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
    static final char WALL = '-';
    static final char EMPTY = 'X';
    static final char COLOR_A = 'a';
    static final char COLOR_B = 'b';
    static final char IMPOSSIBLE = '.';
    static final Set<Character> PALATE = new HashSet<>(Arrays.asList(COLOR_A, COLOR_B));

    static int N;
    static char[][] map;

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
        map = new char[N][N];

        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
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
        map[x][y] = COLOR_A;
        int cnt = 0;

        while (!q.isEmpty()) {
            Pair now = q.poll();
            cnt++;

            for (int[] d : DIR) {
                int nextX = now.x + d[0];
                int nextY = now.y + d[1];
                if (isInRange(nextX, nextY)  && map[nextX][nextY] == EMPTY) {
                    q.add(new Pair(nextX, nextY));
                    char color = COLOR_A;
                    if (!needThree) {
                        color = findColor(nextX, nextY);
                        needThree = (color == IMPOSSIBLE);
                    }
//                    System.out.printf("next : (%d, %d), color : %c\n", nextX, nextY, color);
                    map[nextX][nextY] = color;

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

    static public char findColor(int x, int y) {
        int availables = 3;

        for (int i = 0; i<6; i++) {
            int nextX = x + DIR[i][0];
            int nextY = y + DIR[i][1];
            if (isInRange(nextX, nextY) && PALATE.contains(map[nextX][nextY])) {
                int c = map[nextX][nextY] - 'A';
                availables &= (~(1 << c));
            }
        }
        if (availables == 0) return IMPOSSIBLE;
        else if (availables == 2) return COLOR_B;
        else return COLOR_A;
    }

    static public boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N;
    }

}
