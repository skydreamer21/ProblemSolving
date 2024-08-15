// 2579번 계단 오르기

/*
<문제 정보>
 1. 계단마다 점수 있음
 2. 계단은 한번에 한칸 또는 두칸 오를 수 있고 / 연속한 세 계단은 동시에 밟지 못함
 3. 위 규칙을 만족할 때 얻을 수 있는 점수의 최댓값
 4. 계단 N개 1<=N<=300 /  계단에 쓰여 있는 점수는 10,000이하의 자연수

<프로그램 진행>
 1.

<필요 함수>
 1.

 */



import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] stairs;
    static int[][] memo;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        stairs = new int[N+1];
        memo = new int[N+1][2];
        for (int i=1;i<=N;i++) stairs[i]=Integer.parseInt(br.readLine());
        memo[1][0]=stairs[1]; memo[1][1]=1;
        if(N>1) {
            memo[2][0]=stairs[1]+stairs[2];
            memo[2][1]=2;
        }

        if(N<3) bw.write(String.valueOf(memo[N][0]));
        else bw.write(String.valueOf(find_Bottomup(N)));

        bw.flush();
        bw.close();
        br.close();
    }

    public static int find_Bottomup (int n) {
        if (memo[n][0]==0 && n!=0) {
            for (int i=3;i<=n;i++) {
                if (memo[i][0]==0) {
                    if(memo[i-1][1]!=2) {
                        if(memo[i-2][0]>=memo[i-1][0]) {
                            memo[i][0]=memo[i-2][0]+stairs[i];
                            memo[i][1]=1;
                            //System.out.println("1");
                        }
                        else {
                            memo[i][0]=memo[i-1][0]+stairs[i];
                            memo[i][1]=memo[i-1][1]+1;
                            //System.out.println("2");
                        }
                    }
                    else if (memo[i-1][1]==2) {
                        if (stairs[i-1]+memo[i-3][0]>=memo[i-2][0]) {
                            memo[i][0]=memo[i-3][0]+stairs[i-1]+stairs[i];
                            memo[i][1]=2;
                            //System.out.println("3");
                        }
                        else {
                            memo[i][0]=memo[i-2][0]+stairs[i];
                            memo[i][1]=1;
                            //System.out.println("4");
                        }
                    }
                }
            }
        }
        else if (n==0) return 0;
        return memo[n][0];
    }
}