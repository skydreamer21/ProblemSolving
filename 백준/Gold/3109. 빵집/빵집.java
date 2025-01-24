import java.io.*;
import java.util.*;

public class Main {
    static final char BUILDING = 'x';
    static final char EMPTY = '.';

    static int N, M;
    static char[][] map;
    static boolean[][] visited;
    static Deque<Integer> visitStack;
    static boolean[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];
        for (int i = 0; i<N; i++) {
            map[i] = br.readLine().toCharArray();
        }
        visited = new boolean[N][M];
        dp = new boolean[N][M];
        for (int i=0; i<N; i++) {
            Arrays.fill(dp[i], true);
        }

        int ans = 0;
        visitStack = new ArrayDeque<>();
        for (int i=0; i<N; i++) {
//            System.out.printf("\n### row %d search!###\n", i);
            if (!visitStack.isEmpty()) {
                throw new IllegalStateException("초기 스택이 비어있지 않습니다.");
            }
            if (search(0, i)) {
                if (visitStack.size() != M-1) {
                    throw new IllegalArgumentException("길을 찾았지만 경로의 개수가 알맞지 않습니다.");
                }
                ans++;

                int col = 1;
                while (!visitStack.isEmpty()) {
                    visited[visitStack.poll()][col] = true;
                    col++;
                }
            }
        }

        sb.append(ans);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }


    public static boolean search(int c, int row) {
//        System.out.printf("[IN] c : %d, row : %d \n", c, row);
        if (c == M-1) {
//            System.out.printf("[OUT - END] c : %d, row : %d \n", c, row);
            return true;
        }

        boolean hasPath = false;
        for (int d=-1; d<=1; d++) { // 다음 위치 정하기
            int nextR = row + d;
            if ( isInRange(nextR, c+1) && !visited[nextR][c+1] && map[nextR][c+1] != BUILDING && dp[nextR][c+1] ) {
                visitStack.add(nextR);
                hasPath = search(c+1, nextR);
                dp[nextR][c+1] = false;
                if (hasPath) break;
                visitStack.removeLast();
            }
        }

//        System.out.printf("[OUT - ALL FOUND] c : %d, row : %d \n", c, row);
        return hasPath;
    }

    public static boolean isInRange(int r, int c) {
        return r>=0 && c>=0 && r<N && c<M;
    }
}