package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Segment class
 */
public class Segment {
    private PositionOnTile positionOnTile;
    private Coordinate affiliateTileCoordinate;
    private List<Segment> neighbors;
    private Feature feature;
    private Meeples meeple;
    private boolean visited;

    /**
     * Segment class constructor
     *
     * @param positionOnTile    the position on the tile
     * @param feature           the feature the segment contains
     */
    public Segment(PositionOnTile positionOnTile, Feature feature) {
        this.positionOnTile = positionOnTile;
        this.neighbors = new ArrayList<>();
        this.feature = feature;
        this.meeple = null;
        this.visited = false;
    }

    /**
     * Get the feature
     *
     * @return return the feature
     */
    public Feature getFeature() {
        return feature;
    }

    /**
     * Get the position of this segment
     *
     * @return return the position
     */
    public PositionOnTile getPositionOnTile() {
        return positionOnTile;
    }

    /**
     * Add the neighbor segment on the board to this segment
     *
     * @param neighbor  neighbor segment
     */
    public void addNeighbor(Segment neighbor) {
        neighbors.add(neighbor);
    }

    /**
     * Get the segment list of the neighbor segments
     *
     * @return return the list of neighbor segments
     */
    public List<Segment> getNeighbors() {
        return neighbors;
    }

    /**
     * Set the position of the segment
     *
     * @param position  the position for the segment
     */
    public void setPositionOnTile(PositionOnTile position) {
        positionOnTile = position;
    }

    /**
     * Get the visited status of this segment
     *
     * @return  return if it is visited for this segment
     */
    public boolean getVisitedStatus() {
        return visited;
    }

    /**
     * Set visited to this segment
     */
    public void setVisited() {
        visited = true;
    }

    /**
     * Unvisit the segment
     */
    public void unvisited() {
        visited = false;
    }

    /**
     * Set the coordinate of the tile that has this segment
     *
     * @param coordinate the coordinate of the tile with this segment
     */
    public void setAffiliateTileCoordinate(Coordinate coordinate) {
        this.affiliateTileCoordinate = coordinate;
    }

    /**
     * Get the coordinate of the tile that has this segment
     *
     * @return  return the coordinate of tile with this segment
     */
    public Coordinate getAffiliateTileCoordinate() {
        return affiliateTileCoordinate;
    }

    /**
     * Place a meeple on this segment
     *
     * @param meeple    the meeple placed on this segment
     */
    public void placeMeeple(Meeples meeple) {
        this.meeple = meeple;
    }

    /**
     * Get the meeple placed on this segment
     *
     * @return return the meeple placed on this segment
     */
    public Meeples getMeeple() {
        return meeple;
    }

    /**
     * Remove the meeple from the segment
     */
    public void removeMeeple() {
        meeple = null;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Segment)) {
            return false;
        }
        Segment s = (Segment) o;
        return positionOnTile == s.positionOnTile && affiliateTileCoordinate.equals(s.affiliateTileCoordinate) &&
                neighbors.equals(s.neighbors) && feature.equals(s.feature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionOnTile.hashCode(), affiliateTileCoordinate.hashCode(), neighbors.hashCode(), feature.hashCode());
    }

    @Override
    public String toString() {
        return positionOnTile.toString();
    }
}