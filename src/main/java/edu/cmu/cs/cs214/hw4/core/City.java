package edu.cmu.cs.cs214.hw4.core;

import java.util.Objects;

/**
 * City class as a feature
 */
public class City implements Feature {
    private FeatureType featureType;
    private boolean armedMark;

    /**
     * Constructor for the class
     *
     * @param armedMark if the feature contains a armed mark
     */
    public City(boolean armedMark) {
        this.featureType = FeatureType.CITY_TYPE;
        this.armedMark = armedMark;
    }

    /**
     * Get if the city has the armed mark
     *
     * @return  return the armed mark for this feature
     */
    public boolean getArmedMark() {
        return armedMark;
    }

    /**
     * Get the type of the feature
     *
     * @return  return the feature type
     */
    public FeatureType getType() {
        return featureType;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof City)) {
            return false;
        }
        City c = (City) o;
        return featureType == c.featureType && armedMark == c.armedMark;
    }

    @Override
    public int hashCode() {
        return Objects.hash(featureType, armedMark);
    }

    @Override
    public String toString() {
        return "City";
    }
}