import java.io.*;

public class Main {
    static final char YES = 'Y';
    static final char NO = 'N';
    
    static int N;
    static char[][] map;
    static boolean[][] visited;
    static int[] friends;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        N = Integer.parseInt(br.readLine());
        map = new char[N][N];
        visited = new boolean[N][N];
        friends = new int[N];
        
        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
        }
        
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                if (isFriend(i, j) || hasCommonFriend(i, j)) {
                    friends[i]++;
                    friends[j]++;
                }
            }
        }
        
        int max = -1;
        for (int i = 0; i < N; i++) {
            max = Math.max(max, friends[i]);
        }
        
        sb.append(max);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static boolean hasCommonFriend(int f1, int f2) {
        for (int i = 0; i < N; i++) {
            if (i == f1 || i == f2) continue;
            if (isFriend(i, f1) && isFriend(i, f2)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean isFriend(int f1, int f2) {
        return map[f1][f2] == YES;
    }
}