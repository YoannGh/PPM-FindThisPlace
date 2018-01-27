package fr.upmc.m2sar.findthisplace.model;

import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setScore(long score) {
        this.score = score;
    }

}
