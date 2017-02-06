package com.programmingentrepreneur.popularmovies;

/**
 * Created by phili on 2/6/2017.
 */

/**
 * An enum which will be used to determine the order in which movies should be fetched
 */
public enum MovieSorting {
    TOP_RATED(1),
    POPULAR(0);

    private final int value;

    MovieSorting(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }

    public static MovieSorting getInstanceFromValue(int value){
        for(MovieSorting sort : values()){
            if(sort.getValue() == value)
                return sort;
        }
        return null;
    }
}
