import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static final int MIN = -1_000_000_000;
    static final int MAX = 1_000_000_000;

    static int N, K;
    static int min = Integer.MAX_VALUE;
    static Point[] points;

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        static Point copy(Point point) {
            return new Point(point.x, point.y);
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        if (K == 1) {
            System.out.println(4);
            return;
        }

        points = new Point[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            points[i] = new Point(Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()));
        }

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                // i번째 점과 j번째 점을 대상으로
//                System.out.printf("===========%s, %s===========\n", points[i], points[j]);

                int[] xs = new int[]{Math.min(points[i].x, points[j].x),
                        Math.max(points[i].x, points[j].x)};
                int[] ys = new int[]{Math.min(points[i].y, points[j].y),
                        Math.max(points[i].y, points[j].y)};

                int h = xs[1] - xs[0] + 2;
                int w = ys[1] - ys[0] + 2;
//                System.out.printf("h : %d, w : %d\n", h, w);

                // 1. 세로 -> h가 한변의 길이
                if (h >= w && h < min) {
//                    System.out.printf("세로실행!!\n");
                    int yMin = (int) Math.max((long)ys[0] - 1 - (h - w), MIN-1);
                    int yMax = (int) Math.min((long)ys[1] + 1 + (h - w), MAX+1);
                    List<Point> pointsForVertical = findPointsByRect(xs[0]-1, yMin,
                            xs[1]+1, yMax);


                    pointsForVertical.sort(Comparator.comparingInt((Point p) -> p.y));
//                    System.out.printf("pointsForVertical : %s\n", pointsForVertical);

                    int left = 0;
                    int right = 0;
                    int len = pointsForVertical.size();
                    int pointContains = 1;

                    int width=2;
                    while (right < len - 1) {
                        int diff =
                                pointsForVertical.get(right + 1).y - pointsForVertical.get(right).y;

                        if (width + diff <= h) {
                            right++;
                            pointContains++;
                            if (pointContains >= K) {
//                                System.out.printf("min updated!! --> %d\n", h);
                                min = h;
                                break;
                            }
                            width += diff;
                        } else {
                            if (left == right) {
                                right++;
                                left++;
                            } else {
                                int diffLeft = pointsForVertical.get(left + 1).y - pointsForVertical.get(left).y;
                                width -= diffLeft;
                                pointContains--;
                                left++;
                            }
                        }
                    }

                }

                if (w > h && w < min) {
//                    System.out.printf("가로실행!!\n");
                    int xMin = (int) Math.max((long)xs[0] - 1 - (w - h), MIN-1);
                    int xMax = (int) Math.min((long)xs[1] + 1 + (w - h), MAX+1);
                    List<Point> pointsForHorizontal = findPointsByRect(xMin, ys[0]-1,
                            xMax, ys[1]+1);

                    pointsForHorizontal.sort(Comparator.comparingInt((Point p) -> p.x));
//                    System.out.printf("pointsForHorizontal : %s\n", pointsForHorizontal);

                    int left = 0;
                    int right = 0;
                    int len = pointsForHorizontal.size();
                    int pointContains = 1;

                    int width=2;
                    while (right < len - 1) {
                        int diff =
                                pointsForHorizontal.get(right + 1).x - pointsForHorizontal.get(right).x;

                        if (width + diff <= w) {
                            right++;
                            pointContains++;
                            if (pointContains >= K) {
//                                System.out.printf("min updated!! --> %d\n", w);
                                min = w;
                                break;
                            }
                            width += diff;
                        } else {
                            if (left == right) {
                                right++;
                                left++;
                            } else {
                                int diffLeft = pointsForHorizontal.get(left + 1).x - pointsForHorizontal.get(left).x;
                                width -= diffLeft;
                                pointContains--;
                                left++;
                            }
                        }
                    }
                }

            }
        }

        sb.append((long)min * min);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static List<Point> findPointsByRect(int x1, int y1, int x2, int y2) {
//        System.out.printf("find Rect in (%d, %d) ~ (%d, %d)\n", x1, y1, x2, y2);

        List<Point> pointsInRect = new ArrayList<>();
        for (Point point : points) {
            if (isInRect(x1, y1, x2, y2, point)) {
                pointsInRect.add(point);
            }
        }
        return pointsInRect;
    }

    private static boolean isInRect(int x1, int y1, int x2, int y2, Point point) {
        return point.x > x1 && point.x < x2
                && point.y > y1 && point.y < y2;
    }


}