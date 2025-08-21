import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static Set<Integer> squareNumbers;
    static int max = -1;
//    static int cnt = 0;

    static class Table {
        int[][]  table;

        public Table(int[][] table) {
            this.table = table;
        }

        public void search() {
            for (int r=0; r<N; r++) {
                for (int c=0; c<M; c++) {
//                    cnt = 0;
                    if (squareNumbers.contains(table[r][c])) {
                        max = Math.max(max, table[r][c]);
                    }
                    for (int dr = -N+1; dr < N; dr++) {
                        for (int dc = -M+1; dc < M; dc++) {
                            searchWithDiff(r, c, dr, dc);
                        }
                    }
//                    System.out.printf("(%d, %d) %d번\n", r, c, cnt);
                }
            }
        }

        private void searchWithDiff(int r, int c, int dr, int dc) {
            if (dr == 0 && dc == 0) return;
            int nextR = r + dr;
            int nextC = c + dc;
            int num  = table[r][c];
            while(isInRange(nextR, nextC)) {
//                cnt++;
                num *= 10;
                num += table[nextR][nextC];
                if (isSquare(num)) {
                    max = Math.max(max, num);
                }
                nextR += dr;
                nextC += dc;
            }
        }

        private boolean isSquare(int num) {
            return squareNumbers.contains(num);
        }

        private boolean isInRange(int r, int c) {
            return r >= 0 && c >= 0 && r < N && c < M;
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

        squareNumbers = new HashSet<>();
        for (int i=0; i<=34000; i++) {
            squareNumbers.add(i*i);
        }

        int[][] tab = new int[N][M];

        for (int i=0; i<N; i++) {
            char[] input = br.readLine().toCharArray();
            for (int j=0; j<M; j++) {
                tab[i][j] = input[j] - '0';
            }
        }

        Table table = new Table(tab);

        table.search();

        sb.append(max);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
