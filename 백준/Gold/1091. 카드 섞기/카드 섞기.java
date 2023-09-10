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
    static int N;
    static Card[] cards;
    static int[] shuffleOrder;

    static class Card implements Comparable<Card>{
        int originalOrder;
        int order;
        int dest;

        public Card(int originalOrder, int order, int dest) {
            this.originalOrder = originalOrder;
            this.order = order;
            this.dest = dest;
        }

        @Override
        public int compareTo(Card o) {
            return this.order - o.order;
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        cards = new Card[N];
        for (int i=0; i<N; i++) {
            cards[i] = new Card(i, i, Integer.parseInt(st.nextToken()));
        }

        shuffleOrder = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i=0; i<N; i++) {
            shuffleOrder[i] = Integer.parseInt(st.nextToken());
        }

        int count = 0;
        while(!check()) {
            count++;
            shuffle();
            if (isLoopExist()) {
                count = -1;
                break;
            }
        }

        sb.append(count);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static void shuffle() {
        for (int i=0; i<N; i++) {
            cards[i].order = shuffleOrder[i];
        }
        Arrays.sort(cards);
    }

    public static boolean check() {
        for (int i=0; i<N; i++) {
            if (cards[i].dest != i%3) return false;
        }
        return true;
    }

    public static boolean isLoopExist() {
        for (int i=0; i<N; i++) {
            if (cards[i].order != cards[i].originalOrder) return false;
        }
        return true;
    }
}