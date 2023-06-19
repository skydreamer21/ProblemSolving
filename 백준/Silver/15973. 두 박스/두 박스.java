import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static final String NULL = "NULL";
    static final String POINT = "POINT";
    static final String FACE = "FACE";
    static final String LINE = "LINE";
    
    static Square s1, s2;
    
    static class Point {
        int x, y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public boolean isSame(Point o) {
            return this.x == o.x && this.y == o.y;
        }
    }
    static class Square {
        Point lb, rt; // leftBottom, rightTop
        
        public Square(Point lb, Point rt) {
            this.lb = lb;
            this.rt = rt;
        }
        
        public boolean isSeparatedSquare(Square o) {
            return (this.lb.y > o.rt.y) || (this.rt.y < o.lb.y) ||
                    (this.lb.x > o.rt.x) || (this.rt.x < o.lb.x);
        }
        
        public boolean hasCommonOuterLine(Square o) {
            return (this.lb.y == o.rt.y && (this.lb.x <= o.rt.x && this.rt.x >= o.lb.x))
                    || (this.rt.y == o.lb.y && (this.lb.x <= o.rt.x && this.rt.x >= o.lb.x))
                    || (this.lb.x == o.rt.x && (this.lb.y <= o.rt.y && this.rt.y >= o.lb.y))
                    || (this.rt.x == o.lb.x && (this.lb.y <= o.rt.y && this.rt.y >= o.lb.y));
        }
        
        private boolean containsOnLine(int start, int end, int target) {
            return target >= start && target <= end;
        }
        
        public boolean hasCommonOuterPoint(Square o) {
            return (this.lb.isSame(o.rt))
                    || (this.rt.isSame(o.lb))
                    || (this.lb.x == o.rt.x && this.rt.y == o.lb.y)
                    || (this.rt.x == o.lb.x && this.lb.y == o.rt.y);
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        s1 = new Square(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())),
                new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        st = new StringTokenizer(br.readLine());
        s2 = new Square(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())),
                new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        
        if (s1.isSeparatedSquare(s2)) { // 완전 분리되어 있다면
            sb.append(NULL);
        } else if (s1.hasCommonOuterPoint(s2)) { // 꼭지점이 바깥으로 겹친다면
            sb.append(POINT);
        } else if (s1.hasCommonOuterLine(s2)) { // 선이 바깥으로 겹친다면
            sb.append(LINE);
        } else {
            sb.append(FACE);
        }
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}