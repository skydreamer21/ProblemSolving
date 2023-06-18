import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static class Editor {
        Deque<Character> input;
        int cursor;
        int length;
        
        public Editor(String initInput) {
            int len = initInput.length();
            char[] inputChars = initInput.toCharArray();
            input = new ArrayDeque<>();
            for (char c : inputChars) {
                input.add(c);
            }
            cursor = len;
            length = len;
        }
        
        public void left() {
            if (cursor == 0) return;
            input.addFirst(input.pollLast());
            cursor--;
        }
        
        public void right() {
            if (cursor == length) return;
            input.addLast(input.poll());
            cursor++;
        }
        
        public void backspace() {
            if (cursor == 0) return;
            input.pollLast();
            cursor--;
            length--;
        }
        
        public void add(char c) {
            input.add(c);
            cursor++;
            length++;
        }
        
        public String getResult() {
            char[] output = new char[length];
            int idx = cursor == length ? 0 : cursor;
            for (int i = 0; i < length; i++) {
                output[idx] = input.peek();
                idx = (idx + 1) % length;
                input.addLast(input.poll());
            }
            return String.valueOf(output);
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        Editor editor = new Editor(br.readLine());
        int N = Integer.parseInt(br.readLine());
        
        String inputCommand;
        while (N-- > 0) {
            inputCommand = br.readLine();
            switch (inputCommand.charAt(0)) {
                case 'L':
                    editor.left();
                    break;
                case 'D':
                    editor.right();
                    break;
                case 'B':
                    editor.backspace();
                    break;
                case 'P':
                    editor.add(inputCommand.charAt(2));
                    break;
            }
        }
        
        sb.append(editor.getResult());
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}