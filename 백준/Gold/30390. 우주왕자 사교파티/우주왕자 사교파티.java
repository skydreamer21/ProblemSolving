import java.util.*;
import java.io.*;


public class Main {
    static long A, B, K, N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        A = Long.parseLong(st.nextToken());
        B = Long.parseLong(st.nextToken());
        K = Long.parseLong(st.nextToken());

        N = A+B;

        // A의 최소 최대 범위를 구하자
        long minA = Math.max(0, A - K);
        long maxA = Math.min(N, A+K);

        List<Integer> divisors = findDivisiors(N);
        long maxOfSmallerD = 0;
        long ans = -1;
        for (int d : divisors) {
            long biggerD = N / d;
            if (hasMultipleOf(biggerD, minA, maxA)) {
                ans = biggerD;
                break;
            }
            if (hasMultipleOf(d, minA, maxA)) {
                ans = d;
            }
        }

        sb.append(ans);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    // n 의 루트n이하의 약수들을 반환
    static List<Integer> findDivisiors(long n) {
        int sqrtN = (int) Math.sqrt(n);
        List<Integer> divisors = new ArrayList<>();
        for (int i=1; i<=sqrtN; i++) {
            if (n % i == 0) divisors.add(i);
        }
        return divisors;
    }

    // from이상 to이하의 범위에 n의 배수가 있는지 판단하는 함수
    static boolean hasMultipleOf(long n, long from, long to) {
        if (from > to) {
            throw new IllegalArgumentException("from은 to보다 작거나 같아야 합니다.");
        }
        long fromQ = from / n;
        long fromR = from % n;

        if (fromR == 0) return true;

        long toQ = to / n;
        long toR = to % n;

        if (toQ > fromQ) return true;

        // toR != 0 && toQ == fromQ
        return false;
    }
}