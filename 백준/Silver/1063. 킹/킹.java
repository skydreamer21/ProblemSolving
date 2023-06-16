import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static final int KING = 0;
    static final int STONE = 1;
    private static final int SIZE = 8;

    static HashMap<String, int[]> move = new HashMap<>();
    static Point[] chessPieces;

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String convert() {
            char[] pos = new char[2];
            pos[0] = (char) ('A' + y);
            pos[1] = (char) ('1' + x);
            return String.valueOf(pos);
        }

        @Override
        public boolean equals(Object obj) {
            Point o = (Point) obj;
            return this.x == o.x && this.y == o.y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        initMove();
        st = new StringTokenizer(br.readLine());
        chessPieces = new Point[2];
        chessPieces[KING] = convert(st.nextToken());
        chessPieces[STONE] = convert(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        while (N-- > 0) {
            moveKing(br.readLine());
        }

        sb.append(chessPieces[KING].convert()).append("\n")
                .append(chessPieces[STONE].convert()).append("\n");


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static void moveKing(String command) {

        int[] d = move.get(command);

        for (int i=0; i<2; i++) {
            if (isNotInRange(chessPieces[i].x + d[0], chessPieces[i].y + d[1])) {
                if (i == STONE) {
                    chessPieces[KING].x -= d[0];
                    chessPieces[KING].y -= d[1];
                }
                break;
            }
            chessPieces[i].x += d[0];
            chessPieces[i].y += d[1];
            if (i == KING && !chessPieces[i].equals(chessPieces[i + 1])) {
                break;
            }
        }
    }

    private static boolean isNotInRange(int x, int y) {
        return x<0 || y<0 || x>=SIZE || y>=SIZE;
    }

    private static void initMove() {
        move.put("R", new int[]{0, 1});
        move.put("L", new int[]{0, -1});
        move.put("B", new int[]{-1, 0});
        move.put("T", new int[]{1, 0});
        move.put("RT", new int[]{1, 1});
        move.put("RB", new int[]{-1, 1});
        move.put("LT", new int[]{1, -1});
        move.put("LB", new int[]{-1, -1});
    }

    private static Point convert(String pos) {
        int x = pos.charAt(1) - '1';
        int y = pos.charAt(0) - 'A';
        return new Point(x, y);
    }
}