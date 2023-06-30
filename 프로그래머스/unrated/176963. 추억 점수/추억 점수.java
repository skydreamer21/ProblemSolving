import java.util.*;

class Solution {
    static int N, M;
    static Map<String, Integer> yearnTable;
    
    public int[] solution(String[] name, int[] yearning, String[][] photo) {
        N = name.length;
        M = photo.length;
        initTable(name, yearning);
        int[] answer = new int[M];
        
        for (int i=0; i<M; i++) {
            int len = photo[i].length;
            int score = 0;
            for (int j=0; j<len; j++) {
                score += yearnTable.getOrDefault(photo[i][j], 0);
            }
            answer[i] = score;
        }
        
        return answer;
    }
    
    public static void initTable(String[] name, int[] yearning) {
        yearnTable = new HashMap<>();
        for (int i=0; i<N; i++) {
            yearnTable.put(name[i], yearning[i]);
        }
    }
}