package edu.cmu.cs.cs214.hw4.core;

import java.util.Objects;

/**
 * Field feature
 */
public class Field implements Feature {
    private FeatureType featureType;

    /**
     * Constructor for the class
     */
    public Field() {
        this.featureType = FeatureType.FIELD_TYPE;
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
        if(!(o instanceof Field)) {
            return false;
        }
        Field f = (Field) o;
        return featureType == f.featureType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(featureType);
    }

    @Override
    public String toString() {
        return "Field";
    }
}