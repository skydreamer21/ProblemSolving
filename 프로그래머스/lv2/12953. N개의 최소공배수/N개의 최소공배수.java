class Solution {
    public int solution(int[] arr) {
        int lcmNum = arr[0];
        for (int i=1;i<arr.length;i++) {
            lcmNum = lcm(lcmNum, arr[i]);
        }
        return lcmNum;
    }
    
    public int gcd(int big, int small) {
        return small == 0 ? big : gcd(small, big%small);
    }
    
    public int lcm(int a, int b) {
        int big = Math.max(a,b);
        int small = Math.min(a,b);
        return a*b/gcd(big, small);
    }
}