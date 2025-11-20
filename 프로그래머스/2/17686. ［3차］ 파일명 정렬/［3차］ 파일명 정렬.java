import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solution {
    
    static Word[] words;

    static class Word implements Comparable<Word> {
        String head, mid, tail;
        int order;

        public Word(String word, int order) {
            this.order = order;
            head = extract(word, "^[ a-zA-Z.-]+");
            word = word.substring(head.length());
            mid = extract(word, "^[0-9]{1,5}");
            tail = word.substring(mid.length());
        }

        private String extract(String word, String pattern) {
            Matcher m = Pattern.compile(pattern).matcher(word);
            while(m.find()) {
                return m.group();
            }
            throw new IllegalArgumentException("No Match");
        }

        @Override
        public int compareTo(Word o) {
            // System.out.printf("%s vs %s\n", this.toString(), o.toString());
            String h1 = this.getHead();
            String h2 = o.getHead();
            if (h1.equals(h2)) {
                int mid1 = this.getMid();
                int mid2 = o.getMid();
                if (mid1 == mid2) {
                    // System.out.println(1);
                    return this.order - o.order;
                } else {
                    // System.out.println(2);
                    return mid1 - mid2;
                }
            } else {
                // System.out.println(3);
                return h1.compareTo(h2);
            }
        }
        
        public String getHead() {
            return this.head.toLowerCase();
        }

        public int getMid() {
            return Integer.parseInt(mid);
        }

        @Override
        public String toString() {
            return head+mid+tail;
        }
    }
    
    public String[] solution(String[] files) {
        int N = files.length;
        words = new Word[N];
        for (int i=0; i<N; i++) {
            words[i] = new Word(files[i], i);
        }

        Arrays.sort(words);
        String[] answer = new String[N];
        for (int i=0; i<N; i++) {
            answer[i] = words[i].toString();
        }
        return answer;
    }
}