import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[] arr;
    static Set<Integer> arrSet;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        arr = new int[N+1];
        arrSet = new HashSet<>();
        int max = Integer.MIN_VALUE;
        for (int i = 1; i<=N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            arrSet.add(arr[i]);
            max = Math.max(max, arr[i]);
        }
        M = Integer.parseInt(br.readLine());

        int next = arrSet.size();

//        System.out.printf("next : %d, max : %d\n", next, max);

        if (M <= N) {
            sb.append(arr[M]);
        } else {
            int answer;
            if (next <= max ) {
                int diff = Integer.MAX_VALUE;
                for (int i = 1; i<=N; i++) {
                    if (arr[i] < next) continue;
                    diff = Math.min(diff, arr[i] - next);
                }
//                System.out.printf("diff : %d\n", diff);
                int nextBig = next + diff;

                if (M >= N+1 + diff) {
                    answer = nextBig;
                } else {
                    answer = next + (M - (N+1));
                }
            } else {
                answer = next + (M - (N+1));
            }

            sb.append(answer);
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
