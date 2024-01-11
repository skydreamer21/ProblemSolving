import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    public static final long IMPOSSIBLE = -1L;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        long number = Long.parseLong(br.readLine());

        if (number == 1L) {
            sb.append("no");
        } else if (isPrimeNum(number) && isPrimeNum(rotateNumber(number))) {
            sb.append("yes");
        } else {
            sb.append("no");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static boolean isPrimeNum(long num) {
        if (num == IMPOSSIBLE) return false;

        int sqrt = (int) Math.sqrt(num);
        for (int i=2; i<=sqrt; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static long rotateNumber(long number) {
        long num = 0;
        while (number > 0) {
            int lastDigit = (int) (number%10);

            if (lastDigit == 6) {
                lastDigit = 9;
            } else if (lastDigit == 9) {
                lastDigit = 6;
            } else if (lastDigit == 3 | lastDigit == 4 | lastDigit == 7) {
//                System.out.println("IMPO");
                return IMPOSSIBLE;
            }

            num += lastDigit;
            num *= 10;

            number /= 10;
        }
//        System.out.println(num / 10);
        return num / 10;
    }

}