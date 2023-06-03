import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    static final int IMPOSSIBLE = -1;

    static int A, B;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());

        sb.append(bfs());

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static int bfs() {
        Deque<Integer> q = new ArrayDeque<>();
        q.add(A);
        HashSet<Integer> visited = new HashSet<>();
        visited.add(A);
        int cnt = 0;

        while (!q.isEmpty()) {
            cnt++;
            int size = q.size();
            while (size-- > 0) {
                int now = q.poll();
                int next;

                for (int i = 0; i < 2; i++) {
                    if (i==0) {
                        if ((long)now * 2 > Integer.MAX_VALUE) continue;

                        next = now * 2;
                    } else {
                        if ((long)now * 10 + 1 > Integer.MAX_VALUE) continue;
                        next = now * 10 + 1;
                    }
                    if (next == B) {
                        return cnt+1;
                    }
                    if (next > B || visited.contains(next)) continue;
                    q.add(next);
                    visited.add(next);
                }
            }
        }
        return IMPOSSIBLE;
    }
}