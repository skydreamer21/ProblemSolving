import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static int limit;

    static class Node {
        int idx;

        public Node(int idx) {
            this.idx = idx;
        }

        public int next(int mod) {
            return (idx + mod -1) % mod;
        }

        public void move(int to) {
            this.idx = to;
        }
    }

    static class CV {
        int N;
        int[] hp;
        boolean[] hasRobot;
        int UP_START;
        int DOWN_START;
        Deque<Node> q;
        int cnt = 0;


        public CV(int[] hp) {
            N = hp.length;
            this.hp = hp;
            hasRobot = new boolean[N];
            UP_START = N -1;
            DOWN_START = N/2;
            q = new ArrayDeque<>();
        }

        public int operate() {
            int step = 1;
            while(moveCV(step)) {
//                System.out.printf("[STEP %d] cnt: %d, result : %s\n", step, cnt, Arrays.toString(hp));
                step++;
//                if (step >= 20) break;
            }
            return step;
        }

        public boolean moveCV(int n) {

            // 1. 현재 단계에서 올리는 칸과 내리는 칸 파악
            int up = (UP_START + n) % N;
            int down = (DOWN_START + n) % N;

//            System.out.printf("[STEP %d], up:%d, down:%d\n", n, up, down);

            // 2. 큐에 가장 앞서 있는 로봇이 내리는 칸에 있는지 검사
            if (!q.isEmpty()) {
//                System.out.printf("가장 앞선 로봇 : %d\n", q.peek().idx);

            }
            if (!q.isEmpty() && q.peek().idx == down) {
//                System.out.printf("앞선 로봇이 %d에서 내림\n", q.peek().idx);
                hasRobot[q.peek().idx] = false;
                q.poll();
            }

            // 3. 큐의 요소들을 순회하면서 이동할 수 있는 로봇들을 이동시킨다.
            for (Node node : q) {
                int cur = node.idx;
                int next = node.next(N);
                if (hp[next] != 0 && !hasRobot[next]) {
                    node.move(next);
                    hp[next]--;
                    if (hp[next] == 0) {
                        cnt++;
                        if (cnt == limit) return false;
                    }
                    hasRobot[cur] = false;
                    if (next == down) {
                        q.poll();
                    } else {
                        hasRobot[next] = true;
                    }
                }
            }

            // 4. 올리는 위치에 가능하면 로봇을 올린다.
            if (hp[up] != 0) {
                hp[up]--;
                if (hp[up] == 0) {
                    cnt++;
                    if (cnt == limit) return false;
                }
                hasRobot[up] = true;
                q.add(new Node(up));
            }
            for (int i = 0; i<N; i++) {
                int p = (up + N -i)%N;
//                System.out.printf("%d(%d) ", (p)%N, hasRobot[p] ? 1 : 0);
            }
//            System.out.println();
            return true;
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken())*2;
        limit = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] hp = new int[N];
        for (int i = 0; i<N; i++) {
            hp[N-1-i] = Integer.parseInt(st.nextToken());
        }

        CV cv = new CV(hp);

        sb.append(cv.operate());

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
