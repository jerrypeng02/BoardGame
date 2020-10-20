package edu.cmu.cs.cs214.hw4.core;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * Scoring system for the game
 */
public class ScoringSystem {
    private List<Player> playerList;
    private Map<Coordinate, Tiles> coordinateTilesMap;
    private Map<Coordinate, Tiles> monasteryMapOnBoard;
    private Map<Coordinate, Tiles> roadMapOnBoard;
    private Map<Coordinate, Tiles> cityMapOnBoard;
    private List<Coordinate> returnCoordinate;
    private int[] playerMeepleNumRoad;
    private int[] playerMeepleNumCity;
    private static final int[][] MONASTERY_DIRECTION = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, -1}, {-1, 1}, {1, 1}};
    private static final int MONASTERY_SURROUNDING = 8;

    /**
     * Constructor for the system
     *
     * @param board         the board in the game
     * @param playerList    the player list in the game
     */
    public ScoringSystem(Board board, List<Player> playerList) {
        this.playerList = playerList;
        this.coordinateTilesMap = board.getCoordinateTilesMap();
        this.monasteryMapOnBoard = board.getCoordinateMonasteryMapOnBoard();
        this.roadMapOnBoard = board.getCoordinateRoadMapOnBoard();
        this.cityMapOnBoard = board.getCoordinateCityMapOnBoard();
        this.returnCoordinate = new ArrayList<>();
        this.playerMeepleNumRoad = new int[playerList.size()];
        this.playerMeepleNumCity = new int[playerList.size()];
    }

    /**
     * Score from the tile after placing a tile
     *
     * @param tile  the tile that placed
     */
    public void scoringFromTile(Tiles tile) {
        scoringRoad(tile);
        scoringCity(tile);
        scoringMonastery();
    }

    /**
     * Score the whole board
     */
    public void scoringWholeBoard() {
        scoringRoadFinal();
        scoringCityFinal();
        scoringMonasteryFinal();
    }

    /**
     * Get the coordinate that need to return meeples
     *
     * @return  the list of coordinate that need to return meeples
     */
    public List<Coordinate> getReturnCoordinate() {
        return returnCoordinate;
    }

    private void scoringRoad(Tiles tile) {
        for(Segment s : tile.getSegmentList()) {
            if(s.getFeature().getType() == FeatureType.ROAD_TYPE) {
                scoreRoadFromSegment(s);
            }
        }
    }

    private void scoreRoadFromSegment(Segment segment) {
        Arrays.fill(playerMeepleNumRoad, 0);
        Set<Coordinate> involvedTile = new HashSet<>();
        if(checkRoadCompletion(segment)) {
            Deque<Segment> queue = new ArrayDeque<>();
            queue.offer(segment);
            while(!queue.isEmpty()) {
                Segment s = queue.poll();
                s.setVisited();
                Meeples meeple = s.getMeeple();
                if(meeple != null) {
                    int playerID = meeple.getPlayerID();
                    playerMeepleNumRoad[playerID]++;
                    returnCoordinate.add(s.getAffiliateTileCoordinate());
                    s.removeMeeple();
                }
                involvedTile.add(s.getAffiliateTileCoordinate());
                for(Segment neighbor : s.getNeighbors()) {
                    if((neighbor.getFeature().getType() == FeatureType.ROAD_TYPE ||
                            neighbor.getFeature().getType() == FeatureType.CENTER_CONNECT_TYPE) &&
                            !neighbor.getVisitedStatus()) {
                        queue.offer(neighbor);
                    }
                }
            }
            int max = Integer.MIN_VALUE;
            for(int i : playerMeepleNumRoad) {
                if(i > max) {
                    max = i;
                }
            }
            if(max != 0) {
                for(int i = 0; i < playerMeepleNumRoad.length; i++) {
                    if(playerMeepleNumRoad[i] == max) {
                        Player player = playerList.get(i);
                        player.addScore(involvedTile.size());
                    }
                }
                for(int i = 0; i < playerMeepleNumRoad.length; i++) {
                    Player player = playerList.get(i);
                    player.returnedMeeple(playerMeepleNumRoad[i]);
                }
            }
            resetVisited(segment, FeatureType.ROAD_TYPE);
            for(Coordinate coordinate : involvedTile) {
                roadMapOnBoard.remove(coordinate);
            }
        }
    }

    private boolean checkRoadCompletion(Segment segment) {
        Deque<Segment> queue = new ArrayDeque<>();
        queue.offer(segment);
        int openEnd = 0;
        while(!queue.isEmpty()) {
            Segment s = queue.poll();
            s.setVisited();
            if(s.getPositionOnTile() != PositionOnTile.CENTER && s.getNeighbors().size() == 1) {
                openEnd++;
            }
            for(Segment neighbor : s.getNeighbors()) {
                if((neighbor.getFeature().getType() == FeatureType.ROAD_TYPE ||
                        neighbor.getFeature().getType() == FeatureType.CENTER_CONNECT_TYPE) &&
                        !neighbor.getVisitedStatus()) {
                    queue.offer(neighbor);
                }
            }
        }
        resetVisited(segment, FeatureType.ROAD_TYPE);
        return openEnd == 0;
    }

    private void scoringRoadFinal() {
        for (Tiles tile : roadMapOnBoard.values()) {
            for(Segment segment : tile.getSegmentList()) {
                if(segment.getFeature().getType() == FeatureType.ROAD_TYPE) {
                    Arrays.fill(playerMeepleNumRoad, 0);
                    Set<Coordinate> involvedTile = new HashSet<>();
                    Deque<Segment> queue = new ArrayDeque<>();
                    queue.offer(segment);
                    while(!queue.isEmpty()) {
                        Segment s = queue.poll();
                        s.setVisited();
                        Meeples meeple = s.getMeeple();
                        if(meeple != null) {
                            int playerID = meeple.getPlayerID();
                            playerMeepleNumRoad[playerID]++;
                            returnCoordinate.add(s.getAffiliateTileCoordinate());
                            s.removeMeeple();
                        }
                        involvedTile.add(s.getAffiliateTileCoordinate());
                        for(Segment neighbor : s.getNeighbors()) {
                            if((neighbor.getFeature().getType() == FeatureType.ROAD_TYPE ||
                                    neighbor.getFeature().getType() == FeatureType.CENTER_CONNECT_TYPE) &&
                                    !neighbor.getVisitedStatus()) {
                                queue.offer(neighbor);
                            }
                        }
                    }
                    int max = Integer.MIN_VALUE;
                    for(int i : playerMeepleNumRoad) {
                        if(i > max) {
                            max = i;
                        }
                    }
                    if(max != 0) {
                        for(int i = 0; i < playerMeepleNumRoad.length; i++) {
                            if(playerMeepleNumRoad[i] == max) {
                                Player player = playerList.get(i);
                                player.addScore(involvedTile.size());
                            }
                        }
                        for(int i = 0; i < playerMeepleNumRoad.length; i++) {
                            Player player = playerList.get(i);
                            player.returnedMeeple(playerMeepleNumRoad[i]);
                        }
                    }
                    resetVisited(segment, FeatureType.ROAD_TYPE);
                }
            }
        }
    }

    private void scoringCity(Tiles tile) {
        for(Segment s : tile.getSegmentList()) {
            if(s.getFeature().getType() == FeatureType.CITY_TYPE) {
                scoreCityFromSegment(s);
            }
        }
    }

    private void scoreCityFromSegment(Segment segment) {
        Arrays.fill(playerMeepleNumCity, 0);
        Set<Coordinate> involvedTile = new HashSet<>();
        int armedMarkNum = 0;
        if(checkCityCompletion(segment)) {
            Deque<Segment> queue = new ArrayDeque<>();
            queue.offer(segment);
            while(!queue.isEmpty()) {
                Segment s = queue.poll();
                s.setVisited();
                Meeples meeple = s.getMeeple();
                if(meeple != null) {
                    int playerID = meeple.getPlayerID();
                    playerMeepleNumCity[playerID]++;
                    returnCoordinate.add(s.getAffiliateTileCoordinate());
                    s.removeMeeple();
                }
                if(s.getFeature().getArmedMark()) {
                    armedMarkNum++;
                }
                involvedTile.add(s.getAffiliateTileCoordinate());
                for(Segment neighbor : s.getNeighbors()) {
                    if((neighbor.getFeature().getType() == FeatureType.CITY_TYPE ||
                            neighbor.getFeature().getType() == FeatureType.CENTER_CONNECT_TYPE) &&
                            !neighbor.getVisitedStatus()) {
                        queue.offer(neighbor);
                    }
                }
            }
            int max = Integer.MIN_VALUE;
            for(int i : playerMeepleNumCity) {
                if(i > max) {
                    max = i;
                }
            }
            if(max != 0) {
                for(int i = 0; i < playerMeepleNumCity.length; i++) {
                    if(playerMeepleNumCity[i] == max) {
                        Player player = playerList.get(i);
                        player.addScore(2 * involvedTile.size() + 2 * armedMarkNum);
                    }
                }
                for(int i = 0; i < playerMeepleNumCity.length; i++) {
                    Player player = playerList.get(i);
                    player.returnedMeeple(playerMeepleNumCity[i]);
                }
            }
            resetVisited(segment, FeatureType.CITY_TYPE);
            for(Coordinate coordinate : involvedTile) {
                cityMapOnBoard.remove(coordinate);
            }
        }
    }

    private boolean checkCityCompletion(Segment segment) {
        Deque<Segment> queue = new ArrayDeque<>();
        queue.offer(segment);
        int openEnd = 0;
        while(!queue.isEmpty()) {
            Segment s = queue.poll();
            s.setVisited();
            if(s.getPositionOnTile() != PositionOnTile.CENTER && s.getNeighbors().size() == 1) {
                openEnd++;
            }
            for(Segment neighbor : s.getNeighbors()) {
                if((neighbor.getFeature().getType() == FeatureType.CITY_TYPE ||
                        neighbor.getFeature().getType() == FeatureType.CENTER_CONNECT_TYPE) &&
                        !neighbor.getVisitedStatus()) {
                    queue.offer(neighbor);
                }
            }
        }
        resetVisited(segment, FeatureType.CITY_TYPE);
        return openEnd == 0;
    }

    private void scoringCityFinal() {
        for (Tiles tile : cityMapOnBoard.values()) {
            for(Segment segment : tile.getSegmentList()) {
                if(segment.getFeature().getType() == FeatureType.CITY_TYPE) {
                    Arrays.fill(playerMeepleNumCity, 0);
                    Set<Coordinate> involvedTile = new HashSet<>();
                    int armedMarkNum = 0;
                    Deque<Segment> queue = new ArrayDeque<>();
                    queue.offer(segment);
                    while(!queue.isEmpty()) {
                        Segment s = queue.poll();
                        s.setVisited();
                        Meeples meeple = s.getMeeple();
                        if(meeple != null) {
                            int playerID = meeple.getPlayerID();
                            playerMeepleNumCity[playerID]++;
                            returnCoordinate.add(s.getAffiliateTileCoordinate());
                            s.removeMeeple();
                        }
                        if(s.getFeature().getArmedMark()) {
                            armedMarkNum++;
                        }
                        involvedTile.add(s.getAffiliateTileCoordinate());
                        for(Segment neighbor : s.getNeighbors()) {
                            if((neighbor.getFeature().getType() == FeatureType.CITY_TYPE ||
                                    neighbor.getFeature().getType() == FeatureType.CENTER_CONNECT_TYPE) &&
                                    !neighbor.getVisitedStatus()) {
                                queue.offer(neighbor);
                            }
                        }
                    }
                    int max = Integer.MIN_VALUE;
                    for(int i : playerMeepleNumCity) {
                        if(i > max) {
                            max = i;
                        }
                    }
                    if(max != 0) {
                        for(int i = 0; i < playerMeepleNumCity.length; i++) {
                            if(playerMeepleNumCity[i] == max) {
                                Player player = playerList.get(i);
                                player.addScore(involvedTile.size() + armedMarkNum);
                            }
                        }
                        for(int i = 0; i < playerMeepleNumCity.length; i++) {
                            Player player = playerList.get(i);
                            player.returnedMeeple(playerMeepleNumCity[i]);
                        }
                    }
                    resetVisited(segment, FeatureType.CITY_TYPE);
                }
            }
        }
    }

    /**
     * Scoring for monastery on the board every placement of a tile during the game, not the final scoring
     */
    private void scoringMonastery() {
        for(Coordinate monasteryCoordinate : monasteryMapOnBoard.keySet()) {
            Tiles monastery = monasteryMapOnBoard.get(monasteryCoordinate);
            int x = monasteryCoordinate.getX();
            int y = monasteryCoordinate.getY();
            int nonEmptySurrounding = 0;
            for (int[] ints : MONASTERY_DIRECTION) {
                int newX = x + ints[0];
                int newY = y + ints[1];
                Coordinate surroundingCoordinate = new Coordinate(newX, newY);
                if (coordinateTilesMap.containsKey(surroundingCoordinate)) {
                    nonEmptySurrounding++;
                }
            }
            if(nonEmptySurrounding == MONASTERY_SURROUNDING) {
                Segment center = monastery.getCenterSegment();
                Meeples meeple = center.getMeeple();
                if(meeple != null) {
                    returnCoordinate.add(monasteryCoordinate);
                    center.removeMeeple();
                    Player player = playerList.get(meeple.getPlayerID());
                    player.addScore(MONASTERY_SURROUNDING + 1);
                    player.returnedMeeple(1);
                }
                monasteryMapOnBoard.remove(monasteryCoordinate);
            }
        }
    }

    private void scoringMonasteryFinal() {
        for(Coordinate monasteryCoordinate : monasteryMapOnBoard.keySet()) {
            Tiles monastery = monasteryMapOnBoard.get(monasteryCoordinate);
            int x = monasteryCoordinate.getX();
            int y = monasteryCoordinate.getY();
            int surrounding = 0;
            for (int[] ints : MONASTERY_DIRECTION) {
                int newX = x + ints[0];
                int newY = y + ints[1];
                Coordinate surroundingCoordinate = new Coordinate(newX, newY);
                if (coordinateTilesMap.containsKey(surroundingCoordinate)) {
                    surrounding++;
                }
            }
            Segment center = monastery.getCenterSegment();
            Meeples meeple = center.getMeeple();
            if(meeple != null) {
                returnCoordinate.add(monasteryCoordinate);
                center.removeMeeple();
                Player player = playerList.get(meeple.getPlayerID());
                player.addScore(surrounding + 1);
                player.returnedMeeple(1);
            }
        }
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