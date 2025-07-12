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
    static Set<Character>[] words;
    static int max = 0;
    static Set<Character> chosen;
    static Set<Character> base;
    static Character[] allCharacter = {
            'b', 'd', 'e',
            'f', 'g', 'h', 'j',
            'k', 'l', 'm', 'o',
            'p', 'q', 'r', 's',
            'u', 'v', 'w', 'x', 'y',
            'z'
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        if (M < 5) {
            sb.append(0);
        } else {
            words = new Set[N];
            base = new HashSet<>();
            base.add('a');
            base.add('c');
            base.add('i');
            base.add('n');
            base.add('t');
            for (int i = 0; i<N; i++) {
                String input = br.readLine();
                String mid = input.substring(4, input.length()-4);
                words[i] = new HashSet<>();
                for (char c : mid.toCharArray()) {
                    words[i].add(c);
                }
            }

            chosen = new HashSet<>();
            dfs(0, 0);
            sb.append(max);
        }


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static void dfs(int cnt, int start) {
//        System.out.printf("[IN] cnt : %d, start: %d\n", cnt, start);
        // start ~ 'z' 까지의 알파벳 개수 : 26 - start
        // 앞으로 선택해야 하는 알파벳 개수 : M-5 - cnt
        // 남은 알파벳 개수가 부족하면 안된다.
        if (21 - start < M-5 - cnt) return;
        if (cnt == M-5) {
            // 선택한 문자로 할 수 있는지 검사
            int c = 0;
            for (int i = 0; i<N; i++) {
                // 각 단어별
                boolean hasAll = true;
                for (char j : words[i]) {
                    if (!chosen.contains(j) && !base.contains(j)) {
                        hasAll = false;
                        break;
                    }
                }
                if (hasAll) c++;
            }
//            System.out.printf("chosen : %s, canRead: %d\n", chosen, c);
            max = Math.max(max, c);
            return;
        }

        // 단어 선택 (조합)
        for (int i = start; i<21; i++) {
            char alphabet = allCharacter[i];
            chosen.add(alphabet);
            dfs(cnt+1, i+1);
            chosen.remove(alphabet);
        }
    }

}
