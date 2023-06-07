import java.io.*;
import java.util.HashMap;

public class Main {
    static final char BLACK = '#';
    static final char WHITE = '.';
    static final int ERROR = -1;
    
    static int N, len;
    static HashMap<Integer, Integer> decodeTable;
    static char[][] code;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        initDecodeTable();
        
        N = Integer.parseInt(br.readLine());
        len = N / 5;
        
        code = new char[5][len];
        char[] input = br.readLine().toCharArray();
        
        for (int i = 0; i < 5; i++) {
            System.arraycopy(input, len * i, code[i], 0, len);
        }
        
        for (int col = 0; col < len; col++) {
            if (code[0][col] == WHITE) continue;
            int decodedNum = decode(col);
            if (decodedNum != 1) {
                col += 2;
            }
            sb.append(decodedNum);
        }
        
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static void initDecodeTable() {
        decodeTable = new HashMap<>();
        decodeTable.put(75557, 0);
        decodeTable.put(71747, 2);
        decodeTable.put(71717, 3);
        decodeTable.put(55711, 4);
        decodeTable.put(74717, 5);
        decodeTable.put(74757, 6);
        decodeTable.put(71111, 7);
        decodeTable.put(75757, 8);
        decodeTable.put(75717, 9);
    }
    
    static int decode(int col) {
        if (isFullColumn(col)) {
            if (col == len - 1 || code[0][col + 1] == WHITE) {
                return 1;
            }
        }
        
        int num = 0;
        for (int i = 0; i < 5; i++) {
            num += convert(i, col);
            num *= 10;
        }
        return decodeTable.get(num/10);
    }
    
    static int convert(int r, int c) {
        int num = 0;
        for (int i = 0; i < 3; i++) {
            if (code[r][c + i] == BLACK) {
                num += 1;
            }
            num <<= 1;
        }
        return num>>1;
    }
    
    static boolean isFullColumn(int col) {
        for (int i = 0; i < 5; i++) {
            if (code[i][col] == WHITE) {
                return false;
            }
        }
        return true;
    }
}