package com.programmingentrepreneur.popularmovies;

/**
 * Created by phili on 2/6/2017.
 */

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An enum which will be used to determine the order in which movies should be fetched
 * the integers we pass in should be equal with the ones we use in strings.xml
 */
public class MovieSorting {

    // list of accepted constants
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SORT_POPULAR, SORT_TOP_RATED, SORT_FAVORITE})
    public @interface Sorting {}

    public static final int SORT_POPULAR = 0;
    public static final int SORT_TOP_RATED = 1;
    public static final int SORT_FAVORITE = 2;








}
