import java.util.*;

class Solution {
    public int solution(int[] order) {
        int N = order.length;
        Deque<Integer> cv = new ArrayDeque<>();
        
        int cnt = 0;
        int nextOrderIdx = 0;
        boolean canContinue = true;
        for (int i=1; i<=N; i++) {
            // System.out.printf("[New main CV Item] now: %d\n", i);
            if (!canContinue) break;
            boolean hasCorrectBox = true;
            boolean nowTurnBoxLeft = true;
            while(hasCorrectBox && nextOrderIdx < N) {
                // System.out.printf("find %d...\n", order[nextOrderIdx]);
                hasCorrectBox = false;
                if (!cv.isEmpty() && nextOrderIdx < N && cv.peekLast() == order[nextOrderIdx]) {
                    // System.out.printf("[found in sub] sub : %d, target : %d\n", cv.peekLast(), order[nextOrderIdx]);
                    cv.pollLast();
                    cnt++;
                    nextOrderIdx++;
                    hasCorrectBox |= true;
                } else if (order[nextOrderIdx] < i) {
                    // System.out.printf("[[IMPOSSIBLE]] 실어야 하는 것 : %d, 지금 메인 순서 : %d\n", order[nextOrderIdx], i);
                    // 지금 차례인 택배박스가 앞으로 나올 박스의 순서들보다 작을 때
                    canContinue = false;
                    break;
                }
                
                // 현재 메인 컨베이너 벨트를 한번 더 체크
                if (nextOrderIdx < N && i == order[nextOrderIdx]) {
                    // System.out.printf("[found in main] main : %d, target:%d\n", i, order[nextOrderIdx]);
                    cnt++;
                    nextOrderIdx++;
                    hasCorrectBox |= true;
                    nowTurnBoxLeft = false;
                } 
            }
            if (nowTurnBoxLeft) {
                // System.out.printf("now turn box (%d) go in to sub CV\n", i);
                cv.add(i);
            }
        }
        return cnt;
    }
}