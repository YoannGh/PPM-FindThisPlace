package fr.upmc.m2sar.findthisplace;

import java.util.Date;

/**
 * Created by m2sar on 22/01/2018.
 */

public class Score {

    private String playerName;
    private Date date;
    private String difficulty;
    private long score;

    public Score(String playerName, Date date, String difficulty, long score) {
        this.playerName = playerName;
        this.date = date;
        this.difficulty = difficulty;
        this.score = score;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Date getDate() {
        return this.date;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public long getScore() {
        return this.score;
    }
}
