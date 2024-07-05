// 2293번 동전1
/*
<문제 정보>
 1. n가지 종류 동전을 이용해 가치 k를 만드는 경우의 수 출력

<변수 범위>
 1. 0.5초 / 4MB
 2. n : 100 이하 자연수
 3. k : 10,000 이하 자연수
 4. 순서가 달라도 구성이 같으면 같은 경우

<프로그램 진행>
 1.

<필요 함수>
 1.

 */


import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[] dp;
    static int[] dp_prev;
    static int N;
    static int K;


    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        dp = new int[K+1];
        dp_prev = new int[K+1];
        int value = Integer.parseInt(br.readLine());
        N--;
        init_dp(value);
        int debug=1;
        while(N-- >0) {
            value = Integer.parseInt(br.readLine());
            next_dp(value);
            System.arraycopy(dp,1,dp_prev,1,K);
        }
        bw.write(String.valueOf(dp_prev[K]));
        bw.flush();
        bw.close();
        br.close();
    }

    public static void init_dp(int value) {
        int sum=value;
        while(sum<=K) {
            dp_prev[sum]+=1;
            sum+=value;
        }
    }

    public static void next_dp(int value) {
        for (int i=1;i<=K;i++) {
            if (i<value) dp[i]=dp_prev[i];
            else if (i==value) dp[i]=dp_prev[i]+1;
            else dp[i] = dp_prev[i]+dp[i-value];
        }
    }
}