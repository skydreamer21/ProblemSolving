import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Solution {
    static final int IMPOSSIBLE = -1;
    static final int NOT_INIT = -2;
    static final int FIRST_CONDITION = 1;
    static final int NOT_FIRST_CONDITION = 0;

    static int N, X, M, x;
    static Condition[] conditions;
    static State answer, state;
    static int[] isInFirstCondition;

    static class Condition implements Comparable<Condition>{
        int left, right, sum;

        public Condition(int left, int right, int sum) {
            this.left = left;
            this.right = right;
            this.sum = sum;
        }

        @Override
        public int compareTo(Condition o) {
            int diff = this.right - this.left;
            int oDiff = o.right - o.left;
            if (diff == oDiff) {
                return this.right - o.right;
            }
            return diff - oDiff;
        }
    }

    static class State {
        int[] state;
        int total;
        int[] prefixSum;
        int lineIdx;
        public State () {
            total = NOT_INIT;
            state = new int[N+1];
            prefixSum = new int[N + 1];
        }

        public void setState(int idx, int num) {
            state[idx] = num;
        }

        public void makeMetaData() {
            makePrefixSum();
            makeLineIdx();
        }

        private void makePrefixSum() {
            for (int i=1; i<=N; i++) {
                prefixSum[i] = prefixSum[i - 1] + this.state[i];
            }
            total = prefixSum[N];
        }

        private void makeLineIdx() {
            lineIdx = state[1];
            for (int i=2; i<=N; i++) {
                lineIdx *= x;
                lineIdx += state[i];
            }
        }

        public boolean check(Condition condition) {
            if (prefixSum == null) {
                int sum = 0;
                for (int i=condition.left; i<=condition.right; i++) {
                    sum += state[i];
                }
                return sum == condition.sum;
            }
            return prefixSum[condition.right] - prefixSum[condition.left - 1] == condition.sum;
        }

        public int compareTo (State o) {
            if (o.total == this.total) {
                return this.lineIdx - o.lineIdx;
            }
            return o.total - this.total;
        }

        public boolean isNotInit() {
            return total == NOT_INIT;
        }

        public State copy() {
            State copied = new State();
            copied.state = Arrays.copyOf(this.state, this.state.length);
            copied.makeMetaData();
            return copied;
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
            answer = new State();

            for (int i=0; i<M; i++) {
                st = new StringTokenizer(br.readLine());
                conditions[i] = new Condition(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }
            Arrays.sort(conditions);

            isInFirstCondition = new int[N+1];
            for (int i=conditions[0].left; i<=conditions[0].right; i++) {
                isInFirstCondition[i] = FIRST_CONDITION;
            }

            state = new State();

            solve(1, 0);

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

    // idx 번째 부터 isInFirstCondition 이 FIRST_CONDITION 인 것을 정한다.
    public static void solve(int idx, int sum) {
//        System.out.printf("[IN][solve] idx : %d, sum : %d\n", idx, sum);
        if (idx == N+1) {
            // 첫번째 조건에 맞으면 나머지 것들을 정하자
            if (sum == conditions[0].sum) {
//                System.out.println("첫번째 조건 맞음!! 다음 단계로!");
                solveSecond(1);
            }
            return;
        }

        if (isInFirstCondition[idx] == FIRST_CONDITION) {
            for (int i = 0; i <= X; i++) {
                state.setState(idx, i);
//                System.out.printf("%d 에 %d 넣고 다음 거 정하자\n", idx, i);
                solve(idx+1, sum + i);
            }
        } else {
//            System.out.println("첫번째 조건에서 정할 수가 아님.");
            solve(idx+1, sum);
        }

    }

    // 정해지지 않은 것들을 정하고 마지막에 판단
    public static void solveSecond(int idx) {
//        System.out.println(state);
        if (idx == N+1) {
            state.makeMetaData();
            boolean findAns = true;
            for (int i=1; i<M; i++) {
                if (!state.check(conditions[i])) {
                    findAns = false;
                    break;
                }
            }
            if (findAns && answer.compareTo(state) > 0) {
                answer = state.copy();
            }
            return;
        }

        if (isInFirstCondition[idx] == NOT_FIRST_CONDITION) {
            for (int i=0; i<=X; i++) {
                state.setState(idx, i);
                solveSecond(idx+1);
            }
        } else {
            solveSecond(idx+1);
        }
    }
}