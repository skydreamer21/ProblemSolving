import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Predicate;

public class Main {
    static int N;
    static Farm farm;
    
    static class Farm {
        Wall[] walls;
        private int cnt;
        
        public Farm() {
            walls = new Wall[6];
            cnt = 0;
        }
        
        public void addWall(Wall wall) {
            walls[cnt++] = wall;
        }
        
        public int getArea() {
            return getBigSquareArea() - getSmallSquareArea();
        }
        
        private int getBigSquareArea() {
            int horIdx = getMaxWallIdx(this::isHorizontal);
            int verIdx = getMaxWallIdx(this::isVertical);
            return walls[horIdx].length * walls[verIdx].length;
        }
        
        private int getSmallSquareArea() {
            int horIdx = getMaxWallIdx(this::isHorizontal);
            int verIdx = getMaxWallIdx(this::isVertical);
            return getSmallAreaWallLength(horIdx) * getSmallAreaWallLength(verIdx);
        }
        
        private int getSmallAreaWallLength(int maxLengthIdx) {
            int prev = maxLengthIdx == 0 ? 5 : maxLengthIdx - 1;
            int next = maxLengthIdx == 5 ? 0 : maxLengthIdx + 1;
            return Math.abs(walls[prev].length - walls[next].length);
        }
        
        private int getMaxWallIdx(Predicate<Integer> isTargetWall) {
            int maxLength = -1;
            int idx = -1;
            
            for (int i = 0; i < 6; i++) {
                if (isTargetWall.test(walls[i].dir) && walls[i].length > maxLength) {
                    maxLength = walls[i].length;
                    idx = i;
                }
            }
            return idx;
        }
        
        private boolean isHorizontal(int dir) {
            return dir == 1 || dir == 2;
        }
        
        private boolean isVertical(int dir) {
            return dir == 3 || dir == 4;
        }
    }
    
    static class Wall {
        int dir, length;
        
        public Wall(int dir, int length) {
            this.dir = dir;
            this.length = length;
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        farm = new Farm();
        for (int i = 0; i < 6; i++) {
            st = new StringTokenizer(br.readLine());
            farm.addWall(new Wall(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }
        
        sb.append(farm.getArea() * N);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}