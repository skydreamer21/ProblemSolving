import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[] positions;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        positions = new int[M];
        st = new StringTokenizer(br.readLine());
        for (int i=0; i<M; i++) {
            positions[i] = Integer.parseInt(st.nextToken());
        }

        // lower bound
        int lo = 0;
        int hi = N;
        int mid;

        while (lo < hi) {
            mid = (lo + hi) / 2;
//            System.out.printf("lo : %d, hi : %d, mid : %d\n", lo, hi, mid);
            if (isPossible(mid)) {
//                System.out.println("possible");
                hi = mid;
            } else {
//                System.out.println("impossible");
                lo = mid + 1;
            }
        }

        sb.append(lo);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static boolean isPossible(int height) {
        int coveredPos = 0;
        for (int i=0; i<M; i++) {
            // 1. lower bound check
            if (positions[i] - height > coveredPos) return false;

            // 2. update higher bound
            coveredPos = positions[i] + height;
        }
        return coveredPos >= N;
    }

}