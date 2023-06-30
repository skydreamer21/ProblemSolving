import java.util.*;

class Solution {
    static final int DIA = 0;
    static final int IRON = 1;
    static final int STONE = 2;
    
    static final int[][] costTable = {
        {1, 1, 1}, // dia
        {5, 1, 1}, // iron
        {25, 5, 1} // stone
    };
    
    static Map<String, Integer> mineralIndexTable;
    
    static int N, M;
    static int[] prickOrder;
    static int minCost = Integer.MAX_VALUE;
    static String[] mines;
    static int[] pickList;
    
    public int solution(int[] picks, String[] minerals) {
        mines = minerals;
        pickList = picks;
        initMineralIndexTable();
        
        N = minerals.length;
        M = N % 5 == 0 ? N/5 : N/5 + 1;
        
        int totalPicks = picks[0] + picks[1] + picks[2];
        
        M = Math.min(totalPicks, M);
        // System.out.printf("M : %d\n", M);
        
        dfs(0, 0);
        return minCost;
    }
    
    public static void initMineralIndexTable() {
        mineralIndexTable = new HashMap<>();
        
        mineralIndexTable.put("diamond", 0);
        mineralIndexTable.put("iron", 1);
        mineralIndexTable.put("stone", 2);
    }
    
    public static void dfs(int cnt, int cost) {
        // System.out.printf("[IN] cnt : %d, cost : %d\n", cnt, cost);
        if (cnt == M) {
            minCost = Math.min(minCost, cost);
            // System.out.printf("[OUT - find one case] cnt : %d, cost : %d\n", cnt, cost);
            return;
        }
        
        for (int p=0; p<3; p++) {
            if (pickList[p] == 0) continue;
            pickList[p]--;
            // System.out.printf("%d 곡괭이 사용\n", p);
            dfs(cnt+1, cost + calculateCost(cnt, p));
            pickList[p]++;
        }
        // System.out.printf("[OUT - for end] cnt : %d, cost : %d\n", cnt, cost);
    }
    
    public static int calculateCost(int startIdx, int pick) {
        int sum = 0;
        startIdx *= 5;
        for (int i=0; i<5; i++) {
            if (startIdx + i >= N) break;
            int mineIdx = mineralIndexTable.get(mines[startIdx + i]);
            sum += costTable[pick][mineIdx];
        }
        return sum;
    }
}