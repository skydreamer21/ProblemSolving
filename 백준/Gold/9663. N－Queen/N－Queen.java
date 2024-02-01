import java.io.*;

public class Main {
    static final int FOUR_BITS_ON = 15;
    static int N;
    static long queens;
    static int visited;
    static int count;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        queens = 0;
        visited = 0;
        dfs(0, queens, visited);
        sb.append(count);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    private static void dfs(int depth, long queens, int visited) {
        if (depth == N) {
            count++;
            return;
        }

        for (int row=0; row<N; row++) {
            if (isVisited(visited, row)) continue;
            if (hasOtherQueenInDiagonal(queens, depth, row)) continue;
            dfs(depth + 1, setQueen(queens, depth, row), setVisited(visited, row));
        }
    }

    private static long setQueen(long queens, int index, int row) {
        return queens & ~((long) FOUR_BITS_ON << (index<<2)) | ((long) row << (index<<2));
    }

    private static int setVisited(int visited, int row) {
        return visited | (1 << row);
    }

    private static boolean isVisited(int visited, int row) {
        return (visited & (1<<row)) != 0;
    }

    private static boolean hasOtherQueenInDiagonal(long queens, int depth, int pos) {
        for (int i=0; i<depth; i++) {
            if (Math.abs(i - depth) == Math.abs(getQueenPosition(queens, i) - pos)) {
                return true;
            }
        }
        return false;
    }

    private static long getQueenPosition(long queens, int i) {
        return (queens >> (i<<2)) & FOUR_BITS_ON;
    }
}