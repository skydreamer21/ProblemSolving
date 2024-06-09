import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        // 1. check range
        int lSquare = (x*x) + (y*y);
        int dSquare = d * d;

        if (lSquare > dSquare) {
            System.out.println("Impossible");
            return;
        } else if (lSquare == dSquare) {
            System.out.println("Single staircase");
            return;
        }

        // from now, only double staircase
        double tan = (double) Math.abs(y) / Math.abs(x);
        double rad = Math.atan(tan);
        double lobbyX = x + (x * Math.cos(rad));
        double lobbyY = y + (y * Math.sin(rad));
        double lobbyZ = (d - Math.sqrt(lSquare)) / 2;

        sb.append(lobbyX).append(" ")
                .append(lobbyY).append(" ")
                .append(lobbyZ);


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}