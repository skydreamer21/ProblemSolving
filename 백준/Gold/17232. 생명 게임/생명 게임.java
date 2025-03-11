import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static final char EMPTY = '.';
    static final char LIFE = '*';

    static int N, M, T, K, a, b;

    static class Map {
        char[][] map;
        int[][] countMap;

        public Map(char[][] mapData) {
            this.map = mapData;
            this.countMap = new int[N+1][M+1];
        }

        public void processTurn() {
//            System.out.println("\n==== [PROCESS TURN] ====");
            makeCountMap();
//            printCountMap();
//            System.out.println();
//            printLivesCount();
            for (int r = 1; r<=N; r++) {
                for (int c = 1; c<=M; c++) {
                    int count = countLivesAround(r, c);
                    if (map[r][c] == LIFE) {
                        if (count < a || count > b) {
                            map[r][c] = EMPTY;
                        }
                    } else {
                        if (count > a && count <= b) {
                            map[r][c] = LIFE;
                        }
                    }
                }
            }
//            System.out.println("\n==== [RESULT] ====");
//            printMap();
        }

        private void makeCountMap() {
            for (int i = 1; i<=N; i++) {
                for (int j=1; j<=M; j++) {
                    countMap[i][j] = countMap[i-1][j] + countMap[i][j-1] - countMap[i-1][j-1];
                    if (map[i][j] == LIFE) countMap[i][j]++;
                }
            }
        }

        private int countLivesAround(int r, int c) {
            int beforeR = Math.max(r-(K+1), 0);
            int beforeC = Math.max(c-(K+1), 0);
            int startR = Math.min(r + K, N);
            int startC = Math.min(c + K, M);
            int now = map[r][c] == LIFE ? 1 : 0;
            return countMap[startR][startC] - countMap[beforeR][startC] - countMap[startR][beforeC] + countMap[beforeR][beforeC] - now;
        }

        public char data(int r, int c) {
            return map[r][c];
        }

        public void printMap() {
            for (int r=1; r<=N; r++) {
                System.out.println(Arrays.toString(map[r]));
            }
        }

        public void printCountMap() {
            for (int r=1; r<=N; r++) {
                for (int c=1; c<=M; c++) {
                    System.out.printf("%d ", countMap[r][c]);
                }
                System.out.println();
            }
        }

        public void printLivesCount() {
            for (int r=1; r<=N; r++) {
                for (int c=1; c<=M; c++) {
                    System.out.printf("%d ", countLivesAround(r, c));
                }
                System.out.println();
            }
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
        T = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());

        char[][] mapData = new char[N+1][M+1];
        for (int i = 1; i<=N; i++) {
            char[] row = br.readLine().toCharArray();
            for (int j=1; j<=M; j++) {
                mapData[i][j] = row[j-1];
            }
        }

        Map map = new Map(mapData);

        for (int i = 0; i<T; i++) {
            map.processTurn();
        }

        for (int r = 1; r<=N; r++) {
            for (int c = 1; c<=M; c++) {
                sb.append(map.data(r, c));
            }
            sb.append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
