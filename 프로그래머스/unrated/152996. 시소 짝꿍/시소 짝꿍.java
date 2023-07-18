import java.util.*;

class Solution {
    static final int[] mul = {2, 3, 4};
    
    static int[] weights;
    static Map<Integer, Integer> weightTable;
    static long answer = 0;
    
    public long solution(int[] w) {
        weights = new int[1001];
        weightTable = new HashMap<>();
        initWT(w);
        
        // weightTable.entrySet().stream().forEach((entry) -> {
        //     System.out.printf("(%d, %d)\n", entry.getKey(), entry.getValue());
        // });
        
        for (int i=0; i<=1000; i++) {
            if (weights[i] > 0) {
                // System.out.printf("%d -> %d\n", i, weights[i]);
                answer += combi2(weights[i]);
                // System.out.printf("%d에서 자기들끼리 추가 : %d\n", i, combi2(weights[i]));
                for (int j=i+1; j<=1000; j++) {
                    if (weights[j] == 0) continue;
                    if (isPossible(i, j)) {
                        answer += (long)weights[i] * weights[j];
                        // System.out.printf("%d 와 %d 서로 짝 맞음 --> %d쌍\n", i, j,  weights[i] * weights[j]);
                    }
                }
            }
        }
        
        return answer;
    }
    
    public void initWT(int[] ws) {
        for (int w : ws) {
            weights[w]++;
        }
    }
    
    public long combi2(int n) {
        if (n < 2 ) return 0;
        return (long)n*(n-1)/2;
    }
    
    public boolean isPossible(int w1, int w2) {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (w1 * mul[i] == w2 * mul[j]) {
                    return true;
                }
            }
        }
        return false;
    }
    
}