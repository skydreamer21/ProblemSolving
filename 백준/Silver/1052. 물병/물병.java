import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        int bitCount = Integer.bitCount(a);
        if (bitCount <= b) {
            System.out.println(0);
            return;
        }
        
        char[] bits = Integer.toBinaryString(a).toCharArray();
        int len = bits.length;
        int count = 0;
        int remain = 0;
        for (int i=0; i<len; i++) {
            if (bits[i] == '1') {
                count++;
            }
            if (count == b) {
                remain = Integer.parseInt(String.valueOf(bits, i, len - i), 2);
                break;
            }
        }
        
        int shift = (int)(Math.log(remain)/Math.log(2)) + 1;
        sb.append((1 << shift) - remain);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}