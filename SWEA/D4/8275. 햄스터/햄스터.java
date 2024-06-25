import java.io.*;
import java.util.StringTokenizer;

public class Solution {
    static final int IMPOSSIBLE = -1;
    static final int NOT_INIT = -2;

    static int N, X, M, x;
    static Condition[] conditions;
    static boolean findAns;
    static State answer;

    static class Condition {
        int left, right, sum;

        public Condition(int left, int right, int sum) {
            this.left = left;
            this.right = right;
            this.sum = sum;
        }
    }

    static class State {
        int[] state;
        int total;
        int[] prefixSum;
        public State () {
            total = NOT_INIT;
        }

        public State(int state) {
            this.state = new int[N+1];
            prefixSum = new int[N+1];
            for (int i=1; i<=N; i++) {
                int stateIdx = N+1 - i;
                this.state[stateIdx] = state % x;
                state /= x;
            }
            for (int i=1; i<=N; i++) {
                prefixSum[i] = prefixSum[i - 1] + this.state[i];
            }
            total = prefixSum[N];
        }

        public boolean check(Condition condition) {
            return prefixSum[condition.right] - prefixSum[condition.left - 1] == condition.sum;
        }

        public int compareTo (State o) {
            return o.total - this.total;
        }

        public boolean isNotInit() {
            return total == NOT_INIT;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int TC = Integer.parseInt(br.readLine());
        int testNo = 0;
        while (testNo++ < TC) {
//            System.out.println("===============================");
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            X = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            x = X+1;

            conditions = new Condition[M];
            findAns = false;
            answer = new State();

            for (int i=0; i<M; i++) {
                st = new StringTokenizer(br.readLine());
                conditions[i] = new Condition(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }

            int lineIdx = -1;

            while (lineIdx++ < Math.pow(x, N)) {
//                System.out.printf("<lineIdx : %d>\n", lineIdx);
                State state = new State(lineIdx);
                if (answer.compareTo(state) <= 0) continue;

                boolean findCaseAns = true;

                for (int i=0; i<M; i++) {
                    if (!state.check(conditions[i])) {
                        findCaseAns = false;
                        break;
                    }
                }

                if (!findCaseAns) continue;
                answer = state;
//                System.out.println(state);
            }

            sb.append("#").append(testNo).append(" ");
            if (answer.isNotInit()) {
                sb.append(IMPOSSIBLE);
            } else {
                for (int i=1; i<=N; i++) {
                    sb.append(answer.state[i]).append(" ");
                }
            }

            sb.append("\n");
        }


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}