package edu.cmu.cs.cs214.hw4.core;

import java.util.Objects;

/**
 * Coordinate for the tile
 */
public class Coordinate {
    private int x;
    private int y;

    /**
     * Constructor for the coordinate
     *
     * @param x horizontal axis
     * @param y vertical axis
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get x
     *
     * @return return x
     */
    public int getX() {
        return x;
    }

    /**
     * Get y
     * @return return y
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate coordinate = (Coordinate) o;
        return x == coordinate.x && y == coordinate.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
