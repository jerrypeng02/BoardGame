package edu.cmu.cs.cs214.hw4.core;

import java.util.List;

/**
 * The carcassonne game interface for interacting with the game board panel
 */
public interface CarcassonneInterface {
    /**
     * Get the tile from the tile stack
     *
     * @return  return the tile get from tile stack
     */
    Tiles getTile();

    /**
     * Get the center tile on the board
     *
     * @return  return the center tile on the board
     */
    Tiles getCenterTile();

    /**
     * Rotate the tile clockwise
     *
     * @param tile  the tile that is need to be rotated
     */
    void rotateTile(Tiles tile);

    /**
     * Place the tile on the board if player requested
     *
     * @param tile          the tile that the player want to place on the board
     * @param coordinate    the coordinate that the player want to place
     */
    void placeTile(Tiles tile, Coordinate coordinate);

    /**
     * Place the meeple on a specific area on the tile
     *
     * @param tile          the tile that the player want to place a meeple on
     * @param position      the position on the tile that the player want to place a meeple on
     */
    void placeMeeple(Tiles tile, PositionOnTile position);

    /**
     * Get the player list in the game
     *
     * @return  return the player list of the game
     */
    List<Player> getPlayerList();

    /**
     * Add a game change listener
     *
     * @param listener  the game change listener
     */
    void addGameChangeListener(GameChangeListener listener);

    /**
     * Skip the current tile if there is no place to put it on the board
     */
    void skip();

    /**
     * Go to the next turn for the player
     */
    void nextTurn();

    /**
     * Score after place a tile or a meeple
     *
     * @param tile  the tile that is scored from
     */
    void scoringCompletion(Tiles tile);

    /**
     * Score the whole board at the final
     */
    void scoringFinal();

    /**
     * Get the rest number of tile in the tile stack
     *
     * @return  return the rest number of tile in the tile stack
     */
    int getRestTilesNum();

    /**
     * Return the number of tiles on the board
     *
     * @return  return th number of tiles on the board
     */
    int getTileNumOnBoard();

}
