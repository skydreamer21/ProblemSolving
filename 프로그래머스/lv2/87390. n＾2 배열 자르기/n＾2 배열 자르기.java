class Solution {
    static int N;
    
    public int[] solution(int n, long left, long right) {
        N = n;
        int len = (int) (right - left) + 1;
        int[] answer = new int[len];
        
        for (int i=0; i<len; i++) {
            answer[i] = solve(left + i);
        }
        return answer;
    }
    
    public static int solve(long lineIdx) {
        int row = (int)(lineIdx / N) + 1;
        int col = (int)(lineIdx % N) + 1;
        return Math.max(row, col);
    }
}