import java.io.*;

public class Main {
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}}; // down, right, up, left
    static final int EMPTY = 0;
    static int N, M;
    static int[][] map;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        map = new int[N][N];
        
        int answerX = -1;
        int answerY = -1;
        
        int num = N * N;
        int x = 0;
        int y = 0;
        int d = 0;
        
        while (num > 0) {
            map[x][y] = num;
            if (num == M) {
                answerX = x + 1;
                answerY = y + 1;
            }
            num -= 1;
            if (isNextImpossible(x, y, d)) {
                d = (d+1) % 4;
            }
            x += DIR[d][0];
            y += DIR[d][1];
        }
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(map[i][j]).append(" ");
            }
            sb.append("\n");
        }
        sb.append(answerX).append(" ").append(answerY);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static boolean isNextImpossible(int x, int y, int d) {
        int nextX = x + DIR[d][0];
        int nextY = y + DIR[d][1];
        return isNotInRange(nextX, nextY) || map[nextX][nextY] != EMPTY;
    }
    
    private static boolean isNotInRange(int x, int y) {
        return x < 0 || y < 0 || x >= N || y >= N;
    }
}