import java.util.*;

class Solution {
    static Map<String, Integer> clothMap;
    
    public int solution(String[][] clothes) {
        clothMap = new HashMap<>();
        for (String[] cloth : clothes) {
            if (!clothMap.containsKey(cloth[1])) {
                clothMap.put(cloth[1], 0);
            }            
            clothMap.replace(cloth[1], clothMap.get(cloth[1])+1);
        }
        
        int answer = 1;
        for (String cloth : clothMap.keySet()) {
            answer *= (clothMap.get(cloth)+1);
        }
        return answer-1;
    }
}