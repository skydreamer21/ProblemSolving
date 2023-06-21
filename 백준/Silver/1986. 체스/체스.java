import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static final int[][] DIR = {
            {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1},
            {1, 2}, {2, 1}, {-1, 2}, {-2, 1}, {-1, -2}, {-2, -1}, {1, -2}, {2, -1}
    };

    static final int[][] moveTable = {
            {},
            {0, 1, 2, 3, 4, 5, 6, 7}, // queen
            {8, 9, 10, 11, 12, 13, 14, 15}, // knight
    };

    static final boolean[] isMultipleMove = {
            false, // dummy
            true, // queen
            false, // knight
    };

    static final int EMPTY = 0;
    static final int QUEEN = 1;
    static final int KNIGHT = 2;
    static final int PAWN = 3;
    static final int BLOCK = 4;

    static int N, M;
    static int[][] map;
    static int answer;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        answer = N * M;

        int numberOfPiece;

        // 입력
        for (int piece = 1; piece <= 3; piece++) {
            st = new StringTokenizer(br.readLine());
            numberOfPiece = Integer.parseInt(st.nextToken());
            answer -= numberOfPiece; // numberOfPiece가 바뀔 가능성이 있는지 없는지 체크 중요!
            while (numberOfPiece-- > 0) {
                map[Integer.parseInt(st.nextToken())-1][Integer.parseInt(st.nextToken())-1] = piece;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == EMPTY || map[i][j] == BLOCK || map[i][j] == PAWN) continue;
                check(i, j);
            }
        }

        sb.append(answer);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static void check(int x, int y) {
        int piece = map[x][y];
        for (int dirIdx : moveTable[piece]) {
            checkOneWay(x, y, dirIdx);
        }
    }

    private static void checkOneWay(int x, int y, int dirIdx) {
        int nextX = x + DIR[dirIdx][0];
        int nextY = y + DIR[dirIdx][1];
        while (isPossible(nextX, nextY)) {
            if (map[nextX][nextY] == EMPTY) {
                answer--;
                map[nextX][nextY] = BLOCK;
            }
            nextX += DIR[dirIdx][0];
            nextY += DIR[dirIdx][1];
            if (!isMultipleMove[map[x][y]]) break;
        }
    }

    private static boolean isPossible(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < M && isAvailable(x, y);
    }

    private static boolean isAvailable(int x, int y) {
        for (int i = 1; i <= 3; i++) {
            if (map[x][y] == i) return false;
        }
        return true;
    }
}