package edu.cmu.cs.cs214.hw4.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;



/**
 * Tile generator class to get all tile type with sequence for the game
 */
public class TileGenerator {
    private static final int TILE_NUMBER = 72;
    private List<JSONConfigReader.JSONTileType> tilesList;
    private Deque<JSONConfigReader.JSONTileType> shuffledDeque;
    private JSONConfigReader.JSONTileCollection tilesCollection;
    private Random rand;
    private static final String FILE_PATH = "src/main/resources/TilesCollection.json";
    private static final int TYPE_C = 2;
    private static final int TYPE_F = 5;
    private static final int TYPE_M = 12;
    private static final int TYPE_O = 14;
    private static final int TYPE_Q = 16;
    private static final int TYPE_S = 18;
    private static final boolean ARMED_MARK = false;

    /**
     * Constructor the generator
     */
    public TileGenerator() {
        this.tilesList = new ArrayList<>();
        this.shuffledDeque = new ArrayDeque<>();
        this.rand = new Random();
        this.tilesCollection = parse();
        addTileType();
        shuffleListToDeque();
    }

    /**
     * Get the tile collection
     *
     * @return  return the tile collection as JSON tile collection
     */
    public JSONConfigReader.JSONTileCollection getTilesCollection() {
        return tilesCollection;
    }

    JSONConfigReader.JSONTileCollection parse() {
        Gson gson = new Gson();
        try(Reader reader = new FileReader(new File(TileGenerator.FILE_PATH))) {
            return gson.fromJson(reader, JSONConfigReader.JSONTileCollection.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error when reading file " + TileGenerator.FILE_PATH, e);
        }
    }

    /**
     * Get a tile from the tile type
     *
     * @param index the tile type index
     * @return  return the tile for the tile type
     */
    public Tiles getTileFromCollection(int index) {
        boolean armedMarkLeft = false;
        boolean armedMarkRight = false;
        if(index == TYPE_C || index == TYPE_M || index == TYPE_O || index == TYPE_Q || index == TYPE_S) {
            armedMarkLeft = true;
        }
        if(index == TYPE_F) {
            armedMarkRight = true;
        }
        String leftFeature = tilesCollection.tileTypes[index].leftFeature;
        String rightFeature = tilesCollection.tileTypes[index].rightFeature;
        String upFeature = tilesCollection.tileTypes[index].upFeature;
        String downFeature = tilesCollection.tileTypes[index].downFeature;
        String centerFeature = tilesCollection.tileTypes[index].centerFeature;
        Feature left = getFeature(leftFeature, armedMarkLeft);
        Feature right = getFeature(rightFeature, armedMarkRight);
        Feature up = getFeature(upFeature, ARMED_MARK);
        Feature down = getFeature(downFeature, ARMED_MARK);
        Feature center = getFeature(centerFeature, ARMED_MARK);
        Segment leftSeg = new Segment(PositionOnTile.LEFT, left);
        Segment rightSeg = new Segment(PositionOnTile.RIGHT, right);
        Segment upSeg = new Segment(PositionOnTile.UP, up);
        Segment downSeg = new Segment(PositionOnTile.DOWN, down);
        Segment centerSeg = new Segment(PositionOnTile.CENTER, center);
        return new Tiles(leftSeg, rightSeg, upSeg, downSeg, centerSeg, index);
    }

    private Feature getFeature(String name, boolean armedMark) {
        Feature feature = null;
        switch(name) {
            case("Road"):
                feature = new Road();
                break;
            case("City"):
                feature = new City(armedMark);
                break;
            case("Monastery"):
                feature = new Monastery();
                break;
            case("Field"):
                feature = new Field();
                break;
            case("CenterConnect"):
                feature = new CenterConnect();
                break;
            case("CenterBlock"):
                feature = new CenterBlock();
                break;
            default:
                break;
        }
        return feature;
    }

    /**
     * Get the shuffled deque for the tile types
     *
     * @return return the shuffled deque
     */
    public Deque<JSONConfigReader.JSONTileType> getShuffledDeque() {
        return shuffledDeque;
    }

    private void addTileType() {
        // typeC
        // typeG
        // typeQ
        // typeT
        // typeX
        tilesList.add(tilesCollection.tileTypes[2]);
        tilesList.add(tilesCollection.tileTypes[6]);
        tilesList.add(tilesCollection.tileTypes[16]);
        tilesList.add(tilesCollection.tileTypes[19]);
        tilesList.add(tilesCollection.tileTypes[23]);
        for(int i = 0; i < 2; i++) {
            // typeA
            // typeF
            // typeI
            // typeM
            // typeO
            // typeS
            tilesList.add(tilesCollection.tileTypes[0]);
            tilesList.add(tilesCollection.tileTypes[5]);
            tilesList.add(tilesCollection.tileTypes[8]);
            tilesList.add(tilesCollection.tileTypes[12]);
            tilesList.add(tilesCollection.tileTypes[14]);
            tilesList.add(tilesCollection.tileTypes[18]);
        }
        for(int i = 0; i < 3; i++) {
            //  typeD
            //  typeH
            //  typeJ
            //  typeK
            //  typeL
            //  typeN
            //  typeP
            //  typeR
            tilesList.add(tilesCollection.tileTypes[3]);
            tilesList.add(tilesCollection.tileTypes[7]);
            tilesList.add(tilesCollection.tileTypes[9]);
            tilesList.add(tilesCollection.tileTypes[10]);
            tilesList.add(tilesCollection.tileTypes[11]);
            tilesList.add(tilesCollection.tileTypes[13]);
            tilesList.add(tilesCollection.tileTypes[15]);
            tilesList.add(tilesCollection.tileTypes[17]);
        }
        for(int i = 0; i < 4; i++) {
            // typeB
            // typeW
            tilesList.add(tilesCollection.tileTypes[1]);
            tilesList.add(tilesCollection.tileTypes[22]);
        }
        for(int i = 0; i < 5; i++) {
            // typeE
            tilesList.add(tilesCollection.tileTypes[4]);
        }

        for(int i = 0; i < 8; i++) {
            // typeU
            tilesList.add(tilesCollection.tileTypes[20]);
        }
        for(int i = 0; i < 9; i++) {
            // typeV
            tilesList.add(tilesCollection.tileTypes[21]);
        }
    }

    private void shuffleListToDeque() {
        int range = TILE_NUMBER - 1;
        while(range > 0) {
            int index = rand.nextInt(range);
            JSONConfigReader.JSONTileType tileType = tilesList.get(index);
            shuffledDeque.push(tileType);
            tilesList.remove(index);
            range--;
        }
        shuffledDeque.push(tilesCollection.tileTypes[3]);
    }
}