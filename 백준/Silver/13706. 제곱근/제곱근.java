import java.io.*;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        BigInteger N = new BigInteger(br.readLine());
        sb.append(sqrt(N));
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static BigInteger sqrt(BigInteger n) {
        BigInteger x = n;
        BigInteger y = (x.add(new BigInteger("1"))).shiftRight(1);
        while (y.compareTo(x) < 0) {
            x = y;
            y = x.add(n.divide(x)).shiftRight(1);
        }
        return x;
    }
}