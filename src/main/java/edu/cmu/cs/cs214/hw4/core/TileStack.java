package edu.cmu.cs.cs214.hw4.core;

import java.util.Deque;

/**
 * Tile stack contains all shuffled tile type for this game
 */
public class TileStack {
    private TileGenerator tg;
    private Deque<JSONConfigReader.JSONTileType> shuffledDeque;

    /**
     * Constructor for the stack
     */
    public TileStack() {
        this.tg = new TileGenerator();
        this.shuffledDeque = tg.getShuffledDeque();
    }

    /**
     * Get a tile from the stack
     *
     * @return return a tile from the stack
     */
    public Tiles getTile() {
        JSONConfigReader.JSONTileType tt = shuffledDeque.pop();
        return tg.getTileFromCollection(tt.typeNumber);
    }

    /**
     * Get the stack size
     *
     * @return return the stack size
     */
    public int getStackSize() {
        return shuffledDeque.size();
    }
}
