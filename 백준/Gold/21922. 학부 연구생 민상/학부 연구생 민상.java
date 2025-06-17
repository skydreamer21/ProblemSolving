import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static class Map {
        final int T1 = 1; // |
        final int T2 = 2; // -
        final int T3 = 3; // /
        final int T4 = 4; // \
        final int AC = 9;
        final int EMPTY = 0;
        final int[][] DIR = {
                {-1,0}, // up
                {0,1}, // right
                {1, 0}, // down
                {0, -1} // left
        };
        final int UP = 0;
        final int RIGHT = 1;
        final int DOWN = 2;
        final int LEFT = 3;
        final int[] STUCK_DIR = {-1, -1};
        final int STUCK = -1;

        int N, M;
        int[][] map;
        boolean[][] visited;
        int cnt;

        public Map(int[][] map) {
            this.map = map;
            N = map.length;
            M = map[0].length;
            visited = new boolean[N][M];
            cnt = 0;
        }

        public void search() {
            for (int i = 0; i<N; i++) {
                for (int j=0; j<M; j++) {
                    if (map[i][j] != AC) continue;
//                    System.out.println(show());
                    searchFromAC(i, j);
                }
            }
        }

        private void searchFromAC(int r, int c) {
            visit(r, c);
            for (int[] d : DIR) {
                searchFromPointByDirection(r, c, d);
//                System.out.println("after one d search");
//                System.out.println(show());
            }
        }

        private void searchFromPointByDirection(int r, int c, int[] d) {
//            System.out.println("searchFromPointByDir start");
            int nextX = r + d[0];
            int nextY = c + d[1];
            while(isInRange(nextX, nextY)) {
                visit(nextX, nextY);
                if (isThing(nextX, nextY)) {
                    d = changeDir(map[nextX][nextY], d);
                    if (d[0] == -1 && d[1] == -1) break;
                }
                nextX += d[0];
                nextY += d[1];
//                System.out.println(show());
            }
        }

        private int[] changeDir(int thing, int[] d) {
            int changedDirIdx = -2;
            // 멈춘다면 [-1, -1] 반환
            if (d[0] == -1 && d[1] == 0) { // up
                switch (thing) {
                    case T1:
                        changedDirIdx = UP;
                        break;
                    case T2:
                        changedDirIdx = STUCK;
                        break;
                    case T3:
                        changedDirIdx = RIGHT;
                        break;
                    case T4:
                        changedDirIdx = LEFT;
                }
            } else if (d[0] == 0 && d[1] == 1) { // right
                switch (thing) {
                    case T1:
                        changedDirIdx = STUCK;
                        break;
                    case T2:
                        changedDirIdx = RIGHT;
                        break;
                    case T3:
                        changedDirIdx = UP;
                        break;
                    case T4:
                        changedDirIdx = DOWN;
                }

            } else if (d[0] == 1 && d[1]==0) { // down
                switch (thing) {
                    case T1:
                        changedDirIdx = DOWN;
                        break;
                    case T2:
                        changedDirIdx = STUCK;
                        break;
                    case T3:
                        changedDirIdx = LEFT;
                        break;
                    case T4:
                        changedDirIdx = RIGHT;
                }

            } else { // left
                switch (thing) {
                    case T1:
                        changedDirIdx = STUCK;
                        break;
                    case T2:
                        changedDirIdx = LEFT;
                        break;
                    case T3:
                        changedDirIdx = DOWN;
                        break;
                    case T4:
                        changedDirIdx = UP;
                }
            }
            return changedDirIdx == STUCK ? STUCK_DIR : DIR[changedDirIdx];
        }

        private boolean isThing(int r, int c) {
            return map[r][c] != EMPTY && map[r][c] != AC;
        }

        private boolean isInRange(int r, int c) {
            return r >= 0 && c >= 0 && r < N && c < M;
        }

        private void visit(int r, int c) {
            if (!visited[r][c]) {
                visited[r][c] = true;
                cnt++;
            }
        }

        public String  show() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i<N; i++) {
                for (int j=0; j<M; j++) {
                    sb.append(visited[i][j] ? "*" : ".").append(" ");
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

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] mapInput = new int[N][M];
        for (int i = 0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0 ;j<M; j++) {
                mapInput[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Map map = new Map(mapInput);
        map.search();
//        System.out.println(map.show());

        sb.append(map.cnt);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
