import java.io.*;
import java.util.*;

public class Main {

    static final int[][] DIR = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
    static final int NOT_YET = -1;

    static int N, M, K, L, ans;
    static char[][] map;
    static char[] word;
    static int[][][] dp;

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x=x;
            this.y=y;
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
        K = Integer.parseInt(st.nextToken());

        map = new char[N][M];
        for (int i=0; i<N; i++) {
            map[i] = br.readLine().toCharArray();
        }
        word = br.readLine().toCharArray();
        L = word.length;

        // 1. 시작점 찾기
        List<Point> startPoints = new ArrayList<>();
        findStartPoints(startPoints);

        dp = new int[N][M][L];
        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                for (int k=0; k<L; k++) {
                    dp[i][j][k] = NOT_YET;
                }
            }
        }

        ans = 0;
        for (Point p : startPoints) {
            ans += solveDP(p.x, p.y, 0);
        }

        sb.append(ans);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static void findStartPoints(List<Point> startPoints) {
        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                if (map[i][j] == word[0]) {
                    startPoints.add(new Point(i, j));
                }
            }
        }
    }

    // wordIdx : 현재 위치의 wordIdx
    public static int solveDP(int x, int y, int wordIdx) {
        if (wordIdx == L-1) {
            return 1;
        }

        if (dp[x][y][wordIdx] != NOT_YET) {
            return dp[x][y][wordIdx];
        }

        dp[x][y][wordIdx] = 0;

        for (int d=0; d<4; d++) {
            for (int move=1; move<=K; move++) {
                int nextX = x + DIR[d][0]*move;
                int nextY = y + DIR[d][1]*move;
                if (!isInRange(nextX, nextY)) break;
                if (map[nextX][nextY] == word[wordIdx+1]) {
                    dp[x][y][wordIdx] += solveDP(nextX, nextY, wordIdx+1);
                }
            }
        }

        return dp[x][y][wordIdx];
    }

    public static boolean isInRange(int x, int y){
        return x>=0 && y>=0 && x<N && y<M;
    }

}