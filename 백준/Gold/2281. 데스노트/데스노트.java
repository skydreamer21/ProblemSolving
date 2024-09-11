import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static final int UP = 0;
    static final int DOWN = 1;
    static final int NOT_YET = -1;

    static int N, M;
    static int[] numbers;
    static int[][][] dp;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        numbers = new int[N];

        dp = new int[N][M+1][2];
        for (int i=0; i<N; i++) {
            for (int j=0; j<M+1; j++) {
                for (int k=0; k<2; k++) {
                    dp[i][j][k] = NOT_YET;
                }
            }
        }

        for (int i=0; i<N; i++) {
            numbers[i] = Integer.parseInt(br.readLine());
        }
        Set<Integer> update = new HashSet<>();
        dp[0][numbers[0]][UP] = 0;
        dp[0][numbers[0]][DOWN] = M*M;
        update.add(numbers[0]);
        Set<Integer> temp = new HashSet<>();
        boolean[][] visited = new boolean[M+1][2];

        // j는 채운 칸의 수
        for (int i=1; i<N; i++) {
            // 이전 칸에 올렸던 모든 곳을 탐색
//            System.out.printf("%d번째 수 진행\n", i+1);
//            System.out.println(update);
            for (int j : update) {
                // 바로 직전에 이름을 올렸던 칸들에 대해서 진행
//                System.out.printf("j = %d\n", j);
                int minValue;
                if (dp[i-1][j][UP] == NOT_YET) {
                    minValue = dp[i-1][j][DOWN];
                } else if (dp[i-1][j][DOWN] == NOT_YET) {
                    minValue = dp[i-1][j][UP];
                } else {
                    minValue = Math.min(dp[i-1][j][UP], dp[i-1][j][DOWN]);
                }

                if (minValue == NOT_YET) {
                    throw new IllegalArgumentException("Something is wrong");
                }

                // 1. 현재 칸에 올리기
                if (j + numbers[i] < M) {
                    if (dp[i][j + 1 + numbers[i]][UP] == NOT_YET) {
                        dp[i][j + 1 + numbers[i]][UP] = minValue;
                    } else {
                        dp[i][j + 1 + numbers[i]][UP] = Math.min(dp[i][j + 1 + numbers[i]][UP], minValue);
                    }
                    temp.add(j+1+numbers[i]);
                    visited[j+1+numbers[i]][UP] = true;
                }

                // 2. 다음 칸에 올리기
//                dp[i][numbers[i]][DOWN] = Math.min(dp[i][numbers[i]][DOWN], minValue + (M-j)*(M-j));

                if (dp[i][numbers[i]][DOWN] == NOT_YET) {
                    dp[i][numbers[i]][DOWN] = minValue + (M-j)*(M-j);
                } else {
                    dp[i][numbers[i]][DOWN] = Math.min(dp[i][numbers[i]][DOWN], minValue + (M-j)*(M-j));
                }
                temp.add(numbers[i]);
                visited[numbers[i]][DOWN] = true;
            }

            // 새로운 업데이트 제외하고 모두 초기화
//            for (int j=1; j<=M; j++) {
//                dp
//            }

            // update 갱신하기
            update.clear();
            update.addAll(temp);
            temp.clear();
        }

        int min = Integer.MAX_VALUE;
        for (int i=1; i<=M; i++) {
            for (int j=0; j<2; j++) {
                if (dp[N-1][i][j] == NOT_YET) continue;
                min = Math.min(min, dp[N-1][i][j]);
            }
//            int minValue = Math.min(dp[N-1][i][UP], dp[N-1][i][DOWN]);
//            min = Math.min(min, minValue);
        }

        sb.append(min);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}