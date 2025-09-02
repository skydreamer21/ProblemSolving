import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static final int[] startDirIdx = {0, 8};
    static final int[][] DIR = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, -2}, {1, 2}, {-1, -2}, {-1, 2}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static int K;
    static int N, M;
    static int[][] map;
    static boolean[][][] visited;

    static class Node {
        int x, y;
        int skill;

        public Node(int x, int y, int skill) {
            this.x = x;
            this.y = y;
            this.skill = skill;
        }
    }
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        K = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        visited = new boolean[K + 1][N][M];
        sb.append(bfs());


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static int bfs() {
        if (N - 1 == 0 && M - 1 == 0) {
            return 0;
        }
        Deque<Node> q = new ArrayDeque<>();
        q.add(new Node(0, 0, 0));
        visited[0][0][0] = true;
        int dist = 0;

        while (!q.isEmpty()) {
            dist++;
            int size = q.size();

//            System.out.printf("########## dist : %d ##########\n", dist);
            for (int i = 0; i < size; i++) {
                Node now = q.poll();
//                System.out.printf("@@@ now : (%d, %d), skillCnt : %d\n", now.x, now.y, now.skill);

                int startDir = now.skill < K ? 0 : 8;
                for (int j = startDir; j < 12; j++) {
                    int nextX = now.x + DIR[j][0];
                    int nextY = now.y + DIR[j][1];
                    if (isInRange(nextX, nextY) && map[nextX][nextY] != 1) {
                        int skillCnt = j < 8 ? now.skill+1 : now.skill;
                        if (!visited[skillCnt][nextX][nextY]) {
                            if (nextX == N - 1 && nextY == M - 1) {
                                return dist;
                            }

                            visited[skillCnt][nextX][nextY] = true;
//                            System.out.printf("** 후보 next : (%d, %d), skillCnt : %d\n", nextX, nextY, skillCnt);
                            q.add(new Node(nextX, nextY, skillCnt));
                        }
                    }
                }

            }
        }
        return -1;
    }

    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < M;
    }
}