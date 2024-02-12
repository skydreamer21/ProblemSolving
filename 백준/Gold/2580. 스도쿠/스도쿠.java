import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static Board board;
    static List<Point> emptyPoints;
    static boolean findAns;
    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Board {
        static final int BOARD_SIZE = 9;
        static final int RECT_SIZE = 3;
        static final int BITS_PER_NUMBER = 4;
        static final int MASK_SIZE = 16;
        static final int EMPTY = 0;

        final long FOUR_BITS_ON = (1 << BITS_PER_NUMBER) - 1;

        long[] board;
        int idx;

        public Board() {
            board = new long[6];
            idx = 0;
        }

        public void addNumber(int value) {
            int row = idx / MASK_SIZE;
            board[row] = (board[row] << BITS_PER_NUMBER) | value;
            idx++;
        }

        public void endInitBoard() {
            while (idx % MASK_SIZE != 0) {
                addNumber(EMPTY);
            }
        }

        public int get(int x, int y) {
            int lineIdx = x * BOARD_SIZE + y;
            int row = lineIdx / MASK_SIZE;
            int col = MASK_SIZE - 1 - (lineIdx % MASK_SIZE);
            return (int)((board[row] >> (col << 2)) & FOUR_BITS_ON);
        }

        public void set(int x, int y, int value) {
            int lineIdx = x * BOARD_SIZE + y;
            int row = lineIdx / MASK_SIZE;
            int col = MASK_SIZE - 1 - (lineIdx % MASK_SIZE);
            int pushBits = col << 2;
            board[row] = (board[row] & ~(FOUR_BITS_ON << (pushBits))) | ((long)value << (pushBits));
        }

        public void setEmpty(int x, int y) {
            set(x, y, EMPTY);
        }

        public boolean isPossibleToSet(int x, int y, int value) {
            if (get(x, y) != EMPTY) return false;

            // row check
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (col == y) continue;
                if (get(x, col) == value) return false;
            }

            // col check
            for (int row = 0; row < BOARD_SIZE; row++) {
                if (row == x) continue;
                if (get(row, y) == value) return false;
            }

            // rect check
            int rectX = x / RECT_SIZE;
            int rectY = y / RECT_SIZE;
            for (int row = 0; row < RECT_SIZE; row++) {
                for (int col = 0; col < RECT_SIZE; col++) {
                    int nowX = rectX * RECT_SIZE + row;
                    int nowY = rectY * RECT_SIZE + col;
                    if (nowX == x && nowY == y) continue;
                    if (get(nowX, nowY) == value) return false;
                }
            }
            return true;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    sb.append(get(i, j)).append(" ");
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        board = new Board();
        emptyPoints = new ArrayList<>();
        findAns = false;

        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                int value = Integer.parseInt(st.nextToken());
                board.addNumber(value);
                if (value == Board.EMPTY) {
                    emptyPoints.add(new Point(i, j));
                }
            }
        }
        board.endInitBoard();
        if (emptyPoints.isEmpty()) {
            System.out.println(board);
            return;
        }

        dfs(0);

        sb.append(board);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    private static void dfs(int depth) {
        if (findAns) return;

        if (depth == emptyPoints.size()) {
            findAns = true;
            return;
        }

        int nowX = emptyPoints.get(depth).x;
        int nowY = emptyPoints.get(depth).y;
        for (int i = 1; i <= Board.BOARD_SIZE; i++) {
            if (board.isPossibleToSet(nowX, nowY, i)) {
                board.set(nowX, nowY, i);
                dfs(depth + 1);
                if (findAns) return;
                board.setEmpty(nowX, nowY);
            }
        }
    }
}