import java.io.*;

public class Main {
    static final int DIV = 10_007;
    
    static int N;
    static int[] arr;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        N = Integer.parseInt(br.readLine());
        arr = new int[2];
        
        arr[0] = 3; // A(1)
        arr[1] = 1; // A(2)
        
        // 홀수는 1, 짝수는 0에 저장
        for (int i = 3; i <= N; i++) {
            int temp = arr[i % 2];
            arr[i % 2] = (arr[0] + arr[1] + temp) % DIV;
        }
        
        sb.append(arr[N % 2]);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
