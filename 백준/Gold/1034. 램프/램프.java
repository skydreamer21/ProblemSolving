import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static int N, M, K;
    static Map<Long, Integer> rowMap = new HashMap<>();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        for (int i=0; i<N; i++) {
            Long row = Long.parseLong(br.readLine(), 2);

            if (!rowMap.containsKey(row)) {
                rowMap.put(row, 0);
            }
            rowMap.replace(row, rowMap.get(row)+1);
        }
        K = Integer.parseInt(br.readLine());

        int answer = 0;
        for (long row : rowMap.keySet()) {
            int onCount = Long.bitCount(row);
            int require = M - onCount;
            if (require > K || require % 2 != K % 2) continue;
            answer = Math.max(answer, rowMap.get(row));
        }

        sb.append(answer);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}