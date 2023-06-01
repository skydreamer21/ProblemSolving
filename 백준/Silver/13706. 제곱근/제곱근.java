import java.io.*;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        BigInteger N = new BigInteger(br.readLine());
        sb.append(N.sqrt());
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}