import java.io.*;

public class Main {
    static final char DELIM = '_';
    static final int C_PLUS = 1;
    static final int JAVA = 2;
    static final String ERROR_MSG = "Error!";
    static char[] input;
    static int mode = 0;
    static boolean hasError = false;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        input = br.readLine().toCharArray();
        
        if (Character.isUpperCase(input[0]) || input[0] == DELIM || input[input.length-1] == DELIM) {
            System.out.println(ERROR_MSG);
            return;
        }
        
        boolean hasDelimBefore = false;
        for (char c : input) {
            if (c == DELIM) {
                if (mode == JAVA || hasDelimBefore) {
                    hasError = true;
                    break;
                }
                mode = C_PLUS;
                hasDelimBefore = true;
                continue;
            }
            if (hasDelimBefore) {
                if (Character.isUpperCase(c)) {
                    hasError = true;
                    break;
                }
                sb.append(Character.toUpperCase(c));
                hasDelimBefore = false;
                continue;
            }
            if (Character.isUpperCase(c)) {
                if (mode == C_PLUS) {
                    hasError = true;
                    break;
                }
                mode = JAVA;
                sb.append(DELIM).append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        
        if (hasError) {
            sb.setLength(0);
            sb.append(ERROR_MSG);
        }
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}