import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int NOT_YET = -1;
    static final int IMPOSSIBLE = -2;

    static int N, C, P;
    static int[][][] dp;
    static Food[] foods;

    static class Food {
        int cheese, potato;

        public Food(int cheese, int potato) {
            this.cheese = cheese;
            this.potato = potato;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());

        foods = new Food[N];

        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            foods[i] = new Food(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        dp = new int[N][C+1][P+1];
        for (int i=0; i<N; i++) {
            for (int j=0; j<=C; j++) {
                Arrays.fill(dp[i][j], NOT_YET);
            }
        }

        int max = 0;
        for(int c = 1; c<=C; c++){
            for (int p = 1; p<=P; p++) {
                max = Math.max(max, dfs(N-1, c, p));
            }
        }

        sb.append(max);


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static int dfs(int n, int uc, int up) {
        if (n == -1) {
            if (uc == 0 && up == 0) {
                return 0;
            } else {
                return IMPOSSIBLE;
            }
        }

        if (dp[n][uc][up] != NOT_YET) {
            return dp[n][uc][up];
        }

        if (uc >= foods[n].cheese && up >= foods[n].potato) {
            int used = dfs(n-1, uc - foods[n].cheese, up - foods[n].potato);
            dp[n][uc][up] = used == IMPOSSIBLE ? IMPOSSIBLE : used + 1;
        }

        int notUsed = dfs(n-1, uc,  up);
        dp[n][uc][up] = notUsed == IMPOSSIBLE ? dp[n][uc][up] : Math.max(dp[n][uc][up], notUsed);

        return dp[n][uc][up];
    }

}
