import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static final int A = 0;
    static final int C = 1;
    static final int G = 2;
    static final int T = 3;
    static final char[] convertTable = {'A', 'C', 'G', 'T'};
    
    static int N, M;
    static char[] input;
    static int[] condition; // A C G T
    static Map<Character, Integer> countTable;
    static int answer = 0;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        input = br.readLine().toCharArray();
        st = new StringTokenizer(br.readLine());
        condition = new int[4];
        for (int i = 0; i < 4; i++) {
            condition[i] = Integer.parseInt(st.nextToken());
        }
        
        countTable = new HashMap<>();
        for (int i = 0; i < M; i++) {
            add(input[i]);
        }
        if (check()) {
            answer++;
        }
        
        for (int i = M; i < N; i++) {
            add(input[i]);
            remove(input[i - M]);
            if (check()) {
                answer++;
            }
        }
        
        sb.append(answer);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    public static boolean check() {
        for (int i = 0; i < 4; i++) {
            if (get(convertTable[i]) < condition[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static int get(char c) {
        if (!countTable.containsKey(c)) {
            countTable.put(c, 0);
        }
        return countTable.get(c);
    }
    
    public static void add(char c) {
        if (!countTable.containsKey(c)) {
            countTable.put(c, 0);
        }
        countTable.replace(c, countTable.get(c) + 1);
    }
    
    public static void remove(char c) {
        countTable.replace(c, countTable.get(c) - 1);
    }
}