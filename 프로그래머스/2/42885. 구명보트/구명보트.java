import java.util.*;

class Solution {
    static int N;
    
    public int solution(int[] people, int limit) {
        Arrays.sort(people);
        N = people.length;
        
        int left = 0;
        int right =  N-1;
        int count = 0;
        
        while (left < right) {
            if (people[left] + people[right] <= limit) {
                left++;
            }
            right--;
            count++;
        }
        if (left == right) count++;
        return count;
    }
}