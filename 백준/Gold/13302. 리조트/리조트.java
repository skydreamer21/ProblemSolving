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
    static final int[] prices = {10000, 25000, 37000};
    static final int ONE_DAY = 0;
    static final int THREE_DAY = 1;
    static final int FIVE_DAY = 2;
    private static final int NOT_YET = -1;

    static int N, M;
    static boolean[] isVisitDay;
    static int[][] dp;
    static int minPrice = Integer.MAX_VALUE;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        isVisitDay = new boolean[N+1];
        dp = new int[N+1][40];
        for (int i=0; i<=N; i++) {
            Arrays.fill(dp[i], NOT_YET);
        }
        Arrays.fill(isVisitDay, true);

        if (M > 0) {
            st = new StringTokenizer(br.readLine());
            while (st.hasMoreTokens()) {
                isVisitDay[Integer.parseInt(st.nextToken())] = false;
            }
        }

        sb.append(solveDP(1, 0));

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static int solveDP(int day, int coupon) {
        if (day > N) {
            return 0;
        }

        if (dp[day][coupon] != NOT_YET) {
            return dp[day][coupon];
        }

        dp[day][coupon] = Integer.MAX_VALUE;

        dp[day][coupon] = Math.min(dp[day][coupon], prices[ONE_DAY] + solveDP(findNextVisitDat(day + 1), coupon));
        dp[day][coupon] = Math.min(dp[day][coupon], prices[THREE_DAY] + solveDP(findNextVisitDat(day + 3), coupon+1));
        dp[day][coupon] = Math.min(dp[day][coupon], prices[FIVE_DAY] + solveDP(findNextVisitDat(day + 5), coupon+2));
        if (coupon >= 3) {
            dp[day][coupon] = Math.min(dp[day][coupon], solveDP(findNextVisitDat(day + 1), coupon - 3));
        }

        if (dp[day][coupon] == Integer.MAX_VALUE) {
            System.out.println("뭔가 문제 있음");
        }
        return dp[day][coupon];
    }

    private static int findNextVisitDat(int day) {
        while(day <= N && !isVisitDay[day]) {
            day++;
        }
        return day;
    }
}