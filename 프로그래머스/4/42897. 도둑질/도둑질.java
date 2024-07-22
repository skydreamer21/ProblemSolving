import java.util.*;

// 1. 마지막 집을 털었을 때 + N-2 ~ 1
// 2. 마지막 집을 안털었을 때 + N-1 ~ 0

class Solution {
    static final int NOT_YET = -1;
    
    static int[] money;
    static int[] dp;
    static int N;
    static int max = -1;
    
    public int solution(int[] m) {
        money = m;
        N = m.length;
        
        if (N == 3) {
            return Math.max(money[0], Math.max(money[1], money[2]));
        }
        
        // 첫번째 집을 털때
        max = solveDP(2, N-2) + money[0]; // 2, 2
        
        // 첫번째 집을 안털때
        max = Math.max(max, solveDP(1, N-1)); // 1, 3
        
        return max;
    }
    
    public int solveDP(int s, int e) {
        if (s > e) {
            throw new IllegalArgumentException(String.format("s: %d, e: %d", s, e));
        }
        int len = e-s+1;
        dp = new int[len];
        int idx = 0;
        
        // 초기값 설정
        dp[0] = money[s];
        if (len <= 1) return dp[0];
        dp[1] = Math.max(money[s], money[s+1]);
        if (len <= 2) return dp[1];
        
        for (int i=2; i<len; i++) {
            dp[i] = Math.max(dp[i-2] + money[s+i], dp[i-1]);
        }
        
        return dp[len-1];
    }
}

