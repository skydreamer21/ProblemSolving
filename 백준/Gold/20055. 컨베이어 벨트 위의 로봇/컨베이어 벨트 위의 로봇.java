import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static Machine machine;

    static class Machine {
        int size;
        int start;
        int[] belt;
        boolean[] hasRobot;
        int zeroLimit;
        int zeroCount;

        public Machine(int size, int zeroLimit) {
            this.size = 2 * size;
            start = 0;
            belt = new int[this.size];
            hasRobot = new boolean[this.size];
            this.zeroLimit = zeroLimit;
            zeroCount = 0;
        }

        public void run() {
            rotate();
            moveRobots();
            load();
        }

        public boolean isRunnable() {
            return zeroCount < zeroLimit;
        }

        public void rotate() {
            start = start == 0 ? size - 1 : start - 1;
            int end = getEnd();
            if (hasRobot[end]) {
                hasRobot[end] = false;
            }
        }

        public void moveRobots() {
            int end = getEnd();
            for (int i = 1; i <= size/2 - 2; i++) { // 시작과 끝은 무조건 비어있는 상태임
                int pos = getPos(end, -i);
                // 해당 칸에 로봇이 없다면 pass
                if (!hasRobot[pos]) continue;

                // 다음 칸에 로봇을 올릴 수 없다면 pass
                if (isNotAvailable(getPos(pos, 1))) continue;

                // 이번 칸에 로봇이 있고 다음칸으로 옮길 수 있다면 옮긴다.
                hasRobot[pos] = false;
                int nextPos = getPos(pos, 1);
                hasRobot[nextPos] = true;
                belt[nextPos]--;

                if (belt[nextPos] == 0) {
                    zeroCount++;
                }
            }
            hasRobot[end] = false;
        }

        public void load() {
            if (hasRobot[start]) { // 나오면 안되는 상황
                throw new IllegalArgumentException("[ERROR] 시작 위치에 로봇이 있습니다.");
            }

            if (belt[start] > 0) {
                hasRobot[start] = true;
                belt[start]--;
                if (belt[start] == 0) {
                    zeroCount++;
                }
            }
        }

        private boolean isNotAvailable(int idx) {
            return hasRobot[idx] || belt[idx] == 0;
        }

        private int getEnd() {
            return (start + size / 2 - 1) % size;
        }

        private int getPos(int idx, int dx) {
            return (idx + size + dx) % size;
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        machine = new Machine(N, M);

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < machine.size; i++) {
            machine.belt[i] = Integer.parseInt(st.nextToken());
        }

        int step = 0;
        while (machine.isRunnable()) {
            step++;
            machine.run();
        }

        sb.append(step);


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}