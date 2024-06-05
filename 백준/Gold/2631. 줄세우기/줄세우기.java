import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
    static final int EMPTY = 0;
    static int N;
    static int[] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        int lis = LIS();
        sb.append(N - lis);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    public static int LIS() {
        int[] memo = new int[N];
        int cnt = 0;
        for (int i=0; i<N; i++) {
            boolean isNumberAdded = binarySearch(memo, cnt, arr[i]);
            if (isNumberAdded) {
                cnt++;
            }
        }
        return cnt;
    }

    // upper bound
    private static boolean binarySearch(int[] src, int length, int target) {
        int lo = 0;
        int hi = length;
        int mid;

        while (lo < hi) {
            mid = (lo + hi) / 2;

            if (target > src[mid]) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        boolean hasAdded = src[lo] == EMPTY;
        src[lo] = target;
        return hasAdded;
    }

}