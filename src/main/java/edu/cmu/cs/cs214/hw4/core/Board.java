package edu.cmu.cs.cs214.hw4.core;

import java.util.HashMap;
import java.util.Map;

/**
 * The board class for storing information of each tiles placed on the board
 */
public class Board {
    private Map<Coordinate, Tiles> coordinateTilesMap;
    private Map<Coordinate, Tiles> coordinateMonasteryMapOnBoard;
    private Map<Coordinate, Tiles> coordinateRoadMapOnBoard;
    private Map<Coordinate, Tiles> coordinateCityMapOnBoard;
    private static final int[][] DIRECTION = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    /**
     *Constructor for the board that contains maps for different features
     *
     * @param centerTile        the very first tile on the board
     * @param centerCoordinate   the center coordinate for as the center of the game
     */
    public Board(Tiles centerTile, Coordinate centerCoordinate) {
        this.coordinateTilesMap = new HashMap<>();
        this.coordinateMonasteryMapOnBoard = new HashMap<>();
        this.coordinateRoadMapOnBoard = new HashMap<>();
        this.coordinateCityMapOnBoard = new HashMap<>();
        centerTile.setCoordinate(centerCoordinate);
        coordinateTilesMap.put(centerCoordinate, centerTile);
        setSegmentCoordinateAndPutTypeMap(centerTile, centerCoordinate);
    }

    /**
     * Place the tile to a coordinate that player want
     *
     * @param tile          the tile that player want to place to the board
     * @param coordinate    the coordinate the player want to place the board
     * @return              return if the tile is successfully placed on a coordinate
     */
    public boolean placeTile(Tiles tile, Coordinate coordinate) {
        if(coordinateTilesMap.containsKey(coordinate)) {
            return false;
        }
        int inputX = coordinate.getX();
        int inputY = coordinate.getY();
        int nonEmptySide = 0;
        int matchingSide = 0;
        for (int[] ints : DIRECTION) {
            int newX = inputX + ints[0];
            int newY = inputY + ints[1];

            Coordinate abuttingCoordinate = new Coordinate(newX, newY);
            if (coordinateTilesMap.containsKey(abuttingCoordinate)) {
                nonEmptySide++;
                Tiles abuttingTile = coordinateTilesMap.get(abuttingCoordinate);
                if (tile.compareTiles(abuttingTile, coordinate)) {
                    matchingSide++;
                }
            }
        }
        if(matchingSide == nonEmptySide && nonEmptySide != 0) {
            tile.setCoordinate(coordinate);
            for(int i = 0; i < DIRECTION.length; i++) {
                int newX = inputX + DIRECTION[i][0];
                int newY = inputY + DIRECTION[i][1];
                Coordinate abuttingCoordinate = new Coordinate(newX, newY);
                if(coordinateTilesMap.containsKey(abuttingCoordinate)) {
                    Tiles abuttingTile = coordinateTilesMap.get(abuttingCoordinate);
                    if(i == 0) {
                        Segment left = tile.getLeftSegment();
                        Segment abuttingTileRightSegment = abuttingTile.getRightSegment();
                        left.addNeighbor(abuttingTileRightSegment);
                        abuttingTileRightSegment.addNeighbor(left);

                    } else if(i == 1) {
                        Segment right = tile.getRightSegment();
                        Segment abuttingTileLeftSegment = abuttingTile.getLeftSegment();
                        right.addNeighbor(abuttingTileLeftSegment);
                        abuttingTileLeftSegment.addNeighbor(right);
                    } else if(i == 2) {
                        Segment down = tile.getDownSegment();
                        Segment abuttingTileUpSegment = abuttingTile.getUpSegment();
                        down.addNeighbor(abuttingTileUpSegment);
                        abuttingTileUpSegment.addNeighbor(down);
                    } else {
                        Segment up = tile.getUpSegment();
                        Segment abuttingTileDownSegment = abuttingTile.getDownSegment();
                        up.addNeighbor(abuttingTileDownSegment);
                        abuttingTileDownSegment.addNeighbor(up);
                    }
                }
            }

            coordinateTilesMap.put(coordinate, tile);
            setSegmentCoordinateAndPutTypeMap(tile, coordinate);
            return true;
        }
        return false;
    }

    /**
     * Place a meeple on a specific tile segment
     *
     * @param tile      the tile that the player wants to place the meeple on
     * @param position  the segment position on the tile
     * @param meeple    the meeple with the player ID indicating which player it belongs to
     */
    public void placeMeeple(Tiles tile, PositionOnTile position, Meeples meeple) {
        tile.placeMeeple(position, meeple);
    }

    /**
     * Get the map with the tiles that contains monastery feature
     *
     * @return  return the map with the tiles that contains monastery feature
     */
    public Map<Coordinate, Tiles> getCoordinateMonasteryMapOnBoard() {
        return coordinateMonasteryMapOnBoard;
    }

    /**
     * Get the map with the tiles that contains road feature
     *
     * @return  return the map with tiles that contains road feature
     */
    public Map<Coordinate, Tiles> getCoordinateRoadMapOnBoard() {
        return coordinateRoadMapOnBoard;
    }

    /**
     * Get the map with the tiles that contains city feature
     *
     * @return  return the map with tiles that contains city feature
     */
    public Map<Coordinate, Tiles> getCoordinateCityMapOnBoard() {
        return coordinateCityMapOnBoard;
    }

    /**
     * Get the map with the tiles that contains all tiles
     *
     * @return  return the map with tiles that contains all tiles
     */
    public Map<Coordinate, Tiles> getCoordinateTilesMap() {
        return coordinateTilesMap;
    }

    private void setSegmentCoordinateAndPutTypeMap(Tiles tile, Coordinate coordinate) {
        for(Segment s : tile.getSegmentList()) {
            s.setAffiliateTileCoordinate(coordinate);
        }
        for(Segment s : tile.getSegmentList()) {
            if(s.getFeature().getType() == FeatureType.ROAD_TYPE) {
                coordinateRoadMapOnBoard.put(coordinate, tile);
            } else if(s.getFeature().getType() == FeatureType.CITY_TYPE) {
                coordinateCityMapOnBoard.put(coordinate, tile);
            } else if(s.getFeature().getType() == FeatureType.MONASTERY_TYPE) {
                coordinateMonasteryMapOnBoard.put(coordinate, tile);
            }
        }
    }

    /**
     * Return the number of tiles on the board
     *
     * @return  the number of tiles on the board
     */
    public int tileNumOnBoard() {
        return coordinateTilesMap.size();
    }
}
