import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static final int IMPOSSIBLE = -1;
    static int A, B;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        int cnt = 1;

        while (B > A) {
            if (B % 2 == 0) {
                B >>= 1;
            } else if (B % 10 == 1) {
                B /= 10;
            } else {
                break;
            }
            cnt++;
        }

        if (B != A) {
            sb.append(IMPOSSIBLE);
        } else {
            sb.append(cnt);
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}