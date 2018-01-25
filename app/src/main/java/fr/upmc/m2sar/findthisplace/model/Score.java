package fr.upmc.m2sar.findthisplace.model;

import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    private String playerName;
    private long timestamp;
    private String difficulty;
    private long score;

    public Score(String playerName, long timestamp, String difficulty, long score) {
        this.playerName = playerName;
        this.timestamp = timestamp;
        this.difficulty = difficulty;
        this.score = score;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public long getTimestamp() {
        return this.timestamp;
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

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setScore(long score) {
        this.score = score;
    }

}
