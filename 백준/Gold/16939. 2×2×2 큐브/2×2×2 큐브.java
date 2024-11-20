import java.io.*;
import java.util.*;

public class Main {
    static final int FACE_COLOR_NUM = 4;
    static final int FACE_NUM = 6;
    static final int NOT_SAME = -1;
    static final boolean ROW = true;
    static final boolean COLUMN = false;
    static final int UP = 0;
    static final int FRONT = 1;
    static final int DOWN = 2;
    static final int LEFT = 3;
    static final int RIGHT = 4;
    static final int BACK = 5;
    static final int FORWARD = 1;
    static final int BACKWARD = -1;

    static class Face {
        int[] colors;

        public Face(int c1, int c2, int c3, int c4) {
            colors = new int[FACE_COLOR_NUM];
            colors[0] = c1;
            colors[1] = c2;
            colors[2] = c3;
            colors[3] = c4;
        }

        /**
         *
         * @param isRow row(true), column(false)
         * @param index 1 or 2
         * @return color, if different -1
         */
        public int getLineColor(boolean isRow, int index) {
            if (index != 1 && index != 2) {
                throw new IllegalArgumentException(String.format("잘못된 line index: %d", index));
            }

            index -= 1;

            if (isRow) {
                int startIdx = index*2;
                if (colors[startIdx] == colors[startIdx+1]) {
                    return colors[startIdx];
                }
            } else {
                if (colors[index] == colors[index+2]) {
                    return colors[index];
                }
            }
            return NOT_SAME;
        }
        public int getFaceColor() {
            int color = colors[0];
            for (int i=1; i<FACE_COLOR_NUM; i++) {
                if (colors[i] != color) {
                    return NOT_SAME;
                }
            }
            return color;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(colors[0]).append(" ").append(colors[1])
                    .append("\n")
                    .append(colors[2]).append(" ").append(colors[3]);
            return sb.toString();
        }
    }

    static class Cube {
        Face[] faces;
        RotatingStrategy rotatingStrategy;

        public Cube(int[] colors) {
            faces = new Face[FACE_NUM];
            for (int i=0; i<FACE_NUM; i++) {
                int startIdx = 4*i;
                faces[i] = new Face(colors[startIdx], colors[startIdx+1], colors[startIdx+2], colors[startIdx+3]);
            }
        }

        // UP-DOWN => LFRB
        // FRONT-BACK => LURD
        // LEFT-RIGHT => FDBU
        public boolean setStrategy() {
            if (isTwoFaceSameColor(UP, DOWN)) {
                rotatingStrategy = new UDRotatingStrategy(new Face[]{faces[LEFT], faces[FRONT], faces[RIGHT], faces[BACK]});
            } else if (isTwoFaceSameColor(LEFT, RIGHT)) {
                rotatingStrategy = new LRRotatingStrategy(new Face[]{faces[FRONT], faces[DOWN], faces[BACK], faces[UP]});
            } else if (isTwoFaceSameColor(FRONT, BACK)) {
                rotatingStrategy = new FBRotatingStrategy(new Face[]{faces[LEFT], faces[UP], faces[RIGHT], faces[DOWN]});
            }
            return rotatingStrategy != null;
        }

        private boolean isTwoFaceSameColor(int idx1, int idx2) {
            int color1 = faces[idx1].getFaceColor();
            int color2 = faces[idx2].getFaceColor();
            if (color1 == NOT_SAME || color2 == NOT_SAME) {
                return false;
            }
            return true;
        }
        public boolean canSolve() {
            return rotatingStrategy.rotate(FORWARD) || rotatingStrategy.rotate(BACKWARD);
        }

        // UFDLRB
        @Override
        public String toString() {
            String[] faceNames = new String[]{"UP", "FRONT", "DOWN", "LEFT", "RIGHT", "BACK"};

            StringBuilder sb = new StringBuilder();
            for (int i=0; i<FACE_NUM; i++) {
                sb.append(faceNames[i]).append("\n")
                  .append(faces[i]).append("\n\n");
            }
            return sb.toString();
        }
    }

    static abstract class RotatingStrategy {
        protected final int ROTATE_FACE_NUM = 4;

        private Face[] faces;

        public RotatingStrategy(Face[] faces) {
            this.faces = faces;
        }

        public boolean rotate(int dir) {
            for (int i=0; i<ROTATE_FACE_NUM; i++) {
                if (!hasSameInNextFace(i, dir)) {
                    return false;
                }
            }
            return true;
        }

        public boolean hasSameInNextFace(int faceIdx, int dir) {
            boolean[] lineTypes = getLineTypesToBeCompared(faceIdx);
            int[] lineIdxs = getLineIdxToBeCompared(faceIdx, dir);
            int color1 = faces[faceIdx].getLineColor(lineTypes[0], lineIdxs[0]);
            int color2 = faces[(faceIdx+dir+ROTATE_FACE_NUM)%ROTATE_FACE_NUM].getLineColor(lineTypes[1], lineIdxs[1]);
            if (color1 == NOT_SAME || color2 == NOT_SAME) {
                return false;
            }
            return color1 == color2;
        }

        abstract boolean[] getLineTypesToBeCompared(int faceIdx);
        abstract int[] getLineIdxToBeCompared(int faceIdx, int dir);
    }


    // UDRotatingStrategy : LFRB
    static class UDRotatingStrategy extends RotatingStrategy {

        public UDRotatingStrategy(Face[] faces) {
            super(faces);
        }

        @Override
        public boolean[] getLineTypesToBeCompared(int faceIdx) {
            return new boolean[]{ROW, ROW};
        }

        @Override
        public int[] getLineIdxToBeCompared(int faceIdx, int dir) {
            int[] lineIdxs;
            if (dir == FORWARD) {
                switch(faceIdx) {
                    case 0:
                        lineIdxs = new int[]{1, 2};
                        break;
                    case 1:
                        lineIdxs = new int[]{1, 2};
                        break;
                    case 2:
                        lineIdxs = new int[]{1, 2};
                        break;
                    case 3:
                        lineIdxs = new int[]{1, 2};
                        break;
                    default:
                        throw new IllegalArgumentException(String.format("wrong faceIdx: %d", faceIdx));
                }
            } else {
                switch(faceIdx) {
                    case 0:
                        lineIdxs = new int[]{1, 2};
                        break;
                    case 1:
                        lineIdxs = new int[]{1, 2};
                        break;
                    case 2:
                        lineIdxs = new int[]{1, 2};
                        break;
                    case 3:
                        lineIdxs = new int[]{1, 2};
                        break;
                    default:
                        throw new IllegalArgumentException(String.format("wrong faceIdx: %d", faceIdx));
                }
            }
            return lineIdxs;
        }
    }

    // LRRotatingStrategy : FDBU
    static class LRRotatingStrategy extends RotatingStrategy {

        public LRRotatingStrategy(Face[] faces) {
            super(faces);
        }

        @Override
        public boolean[] getLineTypesToBeCompared(int faceIdx) {
            return new boolean[]{COLUMN, COLUMN};
        }

        @Override
        public int[] getLineIdxToBeCompared(int faceIdx, int dir) {
            int[] lineIdxs;
            if (dir == FORWARD) {
                switch(faceIdx) {
                    case 0:
                        lineIdxs = new int[]{1, 2};
                        break;
                    case 1:
                        lineIdxs = new int[]{1, 1};
                        break;
                    case 2:
                        lineIdxs = new int[]{2, 2};
                        break;
                    case 3:
                        lineIdxs = new int[]{1, 2};
                        break;
                    default:
                        throw new IllegalArgumentException(String.format("wrong faceIdx: %d", faceIdx));
                }
            } else {
                switch(faceIdx) {
                    case 0:
                        lineIdxs = new int[]{1, 2};
                        break;
                    case 1:
                        lineIdxs = new int[]{1, 2};
                        break;
                    case 2:
                        lineIdxs = new int[]{2, 2};
                        break;
                    case 3:
                        lineIdxs = new int[]{1, 1};
                        break;
                    default:
                        throw new IllegalArgumentException(String.format("wrong faceIdx: %d", faceIdx));
                }
            }
            return lineIdxs;
        }
    }

    // FBRotatingStrategy : LURD
    static class FBRotatingStrategy extends RotatingStrategy {

        public FBRotatingStrategy(Face[] faces) {
            super(faces);
        }

        @Override
        public boolean[] getLineTypesToBeCompared(int faceIdx) {
            boolean first = faceIdx % 2 == 1;
            return new boolean[]{first, !first};
        }

        @Override
        public int[] getLineIdxToBeCompared(int faceIdx, int dir) {
            int[] lineIdxs;
            if (dir == FORWARD) {
                switch(faceIdx) {
                    case 0:
                        lineIdxs = new int[]{1, 2};
                        break;
                    case 1:
                        lineIdxs = new int[]{1, 1};
                        break;
                    case 2:
                        lineIdxs = new int[]{2, 1};
                        break;
                    case 3:
                        lineIdxs = new int[]{2, 2};
                        break;
                    default:
                        throw new IllegalArgumentException(String.format("wrong faceIdx: %d", faceIdx));
                }
            } else {
                switch(faceIdx) {
                    case 0:
                        lineIdxs = new int[]{1, 1};
                        break;
                    case 1:
                        lineIdxs = new int[]{1, 2};
                        break;
                    case 2:
                        lineIdxs = new int[]{2, 2};
                        break;
                    case 3:
                        lineIdxs = new int[]{2, 1};
                        break;
                    default:
                        throw new IllegalArgumentException(String.format("wrong faceIdx: %d", faceIdx));
                }
            }
            return lineIdxs;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int[] colors = new int[FACE_NUM * FACE_COLOR_NUM];
        for (int i=0; i<colors.length; i++) {
            colors[i] = Integer.parseInt(st.nextToken());
        }

        Cube cube = new Cube(colors);
        boolean hasStrategy = cube.setStrategy();
        if (hasStrategy) {
            sb.append(cube.canSolve() ? 1 : 0);
        } else {
            sb.append(0);
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}