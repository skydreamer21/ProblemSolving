import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static final int EMPTY = 2;
    static final int C = 0;
    static final int V = 1;
    static final int C_TOT = 21;
    static final int V_TOT = 5;
    static final Set<Character> vowels = new HashSet<>(Arrays.asList('A', 'E', 'I', 'O', 'U'));

    static int[] str;
    static boolean hasL = false;
    static int N;
    static long cnt = 0;
    static int emptyCnt = 0;
    static int[] totalCVCnt = new int[2];
    static List<Integer> emptyIdx;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        char[] input = br.readLine().toCharArray();
        N = input.length;
        str = new int[N];
        emptyIdx = new ArrayList<>();


        int idx = 0;
        for (char c : input) {
            if (c == '_') {
                str[idx] = EMPTY;
                emptyIdx.add(idx);
            } else {
                if (vowels.contains(c)) {
                    str[idx] = V;
                } else {
                    if (c == 'L') {
                        hasL = true;
                    }
                    str[idx] = C;
                }
            }
            idx++;
        }

        emptyCnt = emptyIdx.size();

        dfs(0);

        sb.append(cnt);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static void dfs(int depth) {
        if (depth == emptyCnt) {
//             System.out.printf("[LAST] cur : %s, cCnt:%d, vCnt: %d, cal : %d\n", Arrays.toString(str), totalCVCnt[C], totalCVCnt[V], calculate());
            cnt += calculate();
            return;
        }

        Set<Integer> availables = new HashSet<>();
        availables.add(C);
        availables.add(V);

        int targetIdx = emptyIdx.get(depth);
        int[] cvCnt = new int[2];

        int left = Math.max(targetIdx - 2, 0);
        int right = Math.min(left + 2, N - 1);
        for (int i = left; i <= right; i++) {
            if (str[i] == EMPTY) continue;
            cvCnt[str[i]]++;
        }
        if (cvCnt[C] == 2) availables.remove(C);
        if (cvCnt[V] == 2) availables.remove(V);
//         System.out.printf("depth[%d] cur : %s, cvCnt: %s (%d~%d)\n",depth, Arrays.toString(str), Arrays.toString(cvCnt), left,right);
        left++;
        right++;

        // 1. 가능한것 골라내기
        while (left <= targetIdx && right < N) {
            if (str[left-1] != EMPTY) cvCnt[str[left-1]]--;
            if (str[right] != EMPTY) cvCnt[str[right]]++;
            if (cvCnt[C] == 2) availables.remove(C);
            if (cvCnt[V] == 2) availables.remove(V);

            left++;
            right++;
        }

        // 2. 탐색
//         System.out.printf("depth[%d] cur:%s availables:%s\n", depth, Arrays.toString(str), availables);
        for (int cv : availables) {
            str[targetIdx] = cv;
            totalCVCnt[cv]++;
            dfs(depth+1);
            totalCVCnt[cv]--;
            str[targetIdx] = EMPTY;
        }
    }

    private static long calculate() {
        long answer = (long) Math.pow(C_TOT, totalCVCnt[C]) * (long) Math.pow(V_TOT, totalCVCnt[V]);
        if (!hasL) {
            answer -= (long) Math.pow(C_TOT-1, totalCVCnt[C]) * (long) Math.pow(V_TOT, totalCVCnt[V]);
        }
        return answer;
    }

}
