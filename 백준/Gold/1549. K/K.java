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
    static long[] prefixSum;
    static int ansLen = -1;
    static long min = Long.MAX_VALUE;

    static class Number implements Comparable<Number> {
        int i;
        long num;

        public Number(int i,  long num) {
            this.i  = i;
            this.num = num;
        }

        public boolean isComparable(Number o, int n) {
            return Math.abs(this.i - o.i) >= n;
        }

        public long diff(Number o) {
            return Math.abs(this.num - o.num);
        }

        @Override
        public int compareTo(Number o) {
            return Long.compare(this.num, o.num);
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        prefixSum = new long[N+1];
        for (int i = 1; i<=N; i++) {
            int num = Integer.parseInt(st.nextToken());
            prefixSum[i] = prefixSum[i-1] + num;
        }

        for (int i = 1; i<=N/2; i++) {
            Number[] sums = new Number[N-i+1];
            boolean[] visited = new boolean[N-i+1];
            for (int j=0; j<N-i+1; j++) {
                sums[j] = new Number(j, sumNFrom(j+1, i));
            }
            Arrays.sort(sums);
            long tempMin = Long.MAX_VALUE;
            for (int j=1; j<N-i+1; j++) {
                long diff = -1;

                for (int k=1; k<=i; k++) {
//                    System.out.printf("(%d, %d, %d)\n", i, j, k);
                    if (j - k < 0 || visited[j-k]) break;
                    diff = sums[j].diff(sums[j-k]);
                    if (diff > min) break;
                    if (!sums[j].isComparable(sums[j-k], i)) continue;
//                    System.out.printf("cal (%d, %d, %d)\n", i, j, k);
                    tempMin = Math.min(diff, tempMin);
                    visited[j-k] = true;
                    break;
                }
            }
//            System.out.printf("길이 : %d, 최솟값 : %d\n", i, tempMin);

            if (tempMin < min) {
                ansLen = i;
                min = tempMin;
            } else if (tempMin == min) {
                ansLen = i;
            }
        }

        sb.append(ansLen).append("\n").append(min);


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    static long sumNFrom(int from, int cnt) {
        return prefixSum[from+cnt-1] - prefixSum[from-1];
    }

}
