import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static final char EMPTY = '-';
    static final char FILL = '*';
    static final int LEFT = -1;
    static final int RIGHT = 1;

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static class CookieMeasurer {
        char[][] map;
        int N;
        Point heart;
        Point endOfWaist;

        public CookieMeasurer(char[][] map) {
            this.map = map;
            N = map.length;
            heart = findHeart();
            endOfWaist = findEndOfWaist();
        }

        private Point findHeart() {
            for (int i=0; i<N; i++) {
                for (int j=0; j<N; j++) {
                    if (map[i][j] == FILL) {
                        return new Point(i + 1, j);
                    }
                }
            }
            throw new IllegalArgumentException("쿠키에 심장이 없습니다...");
        }

        private Point findEndOfWaist() {
            if (heart == null) {
                throw new IllegalArgumentException("심장을 먼저 찾고 허리를 탐색해야 합니다.");
            }

            int x = heart.x;
            int y = heart.y;

            while (isInRange(x, y) && map[x][y] == FILL) {
                x += 1;
            }

            return new Point(x - 1, y);
        }
        public Point getHeart() {
            return heart;
        }

        public int getWaistLength() {
            return endOfWaist.x - heart.x;
        }

        // left : -1, right : 1
        public int getArmLength(int dir) {
            validateDir(dir);

            int x = heart.x;
            int y = heart.y + dir;
            int cnt = 0;

            while (isInRange(x, y) && map[x][y] == FILL) {
                y += dir;
                cnt++;
            }
            return cnt;
        }

        public int getLegLength(int dir) {
            validateDir(dir);
            if (endOfWaist == null) {
                throw new IllegalArgumentException("허리를 아직 탐색하지 않았습니다.");
            }

            int x = endOfWaist.x + 1;
            int y = endOfWaist.y + dir;
            int cnt = 0;

            while (isInRange(x, y) && map[x][y] == FILL) {
                x += 1;
                cnt++;
            }
            return cnt;
        }


        private void validateDir(int dir) {
            if (dir != LEFT && dir != RIGHT) {
                throw new IllegalArgumentException("왼쪽(0), 오른쪽(1) 이외의 변수가 getArmLength에 입력되었습니다.");
            }
        }

        private boolean isInRange(int x, int y) {
            return x >= 0 && y >= 0 && x < N && y < N;
        }


    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        char[][] map = new char[N][N];

        for (int i=0; i<N; i++) {
            map[i] = br.readLine().toCharArray();
        }

        CookieMeasurer cookieMeasurer = new CookieMeasurer(map);
        Point heart = cookieMeasurer.getHeart();
        sb.append(heart.x+1).append(" ").append(heart.y+1).append("\n");
        sb.append(cookieMeasurer.getArmLength(LEFT)).append(" ")
                .append(cookieMeasurer.getArmLength(RIGHT)).append(" ")
                .append(cookieMeasurer.getWaistLength()).append(" ")
                .append(cookieMeasurer.getLegLength(LEFT)).append(" ")
                .append(cookieMeasurer.getLegLength(RIGHT)).append(" ");


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}