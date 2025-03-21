import java.io.*;
import java.util.*;

public class Main {
    static int N, budget;

    static class PartsManager {
        Map<String, List<Part>> parts;

        public PartsManager() {
            parts = new HashMap<>();
        }

        public void add(String name, Part part) {
            if (!parts.containsKey(name)) {
                parts.put(name, new ArrayList<>());
            }
            parts.get(name).add(part);
        }

        // name이 같을 때 특정 성능 이상의 최소 가격인 부품 찾기
        public Part findTarget(String name, int minQ) {
            List<Part> targets = parts.get(name);
            int minP = Integer.MAX_VALUE;
            Part t = null;
            for (Part p : targets) {
                if (p.better(minQ) && p.cheaper(minP)) {
                    minP = p.price;
                    t = p;
                }
            }
            return t;
        }

        // 특정 성능 이상의 부품들을 예산 이하로 고르는 것이 가능한지
        public boolean isPossible(int targetQ) {
            Set<String> keys = parts.keySet();
            int sum = 0;
            for (String key : keys) {
                Part targetPart = findTarget(key, targetQ);
                if (targetPart == null) {
                    return false;
                }
                sum += targetPart.price;
                if (sum > budget) {
                    return false;
                }
            }
            return true;
        }

        // 전체 성능 범위에서 이분탐색으로 가능한 최대 성능 찾기
        // 가능한걸 만나면 올려서 찾아야함.
        public int findMax() {
            int lo = 0;
            int hi = 1_000_000_001; // 불가능한 첫번째
            int mid;

            while (lo < hi) {
                mid = (lo + hi) / 2;
                if (isPossible(mid)) {
                    lo = mid +1;
                } else {
                    hi = mid;
                }
            }
            return lo - 1;
        }

    }

    static class Part {
        int price;
        int qual;

        public Part(int price, int qual) {
            this.price = price;
            this.qual = qual;
        }

        public boolean better(int q) {
            return this.qual >= q;
        }

        public boolean cheaper(int p) {
            return this.price < p;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            PartsManager pm = new PartsManager();
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            budget = Integer.parseInt(st.nextToken());

            for (int i=0; i<N; i++) {
                st = new StringTokenizer(br.readLine());
                String name = st.nextToken();
                st.nextToken();
                int price = Integer.parseInt(st.nextToken());
                int qual = Integer.parseInt(st.nextToken());

                pm.add(name, new Part(price, qual));
            }

            sb.append(pm.findMax()).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
