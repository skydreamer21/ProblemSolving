import java.io.*;
import java.util.*;

public class Main {
    static final int NOT_EXIST = -1;
    static final String YES = "YES";
    static final String NO = "NO";

    static int N;
    static int[] arr;

    static class UDPStacks {
        final int NOT_EXIST = -1;

        Stack[] stacks;
        boolean[] hasStack;
        int size;

        public UDPStacks() {
            stacks = new Stack[2];
            hasStack = new boolean[2];
            size = 0;
        }

        public boolean addNumber(int num) {
            int existPossibleStack = findStackForNewNumber(num-1);
            if (existPossibleStack != NOT_EXIST) {
                stacks[existPossibleStack].last = num;
                return true;
            }

            if (size < 2) {
                for (int i=0; i<2; i++) {
                    if (!hasStack[i]) {
                        stacks[i] = new Stack(num);
                        hasStack[i] = true;
                        size++;
                        return true;
                    }
                }
                throw new IllegalArgumentException("size가 충분한데 add 불가");
            }

            return false;
        }

        public int findStackForNewNumber(int num) {
            for (int i=0; i<2; i++) {
                if (hasStack[i] && stacks[i].last == num) {
                    return i;
                }
            }
            return NOT_EXIST;
        }

        public int findByFirstNumber(int num) {
            for (int i=0; i<2; i++) {
                if (hasStack[i] && stacks[i].first == num) {
                    return i;
                }
            }
            return NOT_EXIST;
        }

        public int removeStack(int firstNum) {
            int stackWithFirstNum = findByFirstNumber(firstNum);
            if (stackWithFirstNum == NOT_EXIST) return NOT_EXIST;

            hasStack[stackWithFirstNum] = false;
            size--;
            return stacks[stackWithFirstNum].last;
        }
    }

    static class Stack {
        int first, last;

        public Stack(int f, int l) {
            this.first = f;
            this.last = l;
        }

        public Stack(int num) {
            this.first = num;
            this.last = num;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        while (T-- >0) {
            int N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            arr = new int[N];
            for (int i = 0; i<N; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }

            int nextOut = 1;
            String answer = YES;
            UDPStacks udpStacks = new UDPStacks();

            for (int i=0; i<N; i++) {
                int now = arr[i];
//                System.out.printf("@#$ now : %d, nextOut : %d\n", now, nextOut);

                // 현재 바로 내보낼 수 있는 수
                if (now == nextOut) {
                    nextOut++;
//                    System.out.printf("바로 빼냄! nextOut : %d\n", nextOut);

                    // 빼냈다면 스택에 저장되어 있는 수중 뺄 수 있는 수를 다시 검증해야함.
                    int result = udpStacks.removeStack(nextOut);
                    if (result == NOT_EXIST) {
//                        System.out.printf("바로 빼내고 스택에서 뺄 것이 없음\n");
                        continue;
                    }
                    nextOut = result+1;
//                    System.out.printf("바로 빼내고 스택에서 한번 뺌, nextOut : %d\n", nextOut);
                    result = udpStacks.removeStack(nextOut);
                    nextOut = result == NOT_EXIST ? nextOut : result + 1;
//                    System.out.printf("두번째 뺀 결과, nextOut : %d\n", nextOut);
                    continue;
                }

                // 그렇지 않은 수라면 save
                boolean result = udpStacks.addNumber(now);
                if (!result) {
//                    System.out.printf("저장 실패..\n");
                    answer = NO;
                    break;
                } else {
//                    System.out.printf("%d 저장 성공\n", now);
                }
            }

            sb.append(answer).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}