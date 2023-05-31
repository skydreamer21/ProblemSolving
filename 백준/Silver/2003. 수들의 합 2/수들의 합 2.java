import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[] prefixSum;
    static HashMap<Integer,Integer> previousSums;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        prefixSum = new int[N];
        st = new StringTokenizer(br.readLine());
        prefixSum[0] = Integer.parseInt(st.nextToken());
        for (int i = 1; i < N; i++) {
            prefixSum[i] = prefixSum[i-1] + Integer.parseInt(st.nextToken());
        }
        
        previousSums = new HashMap<>();
        previousSums.put(0, 1);
        
        int answer = 0;
        for (int i = 0; i < N; i++) {
            int target = prefixSum[i] - M;
            if (previousSums.containsKey(target)) {
                answer += previousSums.get(target);
            }
            addPreviousSum(prefixSum[i]);
        }
        
        sb.append(answer);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static void addPreviousSum(int sum) {
        if (previousSums.containsKey(sum)) {
            previousSums.replace(sum, previousSums.get(sum) + 1);
        } else {
            previousSums.put(sum, 1);
        }
    }
}