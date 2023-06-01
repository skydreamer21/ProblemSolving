import java.io.*;
import java.util.HashMap;

public class Main {
    static int N;
    static HashMap<String, Integer> files = new HashMap<>();
    static StringBuilder sb = new StringBuilder();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        N = Integer.parseInt(br.readLine());
        while (N-- > 0) {
            addExt(br.readLine().split("\\.")[1]);
        }
        
        files.keySet().stream()
                .sorted()
                .forEach(Main::printExt);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static void addExt(String ext) {
        if (files.containsKey(ext)) {
            files.replace(ext, files.get(ext) + 1);
        } else {
            files.put(ext, 1);
        }
    }
    
    private static void printExt(String ext) {
        sb.append(ext).append(" ").append(files.get(ext)).append("\n");
    }
}