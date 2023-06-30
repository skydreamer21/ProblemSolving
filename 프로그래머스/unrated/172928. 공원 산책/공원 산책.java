import java.util.*;

class Solution {
    static final int[][] DIR = {{1,0}, {0,1}, {-1,0}, {0,-1}}; // S E N W
    static final char START = 'S';
    static final char EMPTY = 'O';
    static final char BLOCK = 'X';
    
    static int N, M;
    static char[][] map;
    static int posX, posY;
    
    public int[] solution(String[] park, String[] routes) {
        N = park.length;
        M = park[0].length();
        
        map = new char[N][M];
        for (int i=0 ;i<N; i++) {
            map[i] = park[i].toCharArray();
        }
        
        setStart();
        printPos();
        
        for (String route : routes) {
            int[] command = interpret(route);
            System.out.println(Arrays.toString(command));
            
            if (isMovable(command, posX, posY)) {
                System.out.println("move!");
                move(command);
                printPos();
            }
        }
        
        return new int[] {posX, posY};
    }
    
    public static void setStart() {
        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                if (map[i][j] == START) {
                    posX = i;
                    posY = j;
                    return;
                }
            }
        }
    }
    
    public static void move(int[] command) {
        posX += DIR[command[0]][0]*command[1];
        posY += DIR[command[0]][1]*command[1];
    }
    
    public static boolean isMovable(int[] command, int x, int y) {
        int com = command[1];
        while (com-- >0) {
            x += DIR[command[0]][0];
            y += DIR[command[0]][1];
            if (isNotInRange(x, y) || map[x][y] == BLOCK) {
                return false;
            }
        }
        return true;
    }
    
    public static int[] interpret(String commandStr) {
        int dir = -1;
        switch(commandStr.charAt(0)) {
            case 'S':
                dir = 0;
                break;
            case 'E':
                dir = 1;
                break;
            case 'N':
                dir = 2;
                break;
            case 'W':
                dir = 3;
                break;
        }
        return new int[]{dir, commandStr.charAt(2) - '0'};
    }
    
    public static boolean isNotInRange(int x, int y) {
        return x<0 || y<0 || x>=N || y>=M;
    }
    
    public static void printPos() {
        System.out.printf("pos : (%d, %d)\n", posX, posY);
    }
}