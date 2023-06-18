import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static final Map<Character, Integer> massTable = new HashMap<>();
    static char[] input;
    static int len;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        initMassTable();
        String inputStr = br.readLine();
        len = inputStr.length();
        input = new char[len + 1];
        char[] src = inputStr.toCharArray();
        System.arraycopy(src, 0, input, 0, len);
        input[len] = ')';
        
        sb.append(dfs(0)[0]);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static int[] dfs(int idx) {
        int total = 0;
        int add = 0;
        while (input[idx] != ')') {
            if (input[idx] == '(') {
                int[] info = dfs(idx+1); 
                add = info[0];
                idx = info[1];
                if (Character.isDigit(input[info[1] + 1])) {
                    add *= Character.digit(input[info[1] + 1], 10);
                    idx++;
                }
            } else if (Character.isDigit(input[idx])) {
                int mul = Character.digit(input[idx], 10) - 1;
                add = massTable.get(input[idx - 1]) * mul;
            } else {
                add = massTable.get(input[idx]);
            }
            total += add;
            idx++;
        }
        return new int[]{total, idx};
    }
    
    private static void initMassTable() {
        massTable.put('H', 1);
        massTable.put('C', 12);
        massTable.put('O', 16);
    }
}