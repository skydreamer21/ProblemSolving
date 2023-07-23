import java.util.*;

class Solution {
    static int N;
    static int[] cards;
    static boolean[] visited;
    static List<Integer> groups;
    static int groupCnt;
    
    
    public int solution(int[] c) {
        N = c.length;
        cards = c;
        visited = new boolean[N];
        groups = new ArrayList<>();
        
        for (int i=0; i<N; i++) {
            if (visited[i]) continue;
            groupCnt = 0;
            dfs(0, i);
            if (groupCnt > 0) {
                groups.add(groupCnt);    
            }
        }
        
        if (groups.size() < 2) {
            return 0;
        } else {
            Collections.sort(groups, (a,b) -> b-a);
            return groups.get(0) * groups.get(1);
        }
    }
    
    public void dfs(int cnt, int box) {
        if (visited[box]) {
            groupCnt = cnt;
            return;
        }
        visited[box] = true;
        dfs(cnt+1, cards[box]-1);
    }
}