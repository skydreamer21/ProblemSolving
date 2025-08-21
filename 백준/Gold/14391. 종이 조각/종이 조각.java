import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static final boolean H = true;
    static final boolean V = false;

    static int N, M;
    static int[][] numMap;
    static int max = 0;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        numMap = new int[N][M];
        visited = new boolean[N][M];

        for (int i = 0; i<N; i++) {
            char[] row = br.readLine().toCharArray();
            for (int j=0; j<M; j++) {
                numMap[i][j] = row[j] - '0';
            }
        }

        dfs(0, 0, 0);
        sb.append(max);


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static void dfs(int r, int c, int score) {
        if (r >= N || c >= M) {
            max = Math.max(max, score);
            return;
        }

        if (visited[r][c]) {
            if (c == M-1) {
                dfs(r+1, 0, score);
            } else {
                dfs(r, c+1, score);
            }
        }

        // 가로
        for (int i = 0; i<M-c; i++) {
            if (visited[r][c+i]) break;
            int tempScore = calculateScore(r, c, c+i, H);
            visit(r, c, c+i, H, true);
            if (c+i == M-1) {
                dfs(r+1, 0, score+tempScore);
            } else {
                dfs(r, c+1, score+tempScore);
            }
            visit(r, c, c+i, H, false);
        }

        //  세로
        for (int i = 0; i<N-r; i++) {
            if (visited[r+i][c]) break;
            int tempScore = calculateScore(c, r, r+i, V);
            visit(c, r, r+i, V, true);
            if (c == M-1) {
                dfs(r+1, 0, score+tempScore);
            } else {
                dfs(r, c+1, score+tempScore);
            }
            visit(c, r,r+i, V, false);
        }
    }

    public static int calculateScore(int pos, int from, int to, boolean dir) {
//        System.out.printf("pos : %d, %d~%d, H ? %b\n", pos, from, to, dir);
        int score = 0;
        for (int i = from; i<=to; i++) {
            score *= 10;
            int num;
            if (dir == H) {
                num = numMap[pos][i];
            } else {
                num = numMap[i][pos];
            }
            score += num;
        }
//        System.out.printf(" => %d\n", score);
        return score;
    }

    public static void visit(int pos, int from, int to, boolean dir, boolean visit) {
        for (int i = from; i<=to; i++) {
            if (dir == H) {
                visited[pos][i] = visit;
            } else {
                visited[i][pos] = visit;
            }
        }
    }

    public void printMap() {
        for (int i = 0 ; i<N; i++) {
            for (int j=0; j<M; j++) {

            }
        }
    }

}
