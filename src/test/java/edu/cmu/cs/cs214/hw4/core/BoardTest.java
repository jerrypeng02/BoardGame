package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class BoardTest {
    TileStack ts = new TileStack();
    Tiles tile = ts.getTile();
    Board board = new Board(tile, new Coordinate(0, 0));


    @Test
    public void placeTile() {
        Coordinate coordinate = new Coordinate(0, 1);
        board.placeTile(tile, coordinate);
        Map<Coordinate, Tiles> map = board.getCoordinateTilesMap();
        Tiles tileFromMap = map.get(coordinate);
        assertTrue(tileFromMap.equals(tile));
    }

    @Test
    public void placeMeeple() {
        Meeples meeple = new Meeples(2);
        board.placeMeeple(tile, PositionOnTile.DOWN, meeple);
        assertTrue(tile.getDownSegment().getMeeple() != null);
    }
}