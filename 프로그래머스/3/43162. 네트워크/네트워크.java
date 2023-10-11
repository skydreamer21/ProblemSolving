import java.util.*;

class Solution {
    static int[] parent;
    
    public int solution(int n, int[][] computers) {
        
        initParent(n);
        
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (computers[i][j] == 1) {
                    union(i, j);
                }
            }
        }
        
        Set<Integer> networkHead = new HashSet<>();
        for (int i=0; i<n; i++) {
            networkHead.add(find(i));
        }
        
        return networkHead.size();
    }
    
    public void initParent(int n) {
        parent = new int[n];
        for (int i=0; i<n; i++) {
            parent[i] = i;
        }
    }
    
    public int find(int a) {
        return parent[a] == a ? a : (parent[a] = find(parent[a]));
    }
    
    public boolean union(int a, int b) {
        a = find(a);
        b = find(b);
        if ( a == b ) return false;
        parent[b] = a;
        return true;
    }
}