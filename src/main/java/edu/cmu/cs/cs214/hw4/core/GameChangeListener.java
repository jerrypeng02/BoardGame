package edu.cmu.cs.cs214.hw4.core;

import java.util.List;

/**
 * The game change listener for any change of the game
 */
public interface GameChangeListener {
    /**
     * Change the tile when user place a tile
     *
     * @param tile          tile placed on the board
     * @param coordinate    the coordinate placed the tile
     */
    void tilePlaced(Tiles tile, Coordinate coordinate);

    /**
     * rotate the tile
     */
    void tileRotated();

    /**
     * Skip the tile if it cannot be placed on the board
     */
    void skip();

    /**
     * Get to the next turn and switch user
     */
    void nextTurn();

    /**
     * Return the meeple and change the image back to the one without meeple placed on the image
     *
     * @param returnCoordinate  coordinate that contains a meeple that need to change back
     */
    void returnMeeple(List<Coordinate> returnCoordinate);

}
