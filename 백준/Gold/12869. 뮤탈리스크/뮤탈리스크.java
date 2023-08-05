import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static final int[] totalAtkPowers = {9, 3, 1};
    static final int SIX_BITS_ON = (1 << 6) - 1;
    static int[] atkPowers;
    static int N;
    static int scvs;
    static List<int[]> atkCase = new ArrayList<>();
    static int[] caseTemp;
    static boolean[] visited;
    static Map<Integer, Integer> dp = new HashMap<>();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        atkPowers = new int[N];
        System.arraycopy(totalAtkPowers, 0, atkPowers, 0, N);

        st = new StringTokenizer(br.readLine());
        for (int i=0; i<N; i++) {
            // i번째 scv에 체력 배정
            scvs = set(scvs, Integer.parseInt(st.nextToken()), i);
        }

        // make perm
        caseTemp = new int[N];
        visited = new boolean[3];
        makeCase(0);

        int answer = solveDP(scvs);

        sb.append(answer);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static int set(int scvs, int hp, int idx) {
        return (scvs & ~(SIX_BITS_ON << (idx * 6))) | (hp << (idx * 6));
    }

    private static int get(int scvs, int idx) {
        return (scvs >> (idx * 6)) & SIX_BITS_ON;
    }

    private static void printIn(int scvs) {
        System.out.print("[IN] ");
        for (int i=0; i<N; i++) {
            System.out.printf("scv%d : %d, ", i + 1, get(scvs, i));
        }
        System.out.println();
    }

    private static void printOut(int scvs, String msg) {
        System.out.printf("[OUT - %s] ", msg);
        for (int i=0; i<N; i++) {
            System.out.printf("scv%d : %d, ", i + 1, get(scvs, i));
        }
        System.out.println();
    }

    private static int solveDP(int scvs) {
//        printIn(scvs);

        if (scvs == 0) {
//            printOut(scvs, "all scv dead");
            return 0;
        }

        if (dp.containsKey(scvs)) {
//            printOut(scvs, "already found");
            return dp.get(scvs);
        }

        int dpValue = Integer.MAX_VALUE;
        int dpTemp;
        for (int[] atk : atkCase) {
            dpTemp = scvs;
            for (int i=0; i<N; i++) {
                dpTemp = set(dpTemp, afterAttack(get(dpTemp, i), atk[i]), i);
            }

            if (dpTemp == scvs) continue;
            dpValue = Math.min(dpValue, solveDP(dpTemp));
        }
        dpValue += 1;
        dp.put(scvs, dpValue);
//        printOut(scvs, "for end");
        return dpValue;
    }

    private static int afterAttack(int hp, int power) {
        return Math.max(hp - power, 0);
    }


    public static void makeCase(int cnt) {
        if (cnt == N) {
            int[] copy = new int[N];
            System.arraycopy(caseTemp, 0, copy, 0, N);
            atkCase.add(copy);
            return;
        }

        for (int i=0; i<N; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            caseTemp[cnt] = atkPowers[i];
            makeCase(cnt + 1);
            visited[i] = false;
        }
    }
}