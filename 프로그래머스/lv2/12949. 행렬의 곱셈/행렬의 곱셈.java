class Solution {
    public int[][] solution(int[][] arr1, int[][] arr2) {
        int n1 = arr1.length;
        int n2 = arr1[0].length;
        int m1 = arr2.length;
        int m2 = arr2[0].length;
        
        int N = n1;
        int M = m2;
        
        if (n2 != m1) {
            int[][] temp = arr1;
            arr1 = arr2;
            arr2 = temp;
            
            N = m1;
            M = n2;
        }
        
        
        int mid = arr1[0].length;
        
        int[][] answer = new int[N][M];
        
        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                for (int k=0; k<mid; k++) {
                    answer[i][j] += arr1[i][k]*arr2[k][j];
                }
            }
        }
        return answer;
    }
}