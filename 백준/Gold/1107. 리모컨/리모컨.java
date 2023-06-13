import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static final int INIT_CHANNEL = 100;
    
    private static final int ERROR = -1;
    static int N, M;
    static boolean[] isNumberPossible;
    static int max, min;
    static int possibleNumberBtns;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        isNumberPossible = new boolean[10];
        possibleNumberBtns = 10 - M;
        if (possibleNumberBtns == 0) {
            System.out.println(Math.abs(INIT_CHANNEL - N));
            return;
        }
        Arrays.fill(isNumberPossible, true);
        
        if (M > 0) {
            st = new StringTokenizer(br.readLine());
            while (st.hasMoreElements()) {
                isNumberPossible[Integer.parseInt(st.nextToken())] = false;
            }
        }
        
        // min 숫자 설정
        for (int i = 0; i < 10; i++) {
            if (isNumberPossible[i]) {
                min = i;
                break;
            }
        }
        
        // max 숫자 설정
        for (int i = 9; i >=0; i--) {
            if (isNumberPossible[i]) {
                max = i;
                break;
            }
        }
        
        int answer = Math.abs(N - INIT_CHANNEL);
        int minDiffNum = getMinDiffNumber(N);
        answer = Math.min(answer, getNumberLength(minDiffNum) + Math.abs(N - minDiffNum));
        sb.append(answer);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    public static int getMinDiffNumber(int n) {
        int[] numberArr = convert(n);
        int smallerNumber = getMinDiffSmallerNumber(numberArr);
        int biggerNumber = getMinDiffBiggerNumber(numberArr);
        if (smallerNumber == 0 && !isNumberPossible[0]) {
            return biggerNumber;
        }
        if (biggerNumber - n < n - smallerNumber) {
            return biggerNumber;
        } else {
            return smallerNumber;
        }
    }
    
    private static int getMinDiffBiggerNumber(int[] numberArr) {
        int resultNum = 0;
        for (int i = 0; i < numberArr.length; i++) {
            if (isNumberPossible[numberArr[i]]) { // 가능하면 그대로 쓴다.
                resultNum *= 10;
                resultNum += numberArr[i];
            } else if (numberArr[i] > max) { // 불가능한데 그게 9라면 앞자리를 올린다.
                int possibleNum = modifyBigNumber(resultNum);
                return makeNumber(possibleNum, min, numberArr.length - i);
            } else { // 불가능한데 max 이하라면
                int nextPossibleNum = findNextBiggerPossibleNumber(numberArr[i]);
                
                if (nextPossibleNum == ERROR) {
                    System.out.println("비정상적인 실행");
                }
                
                resultNum *= 10;
                resultNum += nextPossibleNum;
                return makeNumber(resultNum, min, numberArr.length - 1 - i);
            }
        }
        return resultNum;
    }
    
    private static int modifyBigNumber(int n) {
        int length = getNumberLength(n);
        
        // 1. 뒷자리부터 확인해서 max보다 작은 자릿수를 알아낸다.
        int cnt = 0;
        int digit = n % 10;
        while (cnt < length && digit >= max) {
            cnt++;
            n /= 10;
            digit = n % 10;
        }
        
        if (cnt == length) {
            if (min == 0 && possibleNumberBtns > 1) {
                return makeNumber(findNextBiggerPossibleNumber(0), min, length);
            }
            return makeNumber(0, min, length + 1);
        } else {
            int lastDigit = n % 10;
            int nextPossibleNum = findNextBiggerPossibleNumber(lastDigit);
            n += (nextPossibleNum - lastDigit);
            return makeNumber(n, min, cnt);
        }
    }
    
    private static int findNextBiggerPossibleNumber(int d) {
        for (int i = d+1; i < 10; i++) {
            if (isNumberPossible[i]) {
                return i;
            }
        }
        return ERROR;
    }
    
    private static int getMinDiffSmallerNumber(int[] numberArr) {
        int resultNum = 0;
        for (int i = 0; i < numberArr.length; i++) {
            if (isNumberPossible[numberArr[i]]) { // 가능하면 그대로 쓴다.
                resultNum *= 10;
                resultNum += numberArr[i];
            } else if (numberArr[i] < min) { //
                if (resultNum == 0) {
                    return makeNumber(0, max, numberArr.length - 1);
                }
                int possibleNum = modifySmallNumber(resultNum);
                return makeNumber(possibleNum, max, numberArr.length - i);
            } else { // 불가능한데 max 이하라면
                int nextPossibleNum = findNextSmallerPossibleNumber(numberArr[i]);
                
                if (nextPossibleNum == ERROR) {
                    System.out.println("비정상적인 실행");
                }
                
                resultNum *= 10;
                resultNum += nextPossibleNum;
                return makeNumber(resultNum, max, numberArr.length - 1 - i);
            }
        }
        return resultNum;
    }
    
    private static int modifySmallNumber(int n) {
        int length = getNumberLength(n);
        
        // 1. 뒷자리부터 확인해서 min보다 큰 자릿수를 알아낸다.
        int cnt = 0;
        int digit = n % 10;
        while (cnt < length && digit <= min) {
            cnt++;
            n /= 10;
            digit = n % 10;
        }
        
        if (cnt == length) {
            return makeNumber(0, max, length -1);
        } else {
            int lastDigit = n % 10;
            int nextPossibleNum = findNextSmallerPossibleNumber(lastDigit);
            n -= (lastDigit - nextPossibleNum);
            return makeNumber(n, max, cnt);
        }
    }
    
    private static int findNextSmallerPossibleNumber(int d) {
        for (int i = d-1; i >=0; i--) {
            if (isNumberPossible[i]) {
                return i;
            }
        }
        return ERROR;
    }
    
    private static int makeNumber(int n, int num, int length) {
        while (length-- > 0) {
            n *= 10;
            n += num;
        }
        return n;
    }
    
    public static int[] convert(int n) {
        int length = getNumberLength(n);
        int[] numberArr = new int[length];
        int idx = length - 1;
        while (n > 0) {
            numberArr[idx--] = n % 10;
            n /= 10;
        }
        return numberArr;
    }
    
    public static int getNumberLength(int n) {
        if (n == 0) {
            return 1;
        }
        return (int) Math.log10(n) + 1;
    }
}