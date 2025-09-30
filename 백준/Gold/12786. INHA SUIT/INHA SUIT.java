import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int IMPOSSIBLE = -1;
    static final int TOTAL_HEIGHT = 20;

    static int N, T;
    static boolean[][] holes;
    static int[][] dp;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        T = Integer.parseInt(br.readLine());
        holes = new boolean[N][TOTAL_HEIGHT+1];
        dp = new int[N][TOTAL_HEIGHT+1];

        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            int holeNum = Integer.parseInt(st.nextToken());
            //  구멍이 없는 나무가 있으면 이동 불가 -> 바로 종료
            if (holeNum == 0) {
                System.out.println(IMPOSSIBLE);
                return;
            }
            for (int j=0; j<holeNum; j++) {
                int inputHoleHeight = Integer.parseInt(st.nextToken());
                holes[i][inputHoleHeight] = true;
            }
        }

        for (int i=0; i<N; i++) {
            Arrays.fill(dp[i], T+1);
        }

        for (int i=1; i<=TOTAL_HEIGHT; i++) {
            if(holes[N-1][i]) {
                dp[N-1][i] = 0;
            }
        }

        for (int i=N-2; i>=0; i--) {
            for (int h=1; h<=TOTAL_HEIGHT; h++) {
                if (holes[i][h]) {
                    int nextH = -1;
                    boolean moved = false;
                    for (int m=0; m<4; m++) {
                        switch (m) {
                            case 0:
                                nextH = h;
                                break;
                            case 1:
                                nextH = h+1;
                                break;
                            case 2:
                                nextH = Math.min(h*2, TOTAL_HEIGHT);
                                break;
                            case 3:
                                nextH = h-1;
                                break;
                        }
                        if (nextH < 1 || nextH > 20 || !holes[i+1][nextH]) {
                            continue;
                        }

                        moved = true;
                        dp[i][h] = Math.min(dp[i][h], dp[i+1][nextH]);

                    }

                    // 어떤 일반 이동으로 안되서 순간이동 써야하는 경우
                    if (!moved) {
                        for (int nh=1; nh<=TOTAL_HEIGHT; nh++) {
                            if (holes[i+1][nh]) {
                                dp[i][h] = Math.min(dp[i][h], dp[i+1][nh] + 1);
                            }
                        }
                    }
                }
            }
        }

        //  시작지점 (높이 1) 에서 다음 나무 구멍에 기록된 dp를 보고 정답 출력
        int answer = T+1;
        for(int h=1; h<=TOTAL_HEIGHT; h++) {
            if (holes[0][h]) {
                int compare = dp[0][h];
                if (h != 1 && h != 2) {
                    compare+=1;
                }
                answer = Math.min(answer, compare);
            }
        }

        if (answer > T) {
            sb.append(IMPOSSIBLE);
        } else {
            sb.append(answer);
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}