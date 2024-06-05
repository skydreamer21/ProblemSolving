import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringTokenizer;

public class Main {
    static final int IMPOSSIBLE = -1;
    static int N, P;
    static int[] scores;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        if (N == 0) {
            System.out.println(1);
            return;
        }
        int targetScore = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        scores = new int[N];
        for (int i=0; i<N; i++) {
            scores[i] = Integer.parseInt(st.nextToken());
        }

        int[] ranks = new int[N];
        ranks[0] = 1;
        for (int i=1; i<N; i++) {
            if (scores[i] == scores[i - 1]) {
                ranks[i] = ranks[i - 1];
            } else {
                ranks[i] = i + 1;
            }
        }

        int indexFounded = binarySearch(targetScore);
        int rank = IMPOSSIBLE;
        if (indexFounded != IMPOSSIBLE) {
            if (indexFounded == 0) {
                rank = 1;
            } else if (scores[indexFounded - 1] == targetScore) {
                rank = ranks[indexFounded - 1];
            } else {
                rank = indexFounded + 1;
            }
        }

        sb.append(rank);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 1. 크면 무조건 왼쪽으로
    // 2. 작거나 같으면 오른쪽으로
    // 3. 반환 값 : 내가 들어갈 사이 기준 오른쪽 값
    public static int binarySearch(int target) {
        int lo = 0;
        int hi = N;
        int mid;

        while (lo < hi) {
            mid = (lo + hi) / 2;
            if (target <= scores[mid]) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo < P ? lo : IMPOSSIBLE;
    }

}