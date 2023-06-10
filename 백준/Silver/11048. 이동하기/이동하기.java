import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int[][] DIR = {{0, -1}, {-1, 0}, {-1, -1}};
    static final int NOT_YET = -1;
    static int N, M;
    static int[][] map;
    static int[][] dp;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        map = new int[N][M];
        dp = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], NOT_YET);
        }
        
        sb.append(solveDP(N - 1, M - 1));
    
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static int solveDP(int x, int y) {
        if (dp[x][y] != NOT_YET) {
            return dp[x][y];
        }
        
        dp[x][y] = 0;
        for (int[] d : DIR) {
            int nextX = x + d[0];
            int nextY = y + d[1];
            if (isInRange(nextX, nextY)) {
                dp[x][y] = Math.max(dp[x][y], solveDP(nextX, nextY));
            }
        }
        dp[x][y] += map[x][y];
        return dp[x][y];
    }
    
    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < M;
    }
}