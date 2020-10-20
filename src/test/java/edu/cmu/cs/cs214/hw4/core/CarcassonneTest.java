package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class CarcassonneTest {
    Carcassonne c = new Carcassonne(2);

    @Test
    public void getTile() {
        Tiles tile = c.getTile();
        assertNotNull(tile);
    }

    @Test
    public void rotateTile() {
        Tiles tile = c.getTile();
        Segment left = tile.getLeftSegment();
        Segment right = tile.getRightSegment();
        Segment up = tile.getUpSegment();
        Segment down = tile.getDownSegment();
        Segment center = tile.getCenterSegment();

        c.rotateTile(tile);
        Segment leftAfterRotate = tile.getLeftSegment();
        Segment rightAfterRotate = tile.getRightSegment();
        Segment upAfterRotate = tile.getUpSegment();
        Segment downAfterRotate = tile.getDownSegment();
        Segment centerAfterRotate = tile.getCenterSegment();

        assertEquals(center.getFeature(), centerAfterRotate.getFeature());
        assertTrue(left.getFeature().equals(upAfterRotate.getFeature()));
        assertTrue(up.getFeature().equals(rightAfterRotate.getFeature()));
        assertTrue(right.getFeature().equals(downAfterRotate.getFeature()));
        assertTrue(down.getFeature().equals(leftAfterRotate.getFeature()));
    }

    @Test
    public void placeTile() {
        Coordinate coordinate = new Coordinate(0, 1);
        Tiles tile = c.getTile();
        c.placeTile(tile, coordinate);
        assertFalse(c.getTileNumOnBoard() == 2);
    }

    @Test
    public void placeMeeple() {
        Tiles centerTile = c.getCenterTile();
        c.placeMeeple(centerTile, PositionOnTile.CENTER);
        assertTrue(centerTile.getCenterSegment().getMeeple() != null);
    }
}