import java.io.*;
import java.util.*;

public class Main {
    static final int NOT_EXIST = -1;
    static int N;
    static Point start, end;
    static Circle[] circles;
    static HashSet<Integer> outerCircles;
    
    static class Point {
        int x, y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public int distanceSquare(Point o) {
            return (x - o.x) * (x - o.x) + (y - o.y) * (y - o.y);
        }
        
        public boolean isInCircle(Circle c) {
            return distanceSquare(c.center) < c.r * c.r;
        }
    }
    
    static class Circle implements Comparable<Circle> {
        Point center;
        int r;
        int index;
        
        public Circle(Point center, int r, int index) {
            this.center = center;
            this.r = r;
            this.index = index;
        }
        
        @Override
        public int compareTo(Circle o) {
            return this.r - o.r;
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        int T = Integer.parseInt(br.readLine());
        
        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            start = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            end = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            
            N = Integer.parseInt(br.readLine());
            circles = new Circle[N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                circles[i] = new Circle(
                        new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())),
                        Integer.parseInt(st.nextToken()),
                        i);
            }
            
            outerCircles = new HashSet<>();
            // start
            List<Circle> outerOfStart = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                if (start.isInCircle(circles[i])) {
                    outerOfStart.add(circles[i]);
                    outerCircles.add(i);
                }
            }
            Collections.sort(outerOfStart);
            
            // end
            List<Circle> outerOfEnd = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                if (end.isInCircle(circles[i])) {
                    outerOfEnd.add(circles[i]);
                }
            }
            Collections.sort(outerOfEnd);
            
            int interCircle = NOT_EXIST;
            int answer = 0;
            for (int i = 0; i < outerOfEnd.size(); i++) {
                if (outerCircles.contains(outerOfEnd.get(i).index)) {
                    answer += i;
                    interCircle = outerOfEnd.get(i).index;
                    break;
                }
            }
            
            if (interCircle == NOT_EXIST) {
                answer = outerOfStart.size() + outerOfEnd.size();
            } else {
                for (int i = 0; i < outerOfStart.size(); i++) {
                    if (outerOfStart.get(i).index == interCircle) {
                        answer += i;
                        break;
                    }
                }
            }
            
            sb.append(answer).append("\n");
        }
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}