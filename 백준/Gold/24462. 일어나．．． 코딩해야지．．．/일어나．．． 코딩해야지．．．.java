import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static long K;

    static class Alarm {
        long start;
        long step;

        public Alarm(long start, long step) {
            this.start = start;
            this.step = step;
        }

        public long count() {
            long startDiv = start / step;
            long endDiv = K / step;
            return (endDiv - startDiv) + 1;
        }

        public long unionCount(Alarm o) {
            Alarm intersectAlarm = new Alarm(findFirstSame(this, o), lcm(this.step, o.step));
            return this.count() + o.count() - intersectAlarm.count();
        }

        private long gcd(long big, long small) {
            if (small == 0) {
                return big;
            }
            return gcd(small, big%small);
        }

        private long lcm(long big, long small) {
            long gcd = gcd(big, small);
            return big * small / gcd;
        }

        private long findFirstSame(Alarm o1, Alarm o2) {
            long maxStart = Math.max(o1.start, o2.start);
            long lcm = lcm(o1.step, o2.step);
            long div = maxStart % lcm == 0 ? maxStart / lcm : (maxStart / lcm) + 1;
            return div * lcm;

        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        Alarm[] alarms = new Alarm[N];
        for (int i = 0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            alarms[i] = new Alarm(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        long maxCnt = 0;
        int first = -1;
        int second = -1;
        for(int i = 0; i<N; i++) {
            for (int j=i+1; j<N; j++) {
                long cnt = alarms[i].unionCount(alarms[j]);
                if (cnt > maxCnt) {
                    maxCnt = cnt;
                    first = i;
                    second = j;
                }
            }
        }

        sb.append(first+1).append(" ").append(second+1).append("\n");
        sb.append(maxCnt);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
