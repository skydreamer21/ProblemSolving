import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static final int IMPOSSIBLE = -2;
    private static final int NOT_EXIST = -1;
    static int N;
    static int[] arr;
    static StringBuilder sb = new StringBuilder();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        nextPerm();
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static void nextPerm() {
        int firstSwapIdx = findFirstSwap();
        if (firstSwapIdx == NOT_EXIST) {
            sb.append(NOT_EXIST);
            return;
        }
        int secondSwapIdx = findSecondSwapIdx(firstSwapIdx);
        swap(firstSwapIdx, secondSwapIdx);
        reverse(firstSwapIdx + 1);
        for (int i = 0; i < N; i++) {
            sb.append(arr[i]).append(" ");
        }
    }
    
    private static int findFirstSwap() {
        for (int i = N - 2; i >= 0; i--) {
            if (arr[i] < arr[i + 1]) {
                return i;
            }
        }
        return NOT_EXIST;
    }
    
    private static int findSecondSwapIdx(int firstSwapIdx) {
        for (int i = N - 1; i > firstSwapIdx; i--) {
            if (arr[i] > arr[firstSwapIdx]) {
                return i;
            }
        }
        return IMPOSSIBLE;
    }
    
    private static void reverse(int start) {
        int left = start;
        int right = N - 1;
        while (left < right) {
            swap(left++, right--);
        }
    }
    
    private static void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}