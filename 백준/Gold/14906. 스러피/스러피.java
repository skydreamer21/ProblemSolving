import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static final int IMPOSSIBLE = -1;

    public static boolean isSlufy(String pattern) {
        int length = pattern.length();
//        System.out.printf("*****\n");
        int endOfSlimp = startWithSlimp(pattern, 0);
//        System.out.printf("eos1 : %d\n", endOfSlimp);
        if (endOfSlimp == IMPOSSIBLE || endOfSlimp == length-1) return false;
//        System.out.printf("$$$$$\n");
        int endOfSlump = startWithSlump(pattern, endOfSlimp + 1);
//        System.out.printf("eos2 : %d\n", endOfSlump);
        if (endOfSlump == IMPOSSIBLE || endOfSlump != pattern.length()-1 ) return false;
        return true;
    }

    public static int startWithSlimp(String pattern, int startIdx) {
//        System.out.printf("## [S2] %s, idx : %d(%c)\n", pattern, startIdx, pattern.charAt(startIdx));
        int leftLength = pattern.length() - startIdx;
        int length = pattern.length();

        // 2개 미만 이라면 아니다.
        if (leftLength < 2) {
//            System.out.printf("[FAIL] 2 미만.\n");
            return IMPOSSIBLE;
        }

        int now = startIdx;

        // 첫번째 문자는 A이다.
        if (pattern.charAt(now) != 'A') {
            return IMPOSSIBLE;
        }
        now++;

//        System.out.printf("첫번째 문자 통과\n");
        // 2번째 문자가 H라면 이 위치를 반환한다.
        if (pattern.charAt(now) == 'H') {
            return now;
        } else if (pattern.charAt(now) == 'B') {
//            System.out.printf("두번째 스림프 검사 시작\n");
            now = startWithSlimp(pattern, now+1);
        } else {
//            System.out.printf("두번째 스럼프 검사 시작\n");
            now = startWithSlump(pattern, now);
        }

        if (now == IMPOSSIBLE) return IMPOSSIBLE;
        now++;

        // 마지막은 C로 끝난다.
        if (now < length && pattern.charAt(now) == 'C') return now;

//        System.out.printf("[FAIL] 마지막이 C로 안끝남.\n");
        return IMPOSSIBLE;
    }

    public static int startWithSlump(String pattern, int startIdx) {
//        System.out.printf("## [S1] %s, idx : %d(%c)\n", pattern, startIdx, pattern.charAt(startIdx));
        int leftLength = pattern.length() - startIdx;
        int length = pattern.length();

        // 남은 문자 수가 3개 미만이라면 아니다.
        if (leftLength < 3) {
//            System.out.printf("[FAIL] 3 미만.\n");
            return IMPOSSIBLE;
        }


        int now = startIdx;

        // 첫 번째 글자는 D 또는 E이다.
        char first = pattern.charAt(now);
        if (first != 'D' && first != 'E') return IMPOSSIBLE;
//        System.out.printf("첫번째 문자 통과\n");
        now++;

        // 한 개 이상의 F가 나와야 한다.
        int before = now;
        while(now < length && pattern.charAt(now) == 'F') {
            now++;
        }
        if (before == now || now == length ) {
//            System.out.printf("[FAIL] F가 없거나 문자 끝 도달.\n");
            return IMPOSSIBLE;
        }

        // G 가 나온다면 해당 인덱스 반환
        if (pattern.charAt(now) == 'G') return now;

        // G 가 아니라면 다음 슬럼프 판단에 넘긴다.
//        System.out.printf("스럼프 안 스럼프 검사 시작\n");
        return startWithSlump(pattern, now);

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        sb.append("SLURPYS OUTPUT").append("\n");
        while(N-- >0) {
//            System.out.printf("===================\n");
            String pattern = br.readLine();
            sb.append(isSlufy(pattern) ? "YES" : "NO").append("\n");
        }
        sb.append("END OF OUTPUT");

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
