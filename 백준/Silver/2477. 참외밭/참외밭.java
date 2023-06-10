import java.io.*;
import java.util.StringTokenizer;
import java.util.function.Predicate;

public class Main {
    static int N;
    static Farm farm;
    
    static class Farm {
        Wall[] walls;
        private int cnt;
        private int maxHorIdx, maxHor;
        private int maxVerIdx, maxVer;
        
        public Farm() {
            walls = new Wall[6];
            cnt = 0;
        }
        
        public void addWall(Wall wall) {
            walls[cnt] = wall;
            if (isHorizontal(wall.dir)) {
                if (wall.length > maxHor) {
                    maxHor = wall.length;
                    maxHorIdx = cnt;
                }
            } else {
                if (wall.length > maxVer) {
                    maxVer = wall.length;
                    maxVerIdx = cnt;
                }
            }
            cnt++;
        }
        
        public int getArea() {
            return getBigSquareArea() - getSmallSquareArea();
        }
        
        private int getBigSquareArea() {
            return walls[maxHorIdx].length * walls[maxVerIdx].length;
        }
        
        private int getSmallSquareArea() {
            return getSmallAreaWallLength(maxHorIdx) * getSmallAreaWallLength(maxVerIdx);
        }
        
        private int getSmallAreaWallLength(int maxLengthIdx) {
            int prev = maxLengthIdx == 0 ? 5 : maxLengthIdx - 1;
            int next = maxLengthIdx == 5 ? 0 : maxLengthIdx + 1;
            return Math.abs(walls[prev].length - walls[next].length);
        }
        
        private boolean isHorizontal(int dir) {
            return dir == 1 || dir == 2;
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