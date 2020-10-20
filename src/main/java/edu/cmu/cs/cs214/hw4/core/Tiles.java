package edu.cmu.cs.cs214.hw4.core;


import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Tile class containing all five segments
 */
public class Tiles {
    private Coordinate coordinate;
    private int tileType;
    private Map<PositionOnTile, Segment> segmentMap;
    private Segment leftSegment;
    private Segment rightSegment;
    private Segment upSegment;
    private Segment downSegment;
    private Segment centerSegment;

    /**
     * Constructor for the tile
     *
     * @param leftSegment       left segment
     * @param rightSegment      right segment
     * @param upSegment         up segment
     * @param downSegment       down segment
     * @param centerSegment     center segment
     * @param type              tile type number
     */
    public Tiles(Segment leftSegment, Segment rightSegment,
                 Segment upSegment, Segment downSegment,
                 Segment centerSegment, int type) {
        this.coordinate = null;
        this.tileType = type;
        this.segmentMap = new HashMap<>(); // This representation still need more thinking
        this.leftSegment = leftSegment;
        this.rightSegment = rightSegment;
        this.upSegment = upSegment;
        this.downSegment = downSegment;
        this.centerSegment = centerSegment;
        this.leftSegment.addNeighbor(this.centerSegment);
        this.rightSegment.addNeighbor(this.centerSegment);
        this.upSegment.addNeighbor(this.centerSegment);
        this.downSegment.addNeighbor(this.centerSegment);
        this.centerSegment.addNeighbor(this.leftSegment);
        this.centerSegment.addNeighbor(this.rightSegment);
        this.centerSegment.addNeighbor(this.upSegment);
        this.centerSegment.addNeighbor(this.downSegment);
        segmentMap.put(PositionOnTile.LEFT, this.leftSegment);
        segmentMap.put(PositionOnTile.RIGHT, this.rightSegment);
        segmentMap.put(PositionOnTile.UP, this.upSegment);
        segmentMap.put(PositionOnTile.DOWN, this.downSegment);
        segmentMap.put(PositionOnTile.CENTER, this.centerSegment);
    }

    /**
     * Get the coordinate of this tile
     *
     * @return return the coorinate
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Set the placed coordinate to the tile
     *
     * @param coordinate    the coordinate that place this tile
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Get the tile type
     *
     * @return return the tile type
     */
    public int getTileType() {
        return tileType;
    }

    /**
     * Rotate the tile clockwise
     */
    public void rotate() {
        Segment temp = leftSegment;
        leftSegment = downSegment;
        downSegment = rightSegment;
        rightSegment = upSegment;
        upSegment = temp;

        leftSegment.setPositionOnTile(PositionOnTile.LEFT);
        rightSegment.setPositionOnTile(PositionOnTile.RIGHT);
        upSegment.setPositionOnTile(PositionOnTile.UP);
        downSegment.setPositionOnTile(PositionOnTile.DOWN);

        segmentMap.put(PositionOnTile.LEFT, leftSegment);
        segmentMap.put(PositionOnTile.RIGHT, rightSegment);
        segmentMap.put(PositionOnTile.UP, upSegment);
        segmentMap.put(PositionOnTile.DOWN, downSegment);
    }

    /**
     * Compare tiles with segments
     *
     * @param tile          the abutting tile for comparison
     * @param coordinate    the coordinate of the comparing tile
     * @return              return if they can be connected
     */
    public boolean compareTiles(Tiles tile, Coordinate coordinate) {
        int deltaX = coordinate.getX() - tile.coordinate.getX();
        int deltaY = coordinate.getY() - tile.coordinate.getY();
        if(deltaX == 1 && deltaY == 0) {
            return this.leftSegment.getFeature().getType().equals(tile.rightSegment.getFeature().getType());
        } else if(deltaX == 0 && deltaY == -1) {
            return this.downSegment.getFeature().getType().equals(tile.upSegment.getFeature().getType());
        } else if(deltaX == -1 && deltaY == 0) {
            return this.rightSegment.getFeature().getType().equals(tile.leftSegment.getFeature().getType());
        } else {
            return this.upSegment.getFeature().getType().equals(tile.downSegment.getFeature().getType());
        }
    }

    /**
     * Features that can place a meeple:
     * Road, City, and Monastory
     *
     * @param position    the position indicating which segment to place a meeple on a tile
     * @param meeple      the meeple with the playerID indicating the player owns it
     */
    public void placeMeeple(PositionOnTile position, Meeples meeple) {
        Segment segment = segmentMap.get(position);
        segment.placeMeeple(meeple);
    }

    /**
     * Get the left segment
     *
     * @return left segment
     */
    public Segment getLeftSegment() {
        return leftSegment;
    }

    /**
     * Get the right segment
     *
     * @return right segment
     */
    public Segment getRightSegment() {
        return rightSegment;
    }

    /**
     * Get the up segment
     *
     * @return up segment
     */
    public Segment getUpSegment() {return upSegment;}

    /**
     * Get the down segment
     *
     * @return down segmnet
     */
    public Segment getDownSegment() {
        return downSegment;
    }

    /**
     * Get the center segment
     *
     * @return center segment
     */
    public Segment getCenterSegment() {
        return centerSegment;
    }

    /**
     * Get the segment as a list
     *
     * @return a list of segments
     */
    public List<Segment> getSegmentList() {
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(centerSegment);
        segmentList.add(leftSegment);
        segmentList.add(upSegment);
        segmentList.add(rightSegment);
        segmentList.add(downSegment);
        return segmentList;
    }

    /**
     * Check if on the segment contains a meeple
     *
     * @param position  the position of the segment on the tile
     * @return          return if the segment contains a meeple
     */
    public boolean checkIfContainsMeeple(PositionOnTile position) {
        Segment segment = segmentMap.get(position);
        if(segment.getFeature().getType() == FeatureType.MONASTERY_TYPE) {
            return segment.getMeeple() == null;
        } else if(segment.getFeature().getType() == FeatureType.ROAD_TYPE) {
            return bfs(segment, FeatureType.ROAD_TYPE);
        } else if(segment.getFeature().getType() == FeatureType.CITY_TYPE) {
            return bfs(segment, FeatureType.CITY_TYPE);
        }
        return false;
    }

    private boolean bfs(Segment segment, FeatureType type) {
        Deque<Segment> queue = new ArrayDeque<>();
        queue.offer(segment);
        while (!queue.isEmpty()) {
            Segment s = queue.poll();
            s.setVisited();
            Meeples otherMeeple = s.getMeeple();
            if (otherMeeple != null) {
                resetVisited(segment, type);
                return false;
            }
            for (Segment neighbor : s.getNeighbors()) {
                if ((neighbor.getFeature().getType() == type ||
                        neighbor.getFeature().getType() == FeatureType.CENTER_CONNECT_TYPE) &&
                        !neighbor.getVisitedStatus()) {
                    queue.offer(neighbor);
                }
            }
        }
        resetVisited(segment, type);
        return true;
    }

    private void resetVisited(Segment segment, FeatureType type) {
        Deque<Segment> queue = new ArrayDeque<>();
        queue.offer(segment);
        while(!queue.isEmpty()) {
            Segment s = queue.poll();
            s.unvisited();
            for(Segment neighbor : s.getNeighbors()) {
                if((neighbor.getFeature().getType() == type ||
                        neighbor.getFeature().getType() == FeatureType.CENTER_CONNECT_TYPE) &&
                        neighbor.getVisitedStatus()) {
                    queue.offer(neighbor);
                }
            }
        }
    }
}