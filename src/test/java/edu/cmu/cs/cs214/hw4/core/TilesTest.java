package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class TilesTest {
    TileStack ts = new TileStack();
    Tiles tile = ts.getTile();

    @Test
    public void rotate() {
        Segment left = tile.getLeftSegment();
        Segment right = tile.getRightSegment();
        Segment up = tile.getUpSegment();
        Segment down = tile.getDownSegment();
        Segment center = tile.getCenterSegment();
        tile.rotate();
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
}