import java.io.*;
import java.util.StringTokenizer;

public class Main {
    private static final int IMPOSSIBLE = -1;
    static long total, win;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        total = Integer.parseInt(st.nextToken());
        win = Integer.parseInt(st.nextToken());
        int initRate = calculateWinRate(total, win);
        if (initRate >= 99) {
            System.out.println(IMPOSSIBLE);
            return;
        }
        int goalRate = initRate + 1;
        
        int answer = (int) Math.ceil((double) ((total * goalRate) - 100 * win) / (100 - goalRate));
        
        sb.append(answer);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static int calculateWinRate(long total, long win) {
        return (int)(win * 100 / total);
    }
}