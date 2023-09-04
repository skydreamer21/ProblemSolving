import java.util.*;

class Solution {
    static int N;
    static int[] elements;
    static int[] prefixSum;
    
    public int solution(int[] e) {
        N = e.length;
        elements = e;
        prefixSum = new int[N];
        prefixSum[0] = elements[0];
        
        for (int i=1; i<N; i++) {
            prefixSum[i] = prefixSum[i-1] + elements[i];    
        }
        
        Set<Integer> sums = new HashSet<>();
        int totalSum = prefixSum[N-1];
        sums.add(totalSum);
        
        for (int len=1; len<=N/2; len++) {
            // System.out.printf("len : %d\n", len);
            for (int i=0; i<N; i++) {
                int smallLenSum = sum(i, len);
                // System.out.printf("first : %d, second : %d\n", smallLenSum, totalSum - smallLenSum);
                sums.add(smallLenSum);
                sums.add(totalSum - smallLenSum);
            }
        }
        
        return sums.size();
    }
    
    public int rangeSum(int startIdx, int endIdx) {
        if (startIdx == 0) {
            return prefixSum[endIdx];
        }
        return prefixSum[endIdx] - prefixSum[startIdx-1];
    }
    
    public int sum(int startIdx, int length) {
        if (startIdx + length <= N) {
            return rangeSum(startIdx, startIdx+length-1);
        }
        
        int firstPartLength = N - startIdx;
        int secondPartLength = length - firstPartLength;
        return rangeSum(startIdx, N-1) + rangeSum(0, secondPartLength-1);
    }
}