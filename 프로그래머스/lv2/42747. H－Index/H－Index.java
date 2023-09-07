import java.util.*;

class Solution {
    public int solution(int[] cit) {
        int N = cit.length;
        int[] count = new int[10001];
        for (int i=0; i<N; i++) {
            count[cit[i]]++;
        }
        
        for (int i=9999; i>=0; i--) {
            count[i] += count[i+1];
        }
        
        int answer = -1;
        for (int i=10000; i>=0; i--) {
            if (count[i] >= i) {
                answer = i;
                break;
            }
        }
        return answer;
    }
}