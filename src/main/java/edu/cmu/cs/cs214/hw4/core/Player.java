package edu.cmu.cs.cs214.hw4.core;

import java.util.Objects;

/**
 * Player class
 */
public class Player {
    private int playerID;
    private int meepleNumber;
    private int currentScore;
    private static final int TOTAL_MEEPLE_NUM = 7;

    /**
     * Player with i as ID
     *
     * @param i input sequence as the ID
     */
    public Player(int i) {
        this.playerID = i;
        this.meepleNumber = TOTAL_MEEPLE_NUM;
        this.currentScore = 0;
    }

    /**
     * Meeple return from board
     *
     * @param n the number of meeple need to be returned
     */
    public void returnedMeeple(int n) {
        meepleNumber += n;
    }

    /**
     * Placed a meeple
     */
    public void placedMeeple() {
        meepleNumber--;
    }

    /**
     * Add score to this player
     *
     * @param score score to be added
     */
    public void addScore(int score) {
        currentScore += score;
    }

    /**
     * Get the player ID
     *
     * @return return the player ID
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Get the player current meeple number
     *
     * @return return the meeple number
     */
    public int getMeepleNumber() {
        return meepleNumber;
    }

    /**
     * Get the player current score
     *
     * @return return player current score
     */
    public int getCurrentScore() {
        return currentScore;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Player)) {
            return false;
        }
        Player p = (Player) o;
        return playerID == p.playerID && currentScore == p.currentScore && meepleNumber == p.meepleNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerID, currentScore, meepleNumber);
    }

    @Override
    public String toString() {
        return String.format("PlayerID: %d - current score: %d - meeple number: %d", playerID, currentScore, meepleNumber);
    }
}
