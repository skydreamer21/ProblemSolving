import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N, total;
    static int[] requests;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        requests = new int[N];
        st = new StringTokenizer(br.readLine());
        int totalRequest = 0;
        int maxRequest = 0;
        for (int i = 0; i < N; i++) {
            requests[i] = Integer.parseInt(st.nextToken());
            totalRequest += requests[i];
            maxRequest = Math.max(maxRequest, requests[i]);
        }
        total = Integer.parseInt(br.readLine());
        
        if (totalRequest <= total) {
            System.out.println(maxRequest);
            return;
        }
        
        int answer = binarySearch();
        sb.append(answer);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static int binarySearch() {
        int lo = 0;
        int hi = total+1;
        int mid;
        
        while (lo < hi) {
            mid = (lo + hi) / 2;
            if (isPossible(mid)) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo - 1;
    }
    
    private static boolean isPossible(int budget) {
        int sum = 0;
        for (int request : requests) {
            sum += Math.min(request, budget);
            if (sum > total) {
                return false;
            }
        }
        return true;
    }
}