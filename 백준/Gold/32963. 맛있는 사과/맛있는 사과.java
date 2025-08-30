import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int N, Q;
    static Apple[] apples;
    static AppleManager appleManager;
    static int[] answer;

    static class Apple implements Comparable<Apple> {
        int taste, size;

        public Apple(int taste) {
            this.taste = taste;
        }

        public void setSize(int size) {
            this.size = size;
        }

        @Override
        public int compareTo(Apple o) {
            return this.taste - o.taste;
        }
    }

    static class AppleManager {
        Map<Integer, Integer> countMap;
        PriorityQueue<Integer> sizePq;

        public AppleManager() {
            countMap = new HashMap<>();
            sizePq = new PriorityQueue<>((a, b) -> b-a);
        }

        public void add(int size) {
            if (countMap.containsKey(size)) {
                countMap.replace(size, countMap.get(size) + 1);
            } else {
                countMap.put(size, 1);
                sizePq.add(size);
            }
        }

        public int getCurrentMax() {
            while(countMap.get(sizePq.peek()) == 0) {
                sizePq.poll();
            }

            return countMap.get(sizePq.peek());
        }

        public void remove(int size) {
            countMap.replace(size, countMap.get(size) - 1);
        }

        public void show() {
            System.out.println("## countMap");
            for (Entry<Integer, Integer> entry : countMap.entrySet()) {
                System.out.printf("size : %d, num : %d\n", entry.getKey(), entry.getValue());
            }
            System.out.println("PQ");
            for (int t : sizePq) {
                System.out.printf("%d ", t);
            }
            System.out.println();
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

        apples = new Apple[N];

        st = new StringTokenizer(br.readLine());
        for (int i=0; i<N; i++) {
            apples[i] = new Apple(Integer.parseInt(st.nextToken()));
        }

        st = new StringTokenizer(br.readLine());
        for (int i=0; i<N; i++) {
            apples[i].setSize(Integer.parseInt(st.nextToken()));
        }

        answer = new int[N];
        Arrays.sort(apples);
        appleManager = new AppleManager();

        for (int i=0; i<N; i++) {
            appleManager.add(apples[i].size);
        }

//        appleManager.show();

        for (int i=0; i<N; i++) {
            answer[i] = appleManager.getCurrentMax();
            appleManager.remove(apples[i].size);
        }

        for (int i=0; i<Q; i++) {
            int p = Integer.parseInt(br.readLine());
            sb.append(p > apples[N-1].taste ? 0 : answer[bs(p)]).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    // n 이상의 맛을 가진 사과 중 가장 작은 사과
    public static int bs(int n) {
        int lo = 0;
        int hi = N-1;
        int mid;

        while (lo < hi) {
            mid = (lo + hi) / 2;
            // 만약 mid의 맛이 더 크다면? 답일 수도 있고 아닐 수도 있다.
            // 같다면? 같은 사과가 여러개 있을 수 있음.
            if (apples[mid].taste >= n) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

}
