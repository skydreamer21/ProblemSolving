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
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);
        int answer = 0;
        if (N >= 3) {
            int mid = N/2;
            long sum = Long.MAX_VALUE;
            for (int i = -1; i <= 1; i++) {
                long dist = getTotalDist(mid + i);
                if (dist < sum) {
                    sum = dist;
                    answer = mid + i;
                }
            }
        }

        sb.append(arr[answer]);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static long getTotalDist(int house) {
        long sum = 0;
        for (int i = 0; i < N; i++) {
            sum += Math.abs(arr[i] - arr[house]);
        }
        return sum;
    }
}
