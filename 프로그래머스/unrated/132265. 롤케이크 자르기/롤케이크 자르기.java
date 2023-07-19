import java.util.*;

class Solution {
    static final int ME = 0;
    static final int YOU = 1;
    static final int TOPPING_TYPE_CNT = 10000;
    
    static int N;
    static int[][] toppingTb;
    static Set<Integer>[] toppingTypes;
    static int answer = 0;
    static int[] toppings;
    
    public int solution(int[] t) {
        toppings = t;
        N = t.length;
        initToppingTypes();
        
        toppingTb = new int[2][TOPPING_TYPE_CNT+1];
        initToppingTb();
        
        search();
        
        return answer;
    }
    public void initToppingTypes() {
        toppingTypes = new Set[2];
        toppingTypes[ME] = new HashSet<>();
        toppingTypes[YOU] = new HashSet<>();
    }
    
    public void initToppingTb() {
        for (int t : toppings) {
            if (toppingTb[YOU][t] == 0) {
                toppingTypes[YOU].add(t); // topping을 가지고 있다.
            }
            toppingTb[YOU][t]++;
        }
    }
    
    public void search() {
        for (int i=0; i<N-1; i++) {
            int nowTopping = toppings[i];
            // System.out.printf("내가 0번째부터 %d번째까지 가질때\n", i);
            removeTopping(toppings[i], YOU);
            addTopping(toppings[i], ME);
            if (hasSameTopping()) {
                answer++;
            }
            // System.out.printf("ME TYPE CNT : %d, YOU TYPE CNT : %d\n", toppingTypes[ME].size(), toppingTypes[YOU].size());
        }
    }
    
    public void removeTopping(int topping, int who) {
        if (toppingTb[who][topping] > 0) {
            if (toppingTb[who][topping] == 1) {
                toppingTypes[who].remove(topping);
            }
            toppingTb[who][topping]--;
        }
    }
    
    public void addTopping(int topping, int who) {
        toppingTb[who][topping]++;
        toppingTypes[who].add(topping);
    }
    
    public boolean hasSameTopping() {
        return toppingTypes[ME].size() == toppingTypes[YOU].size();
    }
}