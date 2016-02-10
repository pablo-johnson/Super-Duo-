package barqsoft.footballscores;

import android.database.Cursor;

/**
 * Created by Pablo on 10/02/16.
 */
public class Score {

    private String homeName;
    private String awayName;
    private String date;
    private String score;
    private Double matchId;
    private int homeGoals;
    private int awayGoals;
    private int homeCrestImageResource;
    private int awayCrestImageResource;

    public Score(Cursor cursor) {
        homeName = cursor.getString(ScoresAdapter.COL_HOME);
        awayName = cursor.getString(ScoresAdapter.COL_MATCHTIME);
        date = cursor.getString(ScoresAdapter.COL_DATE);
        score = Utilies.getScores(cursor.getInt(ScoresAdapter.COL_HOME_GOALS), cursor.getInt(ScoresAdapter.COL_AWAY_GOALS));
        matchId = cursor.getDouble(ScoresAdapter.COL_ID);
        homeCrestImageResource = Utilies.getTeamCrestByTeamName(cursor.getString(ScoresAdapter.COL_HOME));
        awayCrestImageResource = Utilies.getTeamCrestByTeamName(cursor.getString(ScoresAdapter.COL_AWAY));
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getAwayName() {
        return awayName;
    }

    public void setAwayName(String awayName) {
        this.awayName = awayName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Double getMatchId() {
        return matchId;
    }

    public void setMatchId(Double matchId) {
        this.matchId = matchId;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public int getHomeCrestImageResource() {
        return homeCrestImageResource;
    }

    public void setHomeCrestImageResource(int homeCrestImageResource) {
        this.homeCrestImageResource = homeCrestImageResource;
    }

    public int getAwayCrestImageResource() {
        return awayCrestImageResource;
    }

    public void setAwayCrestImageResource(int awayCrestImageResource) {
        this.awayCrestImageResource = awayCrestImageResource;
    }
}
