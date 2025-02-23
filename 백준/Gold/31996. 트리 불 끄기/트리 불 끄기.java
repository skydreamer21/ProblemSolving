import java.util.*;
import java.io.*;

public class Main {
    static int N, M;
    static boolean[] light;
    static Graph g;
    static List<Integer> answer = new ArrayList<>();
    static int lightCount = 0;

    static class Node {
        int id;
        Set<Node> remains;
        Node from;

        public Node(int id) {
            this.id = id;
            remains = new HashSet<>();
        }

        public void addEdge(Node node) {
            remains.add(node);
        }

        public void setFrom(Node node) {
            remains.remove(node);
            from = node;
        }

        public boolean hasNode(Node node) {
            return remains.contains(node);
        }

        public boolean hasNode(int id) {
            Node n = new Node(id);
            return remains.contains(n);
        }

        public boolean isLeaf() {
            return remains.isEmpty();
        }

        public Node next() {
            return remains.iterator().next();
        }

        public boolean isLightOn() {
            return light[this.id];
        }

        public void toggle() {
            if (isLightOn()) {
                lightCount--;
                light[this.id] = false;
            } else {
                lightCount++;
                light[this.id] = true;
            }
        }

        public void remove(Node node) {
            remains.remove(node);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || this.getClass() != obj.getClass() ) return false;

            Node other = (Node) obj;
            return this.id == other.id;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(id);
        }
    }

    static class Graph {
        int N;
        Node[] nodes;

        public Graph(int N) {
            this.N = N;
            nodes = new Node[N+1];
            for (int i=1; i<=N; i++) {
                nodes[i] = new Node(i);
            }
        }

        public void addEdge(int n1, int n2) {
            nodes[n1].addEdge(nodes[n2]);
            nodes[n2].addEdge(nodes[n1]);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        light = new boolean[N+1];

        char[] lightStatusInput = br.readLine().toCharArray();
        for (int i=0; i<N; i++) {
            if (lightStatusInput[i] - '0' == 0) {
                light[i+1] = false;
            } else {
                light[i+1] = true;
                lightCount++;
            }
        }

        g = new Graph(N);

        // graph 생성
        for (int i=0; i<N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            g.addEdge(n1, n2);
        }

        Node now = g.nodes[M];

        // start node의 인접한 노드 하나
        int oneOfStartNext = now.next().id;

        while (lightCount != 0) {
//            System.out.println("-------------------------------");
//            System.out.printf("[IN] now : %d, lightCount : %d\n", now.id, lightCount);
//            printLightStatus();
            // 0. remain이 비어있다면 from으로 돌아가기
            if (now.isLeaf()) {
                if (now.from == null) {
                    // 시작점으로 돌아왔다는 뜻. 그런데 시작점은 켜져 있음. 1개만 켜져 있는 상황
                    answer.add(oneOfStartNext);
                    lightCount--;
//                    System.out.printf("[OUT - return to START] now : %d\n", now.id);
                    continue;
                }

                now.toggle();
                now = now.from;
                answer.add(now.id);
//                System.out.printf("[OUT - return to from] now : %d\n", now.id);
                continue;
            }

            // 1. remain에서 하나 뽑기
            Node next = now.next();
//            System.out.printf("[NEXT] next : %d\n", next.id);
            next.setFrom(now);

            // next leaf, 꺼져있을 때는 안감
            if (next.isLeaf()) {
                now.remove(next);
                if (!next.isLightOn()) {
//                    System.out.printf("[OUT - Next OFF Leaf, stay now] now : %d\n", now.id);
                    continue;
                }
            }

            answer.add(next.id);
            now.toggle();

            // 뽑은 노드가 켜져있는 리프노드일 때 
            if (next.isLeaf()) {
//                System.out.printf("[NEXT LEAF - ON] now : %d\n", now.id);
                answer.add(now.id);
                next.toggle();
//                System.out.printf("[OUT - Leaf Node Processed, stay now ] now : %d\n", now.id);
                continue;
            }

            // 뽑은 노드가 리프노드가 아닐 때
//            System.out.printf("[OUT - go to branch %d ] now : %d\n", next.id, now.id);
            now = next;
            continue;
        }

//        System.out.println("============= [Result] =============");
//        System.out.printf("lightCount : %d\n", lightCount);
//        printLightStatus();

        sb.append(answer.size()).append("\n");
        for (int n : answer) {
            sb.append(n).append(" ");
        }


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    static void printLightStatus() {
        System.out.println("< LIGHT STATUS >");
        for (int i=0; i<N; i++) {
            System.out.printf("%5d", i+1);
        }
        System.out.println();
        for (int i=0; i<N; i++) {
            System.out.printf("%5s", light[i+1] ? "ON" : "OFF");
        }
        System.out.println();
        System.out.println();
    }
}