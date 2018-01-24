package fr.upmc.m2sar.findthisplace.model;

import java.util.Date;

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
