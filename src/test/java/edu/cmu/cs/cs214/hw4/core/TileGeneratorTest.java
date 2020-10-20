package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import java.util.Deque;

import static org.junit.Assert.*;

public class TileGeneratorTest {
    TileGenerator tg = new TileGenerator();

    @Test
    public void parse() {
        JSONConfigReader.JSONTileCollection tilesCollection = tg.getTilesCollection();
        assertEquals(tilesCollection.tileTypes.length, 24);
    }

    @Test
    public void getTileFromCollection() {
        Tiles tile = tg.getTileFromCollection(0);
        assertEquals(tile.getTileType(), 0);
        assertEquals(tile.getCenterSegment().getFeature().getType(), FeatureType.MONASTERY_TYPE);
    }

    @Test
    public void getShuffledList() {
        Deque<JSONConfigReader.JSONTileType> deque = tg.getShuffledDeque();
        assertEquals(deque.size(), 72);
    }
}