import java.util.*;

class Solution {
    static Map<Integer, Integer> tanMap = new HashMap<>();
    static int N, M;
    static int[] tanger;
    
    static class TangerCount implements Comparable<TangerCount>{
        int tanger;
        int count;
        
        public TangerCount(int tanger, int count) {
            this.tanger = tanger;
            this.count = count;
        }
        
        @Override
        public int compareTo(TangerCount o) {
            return o.count - this.count;
        }
        
        @Override
        public String toString() {
            return "tanger : " + tanger + ", count : " + count;
        }
    }
    
    public int solution(int k, int[] tangerine) {
        N = tangerine.length;
        M = k;
        tanger = tangerine;
        
        // 1. 원소 개수 map 만들기
        for (int i=0; i<N; i++) {
            if (!tanMap.containsKey(tanger[i])) {
                tanMap.put(tanger[i], 0);
            }
            tanMap.replace(tanger[i], tanMap.get(tanger[i])+1);
        }
        
        // 2. 맵에 저장된 정보들을 정렬가능한 배열로 변환
        TangerCount[] countInfos = new TangerCount[tanMap.size()];
        int num = 0;
        for (Map.Entry<Integer, Integer> entry : tanMap.entrySet()) {
            countInfos[num++] = new TangerCount(entry.getKey(), entry.getValue());
        }
        
        Arrays.sort(countInfos);
        
        // for (int i=0; i<num; i++) {
        //     System.out.println(countInfos[i]);
        // }
        
        int count = 0;
        int types = 0;
        
        // 마지막 파트@!!
        for (int i=0; i<num; i++) {
            if (count >= M) break;
            count += countInfos[i].count;
            types++;
        }
        
        return types;
    }
}