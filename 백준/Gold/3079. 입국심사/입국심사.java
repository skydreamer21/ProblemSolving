import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[] times;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        times = new int[N];
        for (int i=0;i<N;i++) times[i] = Integer.parseInt(br.readLine());
        sb.append(BS_LowerBound(M));

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    public static long BS_LowerBound (int key) {
        long lo = 0;
        long hi = (long) Math.pow(10,18);
        long mid;

        while(lo<hi) {
            mid = (lo+hi)/2;
            if (!isPersonPossible(mid, key)) lo = mid+1;
            else hi = mid;
        }
        return lo;
    }

    public static boolean isPersonPossible (long time, int person) {
        long capacity = 0;
        for (int i=0;i<N;i++) {
            capacity += time/times[i];
            if (capacity>=person) return true;
        }
        return false;
    }
}