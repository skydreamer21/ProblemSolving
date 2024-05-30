import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static Counter counter;
    static int N;

    static class Counter {
        Deque<Integer> stack;
        int cnt;

        public Counter() {
            stack = new ArrayDeque<>();
            cnt = 0;
        }

        public void add(int height) {
            // 만약 stack의 탑보다 더 크다면 추가
            if (stack.isEmpty() || height > top()) {
                if(height == 0) return;
//                System.out.printf("%d stack에 추가!!\n", height);
                stack.addLast(height);
            } else if (height < top()) {
                // 작다면 top이 더 작아질 때까지 빼낸다.
                while (!stack.isEmpty() && height < top()) {
                    int h = stack.pollLast();
                    cnt++;
//                    System.out.printf("%d stack에서 빼고 건물 추가!!\n", h);
                }
                add(height);
            }
        }

        private int top() {
            return stack.peekLast();
        }

        public int total() {
//            System.out.printf("cnt : %d, size : %d\n", cnt, stack.size());
            return cnt + stack.size();
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        counter = new Counter();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            st.nextToken();
            counter.add(Integer.parseInt(st.nextToken()));
        }

        sb.append(counter.total());

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}