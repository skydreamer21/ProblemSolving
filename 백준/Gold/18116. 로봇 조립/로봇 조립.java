import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static final char INFO = 'I';
    static final int N = 1_000_000;

    static int[] parent;
    static int[] count;

    public static void init() {
        parent = new int[N+1];
        count = new int[N + 1];
        for (int i = 1; i<=N; i++) {
            parent[i] = i;
            count[i] = 1;
        }
    }

    public static int find(int a) {
        if (a == parent[a]) {
            return a;
        }
        return parent[a] = find(parent[a]);
    }

    public static void union(int a, int b) {
        int pa = find(a);
        int pb = find(b);

        if (pa == pb) return;

        int increasedCnt = count[pa] + count[pb];
        if (pa < pb) {
            parent[pb] = pa;
            count[pa] = increasedCnt;
        } else {
            parent[pa] = pb;
            count[pb] = increasedCnt;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        init();

        int q = Integer.parseInt(br.readLine());
        for (int i = 0; i< q; i++) {
            st = new StringTokenizer(br.readLine());
            char query = st.nextToken().charAt(0);
            if (query == INFO) {
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                union(a, b);
            } else {
                int c = Integer.parseInt(st.nextToken());
                sb.append(count[find(c)]).append("\n");
            }
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
