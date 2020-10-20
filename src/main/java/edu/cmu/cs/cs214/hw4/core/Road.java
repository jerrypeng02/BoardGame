package edu.cmu.cs.cs214.hw4.core;

import java.util.Objects;

/**
 * Road class
 */
public class Road implements Feature {
    private FeatureType featureType;

    /**
     * Constructor for the class
     */
    public Road() {
        this.featureType = FeatureType.ROAD_TYPE;
    }

    /**
     * Get the type of the feature
     *
     * @return  return the feature type
     */
    public FeatureType getType() {
        return featureType;
    }

    /**
     * N/A for this feature
     *
     * @return  false for this feature
     */
    public boolean getArmedMark() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Road)) {
            return false;
        }
        Road r = (Road) o;
        return featureType == r.featureType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(featureType);
    }

    @Override
    public String toString() {
        return "Road";
    }
}