package edu.cmu.cs.cs214.hw4.core;

import java.util.Objects;

/**
 * Center block feature if all feature is connected at the center for road and city
 */
public class CenterConnect implements Feature {
    private FeatureType featureType;

    /**
     * Constructor for the class
     */
    public CenterConnect() {
        this.featureType = FeatureType.CENTER_CONNECT_TYPE;
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
        if(!(o instanceof CenterConnect)) {
            return false;
        }
        CenterConnect cc = (CenterConnect) o;
        return featureType == cc.featureType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(featureType);
    }

    @Override
    public String toString() {
        return "CenterConnect";
    }
}