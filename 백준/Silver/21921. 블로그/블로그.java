import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static int N, X;
    static int[] arr;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i=0; i<N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int max = 0;
        int cnt = 1;
        for (int i=0; i<X; i++) {
            max += arr[i];
        }

        int tempSum = max;
        for (int i=X; i<N; i++) {
            tempSum += (arr[i] - arr[i-X]);
            if (tempSum > max) {
                max = tempSum;
                cnt = 1;
            } else if (tempSum == max) {
                cnt++;
            }
        }

        if (max == 0) {
            sb.append("SAD");
        } else {
            sb.append(max).append("\n")
                    .append(cnt);
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}