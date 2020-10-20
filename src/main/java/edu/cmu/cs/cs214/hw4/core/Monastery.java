package edu.cmu.cs.cs214.hw4.core;

import java.util.Objects;

/**
 * Monastery class
 */
public class Monastery implements Feature {
    private FeatureType featureType;

    /**
     * Constructor for the class
     */
    public Monastery() {
        this.featureType = FeatureType.MONASTERY_TYPE;
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
        if(!(o instanceof Monastery)) {
            return false;
        }
        Monastery m = (Monastery) o;
        return featureType == m.featureType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(featureType);
    }

    @Override
    public String toString() {
        return "Monastery";
    }
}