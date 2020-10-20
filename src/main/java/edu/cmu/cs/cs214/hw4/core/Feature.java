package edu.cmu.cs.cs214.hw4.core;

/**
 * Feature interface
 */
public interface Feature {
    /**
     * Get the feature type
     *
     * @return  feature type
     */
    FeatureType getType();

    /**
     * Get the coat of armed mark for the tile
     *
     * @return get if the tile is with coat of armed mark
     */
    boolean getArmedMark();
}

