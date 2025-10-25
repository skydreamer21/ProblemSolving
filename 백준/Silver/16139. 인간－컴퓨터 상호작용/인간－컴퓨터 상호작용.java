import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static char[] str;
    static int Q;
    static int[][] arr;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        str = br.readLine().toCharArray();
        int len = str.length;
        arr = new int[26][len + 1];
        for (int i=0; i<26; i++) {
            char target = (char)('a' + i);
            for (int j=1; j<=len; j++) {
                arr[i][j] = arr[i][j - 1] + (str[j - 1] == target ? 1 : 0);
            }
        }

        Q = Integer.parseInt(br.readLine());
        for (int i=0; i<Q; i++) {
            st = new StringTokenizer(br.readLine());
            int c = st.nextToken().charAt(0) - 'a';
            int l = Integer.parseInt(st.nextToken())+1;
            int r = Integer.parseInt(st.nextToken())+1;

            sb.append(arr[c][r] - arr[c][l - 1]).append("\n");

        }


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

}
