import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int K, N;
    static int maxLine=0;
    static int[] lines;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        lines = new int[K];
        for (int i=0;i<K;i++) {
            lines[i] = Integer.parseInt(br.readLine());
            maxLine = Math.max(maxLine, lines[i]);
        }

        sb.append(getMaxLength(N));

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    public static long getMaxLength (int key) {
        long lo = 0;
        long hi = (long) maxLine+1;
        long mid;

        while(lo<hi) {
            mid = (lo+hi)/2;
//            System.out.printf("lo : %d, mid : %d, hi : %d\n",lo,mid,hi);
            if(getMaxLines(mid)>=key) lo = mid+1;
            else hi = mid;
        }
        return lo-1;
    }

    public static int getMaxLines(long length) {
        int line = 0;
        for (int i=0;i<K;i++) line+=lines[i]/length;
        return line;
    }
}