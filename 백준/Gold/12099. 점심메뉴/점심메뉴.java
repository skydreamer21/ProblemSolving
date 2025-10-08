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
    static int N, Q;
    static Food[] foods;

    static class Food implements Comparable<Food> {
        int spicy, sweet;

        public Food(int spicy, int sweet) {
            this.spicy = spicy;
            this.sweet = sweet;
        }

        @Override
        public int compareTo(Food o) {
            return Integer.compare(this.spicy, o.spicy);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        foods = new Food[N];
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            foods[i] = new Food(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        Arrays.sort(foods);

        for (int i=0; i<Q; i++) {
//            System.out.printf("=== Query %d. ===\n", i+1);
            st = new StringTokenizer(br.readLine());
            int spicyL = Integer.parseInt(st.nextToken());
            int spicyR = Integer.parseInt(st.nextToken());
            int sweetL = Integer.parseInt(st.nextToken());
            int sweetR = Integer.parseInt(st.nextToken());

            if (spicyL > foods[N-1].spicy || spicyR < foods[0].spicy) {
                sb.append(0).append("\n");
                continue;
            }

            int[] spicyRange = findRangeByBs(spicyL, spicyR);

//            System.out.printf("spicyRange %d~%d: index(%d-%d)\n", spicyL, spicyR, spicyRange[0], spicyRange[1]);

            int cnt = 0;
            for (int j=spicyRange[0]; j<=spicyRange[1]; j++) {
                if (foods[j].sweet >= sweetL && foods[j].sweet <= sweetR) {
                    cnt++;
                }
            }

            sb.append(cnt).append("\n");

        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static int[] findRangeByBs(int spicyL, int spicyR) {
        int left = bsLower(spicyL);
        int right = bsUpper(spicyR);
        return new int[]{left, right};
    }

    private static int bsUpper(int target) {
//        System.out.printf("bsUpper) target : %d\n", target);
        int lo = 0;
        int hi = N;

        while(lo < hi) {
            int mid = (lo + hi) / 2;
//            System.out.printf("lo : %d, hi : %d, mid: %d\n", lo, hi, mid);
            if (foods[mid].spicy == target) {
                return mid;
            }
            else if (foods[mid].spicy > target) {
                hi = mid;
            } else {
                lo = mid+1;
            }
        }
        return Math.max(lo-1, 0);
    }

    private static int bsLower(int target) {
//        System.out.printf("bsLower) target : %d\n", target);
        int lo = 0;
        int hi = N-1;

        while (lo < hi) {
            int mid = (lo + hi) / 2;
//            System.out.printf("lo : %d, hi : %d, mid: %d\n", lo, hi, mid);
            if (foods[mid].spicy == target) {
                return mid;
            }
            else if (foods[mid].spicy > target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

}
