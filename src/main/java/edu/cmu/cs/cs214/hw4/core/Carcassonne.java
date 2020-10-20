package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

/**
 * The main method that deals with the user input and all gaming logic
 */
public class Carcassonne implements CarcassonneInterface{
    private TileStack tileStack;
    private Board board;
    private List<Player> playerList;
    private ScoringSystem scoringSystem;
    private Player currentPlayer;
    private int roundNum;
    private int totalPlayer;
    private Tiles centerTile;
    private static final Coordinate CENTER_COORDINATE = new Coordinate(10, 10);
    private final List<GameChangeListener> gameChangeListeners = new ArrayList<>();

    /**
     * The constructor that takes in the number of players
     *
     * @param totalPlayer   the number of players that participate the game
     */
    public Carcassonne(int totalPlayer) {
        this.totalPlayer = totalPlayer;
        this.playerList = new ArrayList<>();
        for(int i = 0; i < totalPlayer; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
        this.roundNum = 0;
        this.currentPlayer = playerList.get(roundNum % playerList.size());
        this.tileStack = new TileStack();
        this.centerTile = tileStack.getTile();
        this.board = new Board(centerTile, CENTER_COORDINATE);
        this.scoringSystem = new ScoringSystem(board, playerList);
    }

    /**
     * Get the tile from the tile stack
     *
     * @return  return the tile get from tile stack
     */
    public Tiles getTile() {
        return tileStack.getTile();
    }

    private void notifyTilePlaced(Tiles tile, Coordinate coordinate) {
        for(GameChangeListener listener : gameChangeListeners) {
            listener.tilePlaced(tile, coordinate);
        }
    }

    /**
     * Get the rest number of tile in the tile stack
     *
     * @return  return the rest number of tile in the tile stack
     */
    public int getRestTilesNum() {
        return tileStack.getStackSize();
    }

    /**
     * Get the center tile on the board
     *
     * @return  return the center tile on the board
     */
    public Tiles getCenterTile() {
        return centerTile;
    }

    /**
     * Rotate the tile clockwise
     *
     * @param tile  the tile that is need to be rotated
     */
    public void rotateTile(Tiles tile) {
        notifyTileRotated();
        tile.rotate();
    }

    private void notifyTileRotated() {
        for(GameChangeListener listener : gameChangeListeners) {
            listener.tileRotated();
        }
    }

    /**
     * Place the tile on the board if player requested
     *
     * @param tile          the tile that the player want to place on the board
     * @param coordinate    the coordinate that the player want to place
     */
    public void placeTile(Tiles tile, Coordinate coordinate) {
        if(board.placeTile(tile, coordinate)) {
            notifyTilePlaced(tile, coordinate);
        }
    }

    /**
     * Place the meeple on a specific area on the tile
     *
     * @param tile          the tile that the player want to place a meeple on
     * @param position      the position on the tile that the player want to place a meeple on
     */
    public void placeMeeple(Tiles tile, PositionOnTile position) {
        if(currentPlayer.getMeepleNumber() > 0) {
            int playerID = currentPlayer.getPlayerID();
            Meeples meeple = new Meeples(playerID);
            board.placeMeeple(tile, position, meeple);
            currentPlayer.placedMeeple();
        }
    }

    /**
     * Score after place a tile or a meeple
     *
     * @param tile  the tile that is scored from
     */
    public void scoringCompletion(Tiles tile) {
        scoringSystem.scoringFromTile(tile);
        notifyReturnMeeple(scoringSystem.getReturnCoordinate());
    }

    /**
     * Score the whole board at the final
     */
    public void scoringFinal() {
        scoringSystem.scoringWholeBoard();
        notifyReturnMeeple(scoringSystem.getReturnCoordinate());
    }

    private void notifyReturnMeeple(List<Coordinate> returnCoordinate) {
        for(GameChangeListener listener : gameChangeListeners) {
            listener.returnMeeple(returnCoordinate);
        }
    }

    /**
     * Get the player list in the game
     *
     * @return  return the player list of the game
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Return the number of tiles on the board
     *
     * @return  return th number of tiles on the board
     */
    public int getTileNumOnBoard() {
        return board.tileNumOnBoard();
    }

    @Override
    public void addGameChangeListener(GameChangeListener listener) {
        gameChangeListeners.add(listener);
    }

    @Override
    public void skip() {
        notifySkip();
    }

    @Override
    public void nextTurn() {
        roundNum++;
        currentPlayer = playerList.get(roundNum % totalPlayer);
        notifyNextTurn();
    }

    private void notifySkip() {
        for(GameChangeListener listener : gameChangeListeners) {
            listener.skip();
        }
    }

    private void notifyNextTurn() {
        for(GameChangeListener listener : gameChangeListeners) {
            listener.nextTurn();
        }
    }
}
