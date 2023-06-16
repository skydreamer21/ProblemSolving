import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static final int NOT_YET = 0;
    static final int TRUE = 1;
    static final int FALSE = 2;

    static int[] dp = new int[10000];
    static final int M = 4;
    static int[] arr;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        arr = new int[M];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int inputClockNum = Integer.MAX_VALUE;
        for (int i = 0; i < M; i++) {
            inputClockNum = Math.min(inputClockNum, readFrom(i));
        }

        int cnt = 0;
        for (int i = 1111; i < 10000; i++) {
            if (hasZero(i) || solveDP(i) == FALSE) continue;
            cnt++;
            if (i == inputClockNum) {
                sb.append(cnt);
                break;
            }
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static boolean hasZero(int num) {
        while (num > 0) {
            if (num % 10 == 0) {
                return true;
            }
            num /= 10;
        }
        return false;
    }

    private static int solveDP(int num) {
        if (dp[num] != NOT_YET) {
            return dp[num];
        }

        int minNum = num;
        int temp = num;
        dp[temp] = TRUE;
        for (int i = 0; i < 3; i++) {
            int lastDigit = temp % 10;
            temp = lastDigit * 1000 + (temp / 10);
            if (temp > minNum) {
                dp[temp] = FALSE;
            } else if (temp < minNum) {
                dp[minNum] = FALSE;
                minNum = temp;
            }
        }
        return dp[num];
    }

    private static int readFrom(int start) {
        int num = arr[start];
        for (int i = 1; i < 4; i++) {
            int idx = (start + i) % 4;
            num *= 10;
            num += arr[idx];
        }
        return num;
    }
}