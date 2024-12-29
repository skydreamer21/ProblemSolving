import java.util.*;
import java.io.*;

public class Main {
    static final int DIV = 1_000_000_007;
    static final int NOT_YET = -1;

    static int N, K;
    static int[] expects;
    static int[] moveDP;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        expects = new int[N+1];
        st = new StringTokenizer(br.readLine());
        for (int i=1; i<=N; i++) {
            expects[i] = Integer.parseInt(st.nextToken());
        }

        moveDP = new int[N+1];
        Arrays.fill(moveDP, NOT_YET);
        moveDP[0] = 1;

        int ans = 0;
        int cur = K;
        for (int i=N; i>=1; i--) {
//            System.out.printf("%d번째 원판 배치\n", i);
            if (cur == expects[i]) continue;

            Set<Integer> set = init();
            set.remove(cur);
            set.remove(expects[i]);

            // next
            cur = set.iterator().next();
            ans = (ans + moveN(i-1)) % DIV;
//            System.out.printf("%d번째 원판 배치 후 ans : %d\n", i, ans);

        }

        sb.append(ans);


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static int moveN(int n) {
//        System.out.printf("[IN] n : %d\n", n);
        if (moveDP[n] != NOT_YET) {
//            System.out.printf("[OUT - already found] n : %d\n", n);
            return moveDP[n];
        }

        if (n % 2 == 0) {
            moveDP[n] = (int) (((long) moveN(n / 2) * moveN(n/ 2)) % DIV);
        } else {
            moveDP[n] = (int) (((long) 2 * moveN(n-1)) % DIV);
        }
//        System.out.printf("[OUT - all] n : %d, dp[%d] = %d\n", n, n, moveDP[n]);
        return moveDP[n];
    }

    public static Set<Integer> init() {
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        return set;
    }

}