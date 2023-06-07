import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static Pair[] pairs;
    
    static class Pair implements Comparable<Pair> {
        int doc, interview;
        
        public Pair(int doc, int interview) {
            this.doc = doc;
            this.interview = interview;
        }
        
        @Override
        public int compareTo(Pair o) {
            return this.doc - o.doc;
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            N = Integer.parseInt(br.readLine());
            pairs = new Pair[N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                pairs[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }
            
            Arrays.sort(pairs);
            int answer = 0;
            int interviewMax = N + 1;
            for (Pair pair : pairs) {
                if (pair.interview < interviewMax) {
                    answer++;
                    interviewMax = pair.interview;
                }
            }
            
            sb.append(answer).append("\n");
        }
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}