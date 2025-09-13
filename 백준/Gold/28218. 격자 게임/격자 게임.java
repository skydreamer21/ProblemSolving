import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static final int NOT_YET = 0;
    static final char WALL = '#';
    static final int FIRST = 1;
    static final int SECOND = 2;
    static int N, M, K, Q;
    static char[][] map;
    static int[][] dp;
    static int[][] dir;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dir = new int[K+2][2];
        dir[0] = new int[]{1, 0};
        dir[1] = new int[]{0, 1};
        for (int i=1; i<=K; i++) {
            dir[i + 1] = new int[] { i, i };
        }

        map = new char[N][M];

        for (int i=0; i<N; i++) {
            map[i] = br.readLine().toCharArray();
        }

        dp = new int[N][M];
        Q = Integer.parseInt(br.readLine());

        for (int i=0; i<Q; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken())-1;
            sb.append(dfs(r, c) == 1 ? "First" : "Second").append("\n");
        }


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static int dfs(int r, int c) {
        if (dp[r][c] != NOT_YET) {
            return dp[r][c];
        }

        dp[r][c] = SECOND;
        for (int[] d : dir) {
            int nextR = r + d[0];
            int nextC = c + d[1];
            if (canGo(nextR, nextC)) {
                if (dfs(nextR, nextC) == SECOND) {
                    dp[r][c] = FIRST;
                    break;
                }
            }
        }

        return dp[r][c];
    }

    public static boolean canGo(int r, int c) {
        return r>=0 && c>=0 && r<N && c<M && map[r][c] != WALL;
    }

}
