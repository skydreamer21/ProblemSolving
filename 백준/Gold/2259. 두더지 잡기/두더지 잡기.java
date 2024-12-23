import java.util.*;
import java.io.*;

public class Main {
    static final int NOT_YET = 0;

    static int N, S;
    static Mole[] moles;
    static int[] dp;

    static class Mole implements Comparable<Mole> {
        int x, y, t, index;

        public Mole(int index, int x, int y, int t) {
            this.x = x;
            this.y = y;
            this.t = t;
            this.index = index;
        }

        public boolean isCatchableFrom(int fromX, int fromY, int fromT) {
            if (fromT >= t) return false;
            long capability = (long)S*(t - fromT) * S*(t - fromT);
            int target = ((x - fromX) * (x-fromX)) + ((y-fromY)*(y-fromY));
            return capability >= target;
        }

        public boolean isCatchableFrom(Mole o) {
            return isCatchableFrom(o.x, o.y, o.t);
        }

        @Override
        public int compareTo(Mole o) {
            return o.t - this.t;
        }

        @Override
        public String toString() {
            return String.format("[%d] (%d, %d) at %ds", index, x, y, t);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        moles = new Mole[N];
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            moles[i] = new Mole(i,
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()));
        }

        Arrays.sort(moles);
        dp = new int[N];

        int ans = 0;

        // 시작점으로부터 갈수 있는 곳 찾기
        for (int i=0; i<N; i++) {
            if (moles[i].isCatchableFrom(0, 0, 0)) {
                ans = Math.max(ans, solveDP(i));
            }
        }

        sb.append(ans);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static int solveDP(int m) { // index : m 두더지를 잡았을 때부터 잡을 수 있는 최대 마리 수
        if (dp[m] != NOT_YET) {
            return dp[m];
        }

        int idx = 0;
        while (moles[idx].t > moles[m].t) { // 현재 두더지가 나타나는 시간보다 클 때만
            if (moles[idx].isCatchableFrom(moles[m])) {
                dp[m] = Math.max(dp[m], solveDP(idx));
            }
            idx++;
        }
        dp[m] += 1; // 현재 두더지 잡은것 추가
        return dp[m];
    }

}