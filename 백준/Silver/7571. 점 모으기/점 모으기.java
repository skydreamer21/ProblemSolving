import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int X = 0;
    static final int Y = 1;

    static int N, M;
    static int[][] points;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        points = new int[2][M];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            points[X][i] = Integer.parseInt(st.nextToken());
            points[Y][i] = Integer.parseInt(st.nextToken());
        }

        int[] mid = new int[2];
        mid[X] = getMidPoint(points[X]);
        mid[Y] = getMidPoint(points[Y]);

        int sum = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < M; j++) {
                sum += Math.abs(mid[i] - points[i][j]);
            }
        }

        sb.append(sum);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static int getMidPoint(int[] points) {
        Arrays.sort(points);
        return points[M / 2];
    }
}