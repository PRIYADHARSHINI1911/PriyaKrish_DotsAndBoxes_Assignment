package com.entsia.dotsandboxes;

/**
 * Represents a player in the Dots and Boxes game.
 * Each player has an ID (1 or 2) and maintains their score.
 */
public class Player {
    private final int id;
    private int score;

    public Player(int id) {
        this.id = id;
        this.score = 0;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

    public void addScore(int points) {
        this.score += points;
    }
}
