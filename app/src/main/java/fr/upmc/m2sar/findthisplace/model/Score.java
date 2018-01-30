package fr.upmc.m2sar.findthisplace.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Données persistantes d'un score
 */
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    private String playerName;
    private Date date;
    private String difficulty;
    private String mode;
    private long score;

    public Score(String playerName, Date date, String difficulty, String mode, long score) {
        this.playerName = playerName;
        this.date = date;
        this.difficulty = difficulty;
        this.mode = mode;
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

    public String getMode() { return this.mode; }

    public long getScore() {
        return this.score;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setDate(Date date) { this.date = date; }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setMode(String mode) { this.mode = mode; }

    public void setScore(long score) {
        this.score = score;
    }

}
