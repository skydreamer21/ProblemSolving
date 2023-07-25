import java.util.*;

class Solution {
    static int N, M; // 스테이지 수, 사람 수
    static int[] stages;
    static int[] users;
    static int[] passedUsers;
    
    static class Stage implements Comparable<Stage> {
        int stage;
        int numerator;
        int denominator;
        
        public Stage(int stage, int numerator, int denominator) {
            this.stage = stage;
            this.numerator = numerator;
            this.denominator = denominator;
        }
        
        @Override
        public int compareTo(Stage o) {
            if ( this.denominator == 0 || o.denominator == 0 ) {
                if (this.denominator == o.denominator ) {
                    return this.stage - o.stage;
                } else {
                    return o.denominator - this.denominator;
                }
            }
            
            long comp = (long)o.numerator*this.denominator - (long)this.numerator*o.denominator;
            if (comp > 0) return 1;
            if (comp < 0) return -1;
            return this.stage - o.stage;
        }
    }
    
    public int[] solution(int n, int[] s) {
        N = n;
        stages = s;
        M = s.length;
        
        users = new int[n+2];
        passedUsers = new int[n+2];
        
        countUsers();
        countPassedUsers();
        
        Stage[] res = new Stage[N];
        for (int i=1; i<=N; i++) {
            res[i-1] = new Stage(i, users[i], passedUsers[i]);
        }
        
        Arrays.sort(res);
        
        int[] answer = new int[N];
        for (int i=0; i<N; i++) {
            answer[i] = res[i].stage;
        }
        
        // System.out.println(Arrays.toString(users));
        // System.out.println(Arrays.toString(passedUsers));
        return answer;
    }
    
    public void countUsers() {
        for (int stage: stages) {
            users[stage]++;
        }
    }
    
    public void countPassedUsers() {
        passedUsers[N+1] = users[N+1];
        for (int i=N; i>=1; i--) {
            passedUsers[i] = users[i] + passedUsers[i+1];
        }
    }
}