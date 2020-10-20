package edu.cmu.cs.cs214.hw4.core;

import java.util.Objects;

/**
 * Center block feature if all feature is block at the center
 */
public class CenterBlock implements Feature {
    private FeatureType featureType;

    /**
     * Constructor for the class
     */
    public CenterBlock() {
        this.featureType = FeatureType.CENTER_BLOCK_TYPE;
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
     * N/A for this center
     *
     * @return  false for this feature
     */
    public boolean getArmedMark() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof CenterBlock)) {
            return false;
        }
        CenterBlock cb = (CenterBlock) o;
        return featureType == cb.featureType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(featureType);
    }

    @Override
    public String toString() {
        return "CenterBlock";
    }
}
