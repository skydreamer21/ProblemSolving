import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    static class Team {
        int teamId;
        int score;
        int rankOfFifth;
        int cnt;

        public Team(int teamId) {
            this.teamId = teamId;
            this.score = 0;
            this.rankOfFifth = 0;
            this.cnt = 0;
        }

        public void addResult(int rank) {
            cnt++;
            if (cnt <= 4) {
                score += rank;
//                System.out.printf("%d팀 rank - %d 추가! (score : %d)\n", teamId, rank, score);
            } else if (cnt == 5) {
//                System.out.printf("%d팀의 5등 선수의 rank : %d\n", teamId, rank);
                rankOfFifth = rank;
            }
        }

        public boolean hasCorrectTeam() {
            return cnt == 6;
        }
    }
    
    static class TeamManager {

        Map<Integer, Team> teamMap;

        public TeamManager() {
            teamMap = new HashMap<>();
        }

        public void addResult(int rank, int team) {
            if (!teamMap.containsKey(team)) {
                teamMap.put(team, new Team(team));
            }
            teamMap.get(team).addResult(rank);
        }

        public int getWinTeam() {
            int winTeam = -1;
            int minScore = Integer.MAX_VALUE;
            int fifthOfMinScoreTeam = Integer.MAX_VALUE;

            for (Map.Entry<Integer, Team> teamEntry : teamMap.entrySet()) {
                Team team = teamEntry.getValue();
                int teamId = teamEntry.getKey();
                if (!team.hasCorrectTeam()) continue;
                if (team.score < minScore) {
//                    System.out.printf("%d팀의 점수가 최저점(%d)으로 이긴 팀이 바뀌었습니다 (fifth : %d) \n", teamId, team.score, team.rankOfFifth);
                    winTeam = teamId;
                    minScore = team.score;
                    fifthOfMinScoreTeam = team.rankOfFifth;
                } else if (team.score == minScore) {
                    if (team.rankOfFifth < fifthOfMinScoreTeam) {
//                        System.out.printf("%d팀의 5등 선수가 더 빨라 이긴 팀이 바뀌었습니다 (fifth : %d) \n", teamId, team.rankOfFifth);
                        winTeam = teamId;
                        fifthOfMinScoreTeam = team.rankOfFifth;
                    }
                }
            }
            if (winTeam == -1) {
                throw new IllegalArgumentException("우승 team이 정해지지 않았습니다. 로직 수정이 필요합니다.");
            }
            return winTeam;
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int TC = Integer.parseInt(br.readLine());
        for (int t = 0; t < TC; t++) {
            int N = Integer.parseInt(br.readLine());

            Map<Integer, Integer> cntMap = new HashMap<>();
            st = new StringTokenizer(br.readLine());
            int[] results = new int[N];
            for (int i = 0; i < N; i++) {
                results[i] = Integer.parseInt(st.nextToken());
                if (!cntMap.containsKey(results[i])) {
                    cntMap.put(results[i], 0);
                }
                cntMap.replace(results[i], cntMap.get(results[i]) + 1);
            }


            TeamManager teamManager = new TeamManager();
            int rankCorrection = 0;
            for (int i=0; i<N; i++) {
                if (cntMap.get(results[i]) < 6) {
                    rankCorrection++;
                    continue;
                }
                teamManager.addResult(i+1-rankCorrection, results[i]);
            }
            sb.append(teamManager.getWinTeam()).append("\n");
        }


        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}