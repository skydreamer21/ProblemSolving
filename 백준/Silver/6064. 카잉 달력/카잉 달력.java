import java.io.*;
import java.util.StringTokenizer;

public class Main {
    private static final int IMPOSSIBLE = -1;
    
    static int N, M, A, B;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            boolean findAns = false;
            
            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());
            B = Integer.parseInt(st.nextToken());
            
            A = A == M ? 0 : A;
            B = B == N ? 0 : B;
            
            int lcm = lcm(N, M);
            if (A == 0 && B == 0) {
                sb.append(lcm).append("\n");
                continue;
            }
            for (int i = A; i < lcm; i += M) {
                if (i % N == B) {
                    sb.append(i).append("\n");
                    findAns = true;
                    break;
                }
            }
            
            if (!findAns) {
                sb.append(IMPOSSIBLE).append("\n");
            }
        }
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, (a - b) % b);
    }
    
    private static int lcm(int a, int b) {
        int gcd = gcd(Math.max(a, b), Math.min(a, b));
        return a * b / gcd;
    }
}