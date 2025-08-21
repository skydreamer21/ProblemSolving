
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static final int TOTAL = 24 * 60 * 60;
    static final int[] UNITS = {60*60, 60, 1};

    static class Manager {
        long [] prefixSum;

        public Manager() {
            prefixSum = new long[TOTAL + 1];
        }

        public void addData(String data) {
            int[] nums = parse(data);
            prefixSum[nums[0]] += 1;
            prefixSum[nums[1] + 1] -= 1;
            if (nums[0] > nums[1]) {
                prefixSum[TOTAL] -= 1;
                prefixSum[0] += 1;
            }
        }

        public void make() {
            int n = 10;
            makePrefixSum();
            makePrefixSum();

        }

        private void show(int n) {
            for (int i=0; i<n+1; i++) {
                System.out.printf("%d ", prefixSum[i]);
            }
            System.out.println();
        }

        public double getAnalyzedData(String data) {
            int[] nums = parse(data);
            long sum = prefixSum[nums[1]] - ( nums[0] == 0 ? 0 : prefixSum[nums[0]-1]);
            boolean overDay = nums[0] > nums[1];
            if (overDay) {
                sum += prefixSum[TOTAL];
            }
            int length = nums[1] - nums[0] + 1;
            if (overDay) length += TOTAL;
//            System.out.printf("sum : %d, length : %d, avg: %f\n", sum, length, (double) sum / length);
            // 8 ~ 14 => 8 ~ 9 + 0 ~ 4
            return (double) sum / length;
        }

        private void makePrefixSum() {
            for (int i=1; i<=TOTAL; i++) {
                prefixSum[i] += prefixSum[i-1];
            }
        }

        private int[] parse(String data) {
            String[] splits = data.split("-");
            int[] parsed = new int[]{strToInt(splits[0].trim()), strToInt(splits[1].trim())};
////            System.out.printf("parse %s\n", data);
//            System.out.println(Arrays.toString(parsed));
            return parsed;
        }

        private int strToInt(String time) {
            String[] splits = time.split(":");
            int value = 0;
            for (int i=0; i<splits.length; i++) {
                value += Integer.parseInt(splits[i]) * UNITS[i];
            }
            return value;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        Manager manager = new Manager();
        for (int i=0; i<N; i++) {
            manager.addData(br.readLine());
        }

        manager.make();

        int M = Integer.parseInt(br.readLine());
        for (int i=0; i<M; i++) {
            sb.append(manager.getAnalyzedData(br.readLine())).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
