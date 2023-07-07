import java.util.*;

class Solution {
    static final int DART = 0;
    static final int SINGLE = 1;
    static final int INF = Integer.MAX_VALUE;
    static int[][] dp;
    static int[] answer;
    
    public int[] solution(int target) {
        int len = Math.max(target, 60);
        dp = new int[len+1][2];
        initDP();
        
        if (target <= 60) {
            return dp[target];
        }
        
        for (int i=61; i<=target; i++) {
            dp[i][DART] = INF;
            dp[i][SINGLE] = 0;
            
            int dDart = -1;
            int dSingle = -1;
            
            for (int j=1; j<=60; j++) {
                if (j > i) break;
                
                int count = dp[i-j][DART] + dp[j][DART];
                int single = dp[i-j][SINGLE] + dp[j][SINGLE];
                
                if (count < dp[i][DART]) {
                    dp[i][DART] = count;
                    dp[i][SINGLE] = single;
                } else if (count == dp[i][DART]) {
                    dp[i][SINGLE] = Math.max(dp[i][SINGLE], single);
                }
            }
        }
        
        return dp[target];
    }
    
    public static void initDP() {
        for (int i=1; i<=60; i++) {
            dp[i][DART] = checkCountUnder60(i);
            dp[i][SINGLE] = checkSingleBull(i);
        }
    }
    
    public static int checkCountUnder60(int num) {
        if (num == 0) return 0;
        if (num % 3 == 0) return 1; // 3의 배수는 1
        if (num <= 20) return 1; // 20 이하의 숫자는 1
        if  (num <= 40) {
            if (num % 2 == 0) return 1; // 21 ~ 40 중 짝수는 1
            return 2; // 짝수가 아니고 3의 배수가 아닌 수는 2
        }
        if (num < 50) return 2; // 41 ~ 49 중 3의 배수가 아닌 수는 2
        if (num == 50) return 1;
        return 2; // 51 ~ 60 중 3의 배수가 아닌 수는 2
    }

    public static int checkSingleBull(int num) {
        if (num == 0) return 0;
        if (num == 50) return 1;
        if (num <= 20) return 1;
        if  (num <= 40) {
            if (num % 2 == 0 || num % 3 == 0) return 0; // 21 ~ 40 중 짝수는 1
            return 2; // 짝수가 아니고 3의 배수가 아닌 수는 2
        }
        if (num < 50) {
            if (num % 3 == 0) return 0;
            else return 1;
        }
        else {
            if (num % 3 == 0) return 0;
            else return 2;
        }
    }
}