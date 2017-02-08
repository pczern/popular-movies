package com.programmingentrepreneur.popularmovies.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.programmingentrepreneur.popularmovies.MovieSorting;

/**
 * Created by phili on 2/6/2017.
 */

public class PopularMoviesPreferences {


    /* Sort order key */
    public static final String PREF_SORT_ORDER = "sort_order";

    /* Default sort order */
    private static final int DEFAULT_SORT_ORDER = MovieSorting.POPULAR.getValue();



    /* Static Methods for setting and getting the current sort order */

    static public void setSortOrder(Context context, MovieSorting sorting) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PREF_SORT_ORDER, String.valueOf(sorting.getValue()));
        editor.apply();
    }

    static public MovieSorting getSortOrder(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        // ListPreference uses Strings so we have to parse them to Integers
        int sortOrder = Integer.parseInt(sp.getString(PREF_SORT_ORDER, String.valueOf(DEFAULT_SORT_ORDER)));
        // The integer we get from the Preferences will be used to retrieve the correct MovieSorting from the enum
        return MovieSorting.getInstanceFromValue(sortOrder);
    }




}
