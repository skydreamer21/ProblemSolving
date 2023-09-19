import java.util.*;

class Solution {
    static int N;
    static int[][] dp;
    static int[][] tri;
    
    public int solution(int[][] triangle) {
        tri = triangle;
        N = triangle.length;
        dp = new int[N][];
        for (int i=0; i<N; i++) {
            dp[i] = new int[i+1];
            Arrays.fill(dp[i], -1);
        }
        
        int answer = 0;
        for (int i=0; i<N; i++) {
            answer = Math.max(answer, solveDP(N-1, i));
        }
        
        return answer;
    }
    
    public int solveDP(int depth, int col) {
        if (depth == 0) {
            return tri[0][0];
        }
        
        if (dp[depth][col] != -1) {
            return dp[depth][col];
        }
        
        dp[depth][col] = 0;
        if (col == 0) {
            dp[depth][col] = solveDP(depth-1, 0);
        } else if (col == depth) {
            dp[depth][col] = solveDP(depth-1, depth-1);
        } else {
            for (int i=-1; i<=0; i++) {
                dp[depth][col] = Math.max(dp[depth][col], solveDP(depth-1, col+i));
            }
        }
        dp[depth][col] += tri[depth][col];
        return dp[depth][col];
    }
    
    
}