import java.io.*;
import java.util.StringTokenizer;

public class Solution {
    static final char NORMAL = '.';
    static final char BROKEN = '#';
    static final int SIZE_LINE = 2;
    static final int SIZE_AREA = 4;
    static final String YES = "YES";
    static final String NO = "NO";

    static int N, M;
    static char[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int TC = Integer.parseInt(br.readLine());
        int testNo = 0;
        while (testNo++ < TC) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            map = new char[N][M];
            int brokenCnt = 0;
            boolean isPossibleMap = true;
            for (int i = 0; i < N; i++) {
                char[] inputRow = br.readLine().toCharArray();
                for (int j=0; j<M; j++) {
                    map[i][j] = inputRow[j];
                    if (map[i][j] == BROKEN) {
                        brokenCnt++;
                    }
                }
            }

            if (brokenCnt % SIZE_AREA != 0) {
                printAns(sb, testNo, NO);
                continue;
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (map[i][j] == NORMAL) continue;
                    boolean isPossible = putTile(i, j);
                    if (!isPossible) {
                        isPossibleMap = false;
                        break;
                    }
                }
                if (!isPossibleMap) break;
            }

            printAns(sb, testNo, isPossibleMap ? YES : NO);

        }


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static void printAns(StringBuilder sb, int testNo, String answer) {
        sb.append("#").append(testNo++).append(" ").append(answer).append("\n");
    }

    private static boolean putTile(int r, int c) {
        for (int i = 0; i < SIZE_LINE; i++) {
            for (int j = 0; j < SIZE_LINE; j++) {
                if (isInRange(r + i, c + j) && map[r + i][c + j] == BROKEN) {
                    map[r + i][c + j] = NORMAL;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isInRange(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < M;
    }
}