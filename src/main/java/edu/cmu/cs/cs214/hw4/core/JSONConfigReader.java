package edu.cmu.cs.cs214.hw4.core;

/**
 * JSON reader for the tile type file
 */
public class JSONConfigReader {
    /**
     * Tile type contains feature with names
     */
    public class JSONTileType {
        public String leftFeature;
        public String rightFeature;
        public String upFeature;
        public String downFeature;
        public String centerFeature;
        public int typeNumber;
    }

    /**
     * JSON tile collection class
     */
    public class JSONTileCollection {
        public JSONTileType[] tileTypes;
    }
}
