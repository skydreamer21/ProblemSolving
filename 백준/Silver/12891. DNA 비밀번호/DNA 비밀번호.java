import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static Map<Character, Integer> charToIdx = new HashMap<>();
    
    static int N, M;
    static char[] input;
    static int[] condition; // A C G T
    static int[] countTable;
    static int answer = 0;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        initTable();
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        input = br.readLine().toCharArray();
        st = new StringTokenizer(br.readLine());
        condition = new int[4];
        for (int i = 0; i < 4; i++) {
            condition[i] = Integer.parseInt(st.nextToken());
        }
        
        countTable = new int[4];
        for (int i = 0; i < M; i++) {
            countTable[charToIdx.get(input[i])]++;
        }
        if (check()) {
            answer++;
        }
        
        for (int i = M; i < N; i++) {
            countTable[charToIdx.get(input[i])]++;
            countTable[charToIdx.get(input[i-M])]--;
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
    
    private static void initTable() {
        charToIdx.put('A', 0);
        charToIdx.put('C', 1);
        charToIdx.put('G', 2);
        charToIdx.put('T', 3);
    }
    
    public static boolean check() {
        for (int i = 0; i < 4; i++) {
            if (countTable[i] < condition[i]) {
                return false;
            }
        }
        return true;
    }
}