import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static int N_BITS_ON;
    static int N;
    static int[][] map;
    static Set<Integer> visited = new HashSet<>();
    static int minDiff = Integer.MAX_VALUE;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        N_BITS_ON = (1 << N) - 1;
        map = new int[N][N];
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 팀 선택 후 점수 최소 갱신
        dfs(0, 0);

        sb.append(minDiff);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static void dfs(int cnt, int team) {
        if (cnt == N) {
            if (team == 0 || team == N_BITS_ON || !visited.contains(team)) {
                markVisited(team);
                int onBitCount = Integer.bitCount(team);
                int score1 = calculateScore(team, true, onBitCount);
                int score2 = calculateScore(team, false, N - onBitCount);
                minDiff = Math.min(minDiff, Math.abs(score1 - score2));
            }
            return;
        }

        team = setTeam(team, cnt);
        dfs(cnt + 1, team);

        team = unSetTeam(team, cnt);
        dfs(cnt + 1, team);
    }

    private static int calculateScore(int team, boolean side, int len) {
        int[] teamList = getTeamList(team, side, len);

        int score = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (i == j) continue;
                score += map[teamList[i]][teamList[j]];
            }
        }
        return score;
    }

    private static int[] getTeamList(int team, boolean side, int len) {
        int[] teamList = new int[len];
        int idx = 0;
        for (int i = 0; i < N; i++) {
            if (isTeamSet(team, i) == side) {
                teamList[idx++] = i;
            }
        }
        return teamList;
    }

    private static void markVisited(int team) {
        visited.add(team);
        visited.add(~team & N_BITS_ON);
    }

    public static boolean isTeamSet(int team, int idx) {
        return ((team >> idx) & 1) == 1;
    }

    public static int setTeam(int team, int idx) {
        return team | (1 << idx);
    }

    public static int unSetTeam(int team, int idx) {
        return team & ~(1 << idx);
    }
}