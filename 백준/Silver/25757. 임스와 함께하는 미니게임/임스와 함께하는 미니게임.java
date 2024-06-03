import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static Map<Character, Integer> playerNumMap;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        playerNumMap = new HashMap<>();
        playerNumMap.put('Y', 2);
        playerNumMap.put('F', 3);
        playerNumMap.put('O', 4);

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int gameRequiredPlayerNum = playerNumMap.get(st.nextToken().charAt(0));

        Set<String> playerSet = new HashSet<>();
        for (int i=0; i<N; i++) {
            playerSet.add(br.readLine());
        }

        sb.append(playerSet.size() / (gameRequiredPlayerNum - 1));

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}