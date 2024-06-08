import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;

    static class Word implements Comparable<Word> {
        String word;
        int cnt;

        public Word(String word) {
            this.word = word;
            cnt = 0;
        }

        public void add() {
            cnt++;
        }

        @Override
        public int compareTo(Word o) {
            // 1. 자주 나오는 단어일수록
            if (this.cnt != o.cnt) {
                return o.cnt - this.cnt;
            }

            // 2. 해당 단어의 길이가 길수록
            if (this.word.length() != o.word.length()) {
                return o.word.length() - this.word.length();
            }

            // 3. 알파벳 사전 순으로 앞에 있는 단어일수록
            return this.word.compareTo(o.word);
        }
    }

    static class WordManager {
        Map<String, Word> wordMap;
        PriorityQueue<Word> pq;
        int minLength;

        public WordManager(int minLength) {
            this.minLength = minLength;
            wordMap = new HashMap<>();
            pq = new PriorityQueue<>();
        }

        public void addWord(String word) {
            if (word.length() < minLength) return;
            if (!wordMap.containsKey(word)) {
                wordMap.put(word, new Word(word));
            }
            wordMap.get(word).add();
        }

        public void makePriority() {
            pq.addAll(wordMap.values());
        }

        public Word getWord() {
            return pq.poll();
        }

        public boolean hasWordByPriority() {
            return !pq.isEmpty();
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

        WordManager wordManager = new WordManager(M);
        for (int i=0; i<N; i++) {
            wordManager.addWord(br.readLine());
        }

        wordManager.makePriority();
        
        while (wordManager.hasWordByPriority()) {
            sb.append(wordManager.getWord().word).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}