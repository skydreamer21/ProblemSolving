import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static final int IMPOSSIBLE = -1;
    
    static int N, M;
    static int[] arr;
    static int saveCnt = 0;
    static int answer = IMPOSSIBLE;
    static boolean findAns = false;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        mergeSort(arr, 0, N - 1);
        
        sb.append(answer);
    
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static void mergeSort(int[] arr, int start, int end) {
        if (findAns) {
            return;
        }
        
        if (start >= end) return;
        
        int mid = (start + end) / 2;
        mergeSort(arr, start, mid);
        mergeSort(arr, mid+1, end);
        merge(arr, start, mid, end);
        
    }
    
    private static void merge(int[] arr, int start, int mid, int end) {
        int[] tmp = new int[end - start + 1];
        int left = start;
        int right = mid + 1;
        int t = 0;
        while (left <= mid && right <= end) {
            if (arr[left] <= arr[right]) {
                tmp[t++] = arr[left++];
            } else {
                tmp[t++] = arr[right++];
            }
        }
        
        while (left <= mid) {
            tmp[t++] = arr[left++];
        }
        
        while (right <= end) {
            tmp[t++] = arr[right++];
        }
        
        for (int i = 0; i < tmp.length; i++) {
            saveCnt++;
            arr[start + i] = tmp[i];
            if (saveCnt == M) {
                findAns = true;
                answer = tmp[i];
                break;
            }
        }
    }
}