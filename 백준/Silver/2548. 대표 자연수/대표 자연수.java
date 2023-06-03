import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] arr;
    static int[] sums;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        arr = new int[N+1];
        sums = new int[N+1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        for (int i = 1; i <= N; i++) {
            sums[i] = sums[i - 1] + arr[i];
        }

        int answer = -1;
        int minDiff = Integer.MAX_VALUE;
        for (int i = 1; i <= N; i++) {
            int diff = calculateDiff(i);
            if (diff < minDiff) {
                answer = arr[i];
                minDiff = diff;
            }
        }

        sb.append(answer);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static int calculateDiff(int i) {
        int beforeDiff = arr[i] * (i-1) - sums[i - 1];
        int afterDiff = prefixSum(i, N) - (arr[i] * (N - i + 1));

        return beforeDiff + afterDiff;
    }

    private static int prefixSum(int start, int end) {
        return sums[end] - sums[start - 1];
    }
}