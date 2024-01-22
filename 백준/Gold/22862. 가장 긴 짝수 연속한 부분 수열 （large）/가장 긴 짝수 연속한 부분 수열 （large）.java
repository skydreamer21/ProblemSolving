import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/*
[투 포인터]
1. 오른쪽 포인터를 삭제할 수 없는 첫 홀수가 나올 때까지 이동시킨다.
2. 오른쪽 포인터 - 왼쪽 포인터 로 길이를 세서 비교
3. 왼쪽 포인터를 다음으로 이동
 */

public class Main {
    static int N, M;
    static int[] arr;
    static int max = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        arr = new int[N];
        for (int i=0; i<N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int deletedCnt = 0;
        int left = 0;
        int right = 0;
        while (right < N) {
//            System.out.printf("left : %d, right : %d, deleted : %d, max : %d\n", left, right, deletedCnt, max);

            // 1. 오른쪽 포인터를 삭제할 수 없는 첫 홀수가 나올 때까지 이동시킨다.
            while (right < N && (deletedCnt < M || arr[right] % 2 == 0)) {
                if (arr[right] % 2 == 0) right++;
                else {
                    deletedCnt++;
                    right++;
                }
            }
            // 2. 오른쪽 포인터 - 왼쪽 포인터 로 길이를 세서 비교
            max = Math.max(right - left - deletedCnt, max);

            // 1-1. right 포인터가 끝이라면 끝
            if (right == N) {
                break;
            }

            // 3. 왼쪽 포인터를 다음으로 이동
            if (arr[left] % 2 == 1) deletedCnt--;
            left++;
        }


        sb.append(max);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}