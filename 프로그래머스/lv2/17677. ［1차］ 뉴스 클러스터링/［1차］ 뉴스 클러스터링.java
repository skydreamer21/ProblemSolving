import java.util.*;

class Solution {
    static final int MUL = 65536;
    
    public int solution(String s1, String s2) {
        String str1 = s1.toLowerCase();
        String str2 = s2.toLowerCase();
        
        Map<String, Integer> target1 = createTargetArr(str1);
        Map<String, Integer> target2 = createTargetArr(str2);
        
        return compareSet(target1, target2);
    }
    
    public Map<String, Integer> createTargetArr(String str) {
        Map<String, Integer> target = new HashMap<>();
        
        char[] strArr = str.toCharArray();
        int len = strArr.length;
        for (int i=0; i<len-1; i++) {
            if (!Character.isAlphabetic(strArr[i])) {
                continue;
            } else if (!Character.isAlphabetic(strArr[i+1])) {
                i++;
                continue;
            } else {
                String part = String.valueOf(strArr, i, 2);
                if (!target.containsKey(part)) {
                    target.put(part, 0);
                }
                target.replace(part, target.get(part)+1);
            }
        }
        
        return target;
    }
    
    public int compareSet(Map<String, Integer> set1, Map<String, Integer> set2) {
        int unionCnt = 0;
        int intersectCnt = 0;
        for (String word : set1.keySet()) {
            if (set2.containsKey(word)) {
                unionCnt += Math.max(set1.get(word), set2.get(word));
                intersectCnt += Math.min(set1.get(word), set2.get(word));
            } else {
                unionCnt += set1.get(word);
            }
        }
        
        for (String word : set2.keySet()) {
            if(!set1.containsKey(word)) {
                unionCnt += set2.get(word);
            }
        }
        
        // System.out.printf(" union : %d\n", unionCnt);
        // System.out.printf(" intersectCnt : %d\n", intersectCnt);
        
        if (unionCnt == 0) return 1 * MUL;
        
        double figure = 1.0 * intersectCnt / unionCnt;
        return (int)(figure * MUL);
    }
}