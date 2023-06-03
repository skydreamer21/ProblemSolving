import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        char[] input = br.readLine().toCharArray();
        int len = input.length;
        boolean isOpenBefore = false;
        int overlap = 0;
        int answer = 0;

        for (int i = 0; i < len; i++) {
            if (input[i] == '(') { // 쇠막대기의 시작 또는 레이저 시작
                overlap++;
                isOpenBefore = true;
            } else {
                overlap--;
                if (isOpenBefore) { // 레이저 발사
                    answer += overlap;
                    isOpenBefore = false;
                } else { // 가장 위에 있는 쇠막대기의 끝
                    answer++;
                }
            }
        }

        sb.append(answer);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}