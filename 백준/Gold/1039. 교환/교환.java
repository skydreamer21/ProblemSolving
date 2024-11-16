import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static final int IMPOSSIBLE = -1;

    static int N, K, total;
    static Number sortedNumber;

    static class Number implements Comparable<Number>{
        int size;
        List<Integer> numbers;
        int lastFixedIdx;

        public Number(int n) {
            numbers = new ArrayList<>();
            while (n > 0) {
                numbers.add(n % 10);
                n /= 10;
            }
            Collections.reverse(numbers);
            size = numbers.size();
            lastFixedIdx = -1;
        }

        public Number(List<Integer> numbers, int lastFixedIdx) {
            this.numbers = numbers;
            size = numbers.size();
            this.lastFixedIdx = lastFixedIdx;
        }

        public Number swap(int i, int j) {
//            System.out.printf("%d <-> %d swap called from %s\n", i, j, this);

            // condition : i < j
            if (i >= j) {
                throw new IllegalArgumentException(String.format("swap index 순서 잘못됨 (%d, %d)", i, j));
            }
            List<Integer> swapList = new ArrayList<>(numbers);
            int temp = swapList.get(i);
            swapList.set(i, swapList.get(j));
            swapList.set(j, temp);
            return new Number(swapList, i);
        }

        public int getNumber() {
            int num = 0;
            for (int i = 0; i< size; i++) {
                num *= 10;
                num += numbers.get(i);
            }
            return num;
        }

        public int findNextSwapPos() {
            int idx = lastFixedIdx + 1;
            while (idx < total && isSortedPos(idx)) {
                idx++;
            }
            return idx;
        }

        public boolean isSortedPos(int pos) {
            return this.numbers.get(pos).equals(sortedNumber.numbers.get(pos));
        }

        public int get(int idx) {
            return numbers.get(idx);
        }

        @Override
        public int compareTo(Number o) {
            return o.getNumber() - this.getNumber();
        }

        @Override
        public String toString() {
            return String.format("< numbers: %s, lastFixed: %d >", numbers, lastFixedIdx);
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        Number start = new Number(N);
        total = start.size;

        if (total == 1 || (total == 2 && start.get(1) == 0)) {
            System.out.println(IMPOSSIBLE);
            return; // 프로그램 종료
        }

        sortedNumber = new Number(N);
        sortedNumber.numbers.sort((a, b) -> b - a);

        sb.append(bfs(start));

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static int bfs(Number start) {
        PriorityQueue<Number> q = new PriorityQueue<>();
//        Set<Integer> visited = new HashSet<>();
        q.add(start);
//        visited.add(start.getNumber());

        // hasSameNumber 구하기
        boolean hasSameNumber = false;
        int[] cnt = new int[10];
        for (int i : start.numbers) {
            if (++cnt[i] > 1) {
                hasSameNumber = true;
                break;
            }
        }
        PriorityQueue<Number> tempQ = null;
        int db_cnt = 0;
        while (K>0 && !q.isEmpty()) {
//            System.out.printf("%d번째 진입\n", ++db_cnt);
//            System.out.printf("현재 q : %s\n", q);

            int size = q.size();

            tempQ = new PriorityQueue<>();
            while (size-- >0) {
                Number now = q.poll();
//                System.out.printf("now : %s\n", now);
                int nextSwapIdx = now.findNextSwapPos();

                if (nextSwapIdx >= total-1) {
                    if (hasSameNumber) {
                        tempQ.add(now);
                    } else {
                        tempQ.add(now.swap(total-2, total-1));
                    }
                    continue;
                }

                for (int i = nextSwapIdx + 1; i <total; i++) {
                    // i번째 숫자가 nextSwapIdx 에 있어야 할 숫자랑 같다면
                    if (now.get(i) == sortedNumber.get(nextSwapIdx)) {
                        tempQ.add(now.swap(nextSwapIdx, i));
                    }
                }
            }
            K--;
            q = tempQ;
        }
        if (q.isEmpty()) {
            throw new IllegalArgumentException(String.format("pq가 비어있습니다. K=%d", K));
        }
        return q.poll().getNumber();
    }
}