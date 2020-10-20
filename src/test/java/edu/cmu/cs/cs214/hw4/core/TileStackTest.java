package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class TileStackTest {
    TileStack ts = new TileStack();

    @Test
    public void getTile() {
        Tiles tile = ts.getTile();
        assertNotNull(tile);
        assertNotEquals(tile.getTileType(), 0);
    }

    @Test
    public void getStackSize() {
        assertEquals(ts.getStackSize(), 72);
        ts.getTile();
        assertNotEquals(ts.getStackSize(), 72);
    }
}