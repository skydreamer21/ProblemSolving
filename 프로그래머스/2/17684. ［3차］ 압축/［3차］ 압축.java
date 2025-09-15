import java.util.*;

class Solution {
    static final int NOT_FOUND = -1;
    
    static class Index {
        Map<String, Integer> index;
        int next;
        
        public Index() {
            index = new HashMap<>();
            next = 1;
            for (int i=0; i<26; i++) {
                index.put(String.valueOf((char)('A'+i)), next++);
            }
        }
        
        public int find(String str) {
            if (index.containsKey(str)) {
                return index.get(str);
            }
            return NOT_FOUND;
        }
        
        public void add(String str) {
            index.put(str, next++);
        }
        
        public boolean contains(String str) {
            return index.containsKey(str);
        }
        
    }
    
    static class Zipper {
        Index index;
        String target;
        List<Integer> result;
        int len;
        
        public Zipper(String target) {
            this.index = new Index();
            this.target = target;
            len = target.length();
            result = new ArrayList<>();
        }
        
        private int findPossibleMaxFrom(int start) {
            for (int i=start; i<len; i++) {
                String sub = target.substring(start, i+1);
                if (!index.contains(sub)) {
                    return i;
                }
            }
            return len;
        }
        
        private int zipFrom(int start) {
            int end = findPossibleMaxFrom(start);
            String part = target.substring(start, end);
            result.add(index.find(part));
            if (end < len) {
                index.add(target.substring(start, end+1));
            }
            return end;
        }
        
        public void zip() {
            int now = 0;
            while(now < len) {
                now = zipFrom(now);
            }
        }
        
        public int[] getResult() {
            return result.stream().mapToInt(Integer::intValue).toArray();
        }
        
    }
    public int[] solution(String msg) {
        Zipper zipper = new Zipper(msg);
        zipper.zip();
        return zipper.getResult();
    }
}