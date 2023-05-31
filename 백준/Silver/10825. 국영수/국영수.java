import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static final int KOREAN = 0;
    static final int MATH = 1;
    static final int ENGLISH = 2;
    
    static int N;
    static List<Grade> grades;
    
    static class Grade implements Comparable<Grade>{
        String name;
        int[] scores;
        
        public Grade(String name, int kor, int eng, int math) {
            this.name = name;
            scores = new int[3];
            scores[KOREAN] = kor;
            scores[ENGLISH] = eng;
            scores[MATH] = math;
        }
        
        @Override
        public int compareTo(Grade o) {
            // 1. 국어 성적 내림차순
            if (this.scores[KOREAN] != o.scores[KOREAN]) {
                return o.scores[KOREAN] - this.scores[KOREAN];
            }
            
            // 2. 영어 성적 오름차순
            if (this.scores[ENGLISH] != o.scores[ENGLISH]) {
                return this.scores[ENGLISH] - o.scores[ENGLISH];
            }
            
            // 3. 수학 성적 내림차순
            if (this.scores[MATH] != o.scores[MATH]) {
                return o.scores[MATH] - this.scores[MATH];
            }
            
            return this.name.compareTo(o.name);
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        
        grades = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            grades.add(
                    new Grade(
                            st.nextToken(),
                            Integer.parseInt(st.nextToken()),
                            Integer.parseInt(st.nextToken()),
                            Integer.parseInt(st.nextToken())
                    )
            );
        }
        
        grades.stream()
                .sorted()
                .forEach(grade -> sb.append(grade.name).append("\n"));
        
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}