import java.util.*;

class Solution {
    static final int JOIN = -1;
    
    static int[][] users;
    static int[] emoticons;
    static int[] discounts;
    static int N, M;
    static int[] answer = new int[2];
    
    public int[] solution(int[][] u, int[] e) {
        users = u;
        emoticons = e;
        N = u.length;
        M = e.length;
        discounts = new int[M];
        dfs(0);
        
        return answer;
    }
    
    public void dfs(int cnt) {
        if (cnt == M) {
            // System.out.printf("할인 정보 : %s\n", Arrays.toString(discounts));
            int[] result = findResult();
            // System.out.printf("결과 : %s\n", Arrays.toString(result));
            if (result[0] > answer[0]) {
                answer[0] = result[0];
                answer[1] = result[1];
            } else if (result[0] == answer[0] && result[1] > answer[1]) {
                answer[1] = result[1];
            }
            return;
        }
        
        for (int i=10; i<=40; i+=10) {
            discounts[cnt] = i;
            dfs(cnt+1);
        }
    }
    
    public int[] findResult() {
        int[] result = new int[2];
        
        for (int[] user : users) {
            int userResult = findUserResult(user);
            if (userResult == JOIN) {
                result[0]++;
            } else {
                result[1] += userResult;
            }
        }
        
        return result;
    }
    
    public int findUserResult(int[] user) {
        int moneyUsed = 0;
        
        for (int i=0; i<M; i++) {
            if (discounts[i] < user[0]) continue;
            moneyUsed += emoticons[i] * (100 - discounts[i]) / 100;
            if (moneyUsed >= user[1]) return JOIN;
        }
        
        return moneyUsed;
    }
}