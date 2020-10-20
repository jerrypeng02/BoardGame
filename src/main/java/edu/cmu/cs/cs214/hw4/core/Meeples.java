package edu.cmu.cs.cs214.hw4.core;

import java.util.Objects;

/**
 * Meeple class
 */
public class Meeples {
    private int playerID;

    /**
     * Meeple with the player id
     *
     * @param playerID the player id
     */
    public Meeples(int playerID) {
        this.playerID = playerID;
    }

    /**
     * Get the player id
     *
     * @return return player id
     */
    public int getPlayerID() {
        return playerID;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Meeples)) {
            return false;
        }
        Meeples m = (Meeples) o;
        return playerID == m.playerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerID);
    }

    @Override
    public String toString() {
        return String.format("%d", playerID);
    }
}
