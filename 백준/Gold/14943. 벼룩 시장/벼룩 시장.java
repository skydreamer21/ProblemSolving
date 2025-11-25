import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

    static class Node {
        int index;
        int value;

        public Node(int index, int value) {
            this.index = index;
            this.value = value;
        }
    }

    static class Queue {
        Deque<Node> q;
        long cost;

        public Queue() {
            q = new ArrayDeque<>();
        }

        public void add(int index, int value) {
            //System.out.printf("=== PUT %d\n", index);
            // 맨 위와 같은 부호라면 맨 뒤에 넣는다.
            if (q.isEmpty() || q.peek().value * value > 0) {
                //System.out.println("same");
                q.add(new Node(index, value));
            } else {
                //System.out.println("diff");
                // 다른 부호라면 어느 한쪽이 떨어질때까지 앞에서부터 차례로 뺀다.
                while (!q.isEmpty() && Math.abs(value) >= Math.abs(q.peek().value)) {
                    Node polled = q.poll();
                    cost += (long) Math.abs(polled.value) * (index - polled.index);
                    value += polled.value;
                }
                if (value == 0) return;
                if (q.isEmpty()) {
                    q.add(new Node(index, value));
                } else {
                    cost += (long) Math.abs(value) * (index - q.peek().index);
                    q.peek().value += value;
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        Queue q = new Queue();
        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i=0; i<N; i++) {
            int value = Integer.parseInt(st.nextToken());
            q.add(i, value);
        }

        if (!q.q.isEmpty()) {
            throw new IllegalArgumentException("모두 넣었을 때 큐는 비어있어야 합니다.");
        }

        sb.append(q.cost);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
