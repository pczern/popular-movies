package com.programmingentrepreneur.popularmovies.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.programmingentrepreneur.popularmovies.MovieSorting;

import static com.programmingentrepreneur.popularmovies.MovieSorting.SORT_POPULAR;

/**
 * Created by phili on 2/6/2017.
 */

public class PopularMoviesPreferences {


    /* Sort order key */
    public static final String PREF_SORT_ORDER = "sort_order";

    /* Default sort order */
    private static final @MovieSorting.Sorting int DEFAULT_SORT_ORDER = SORT_POPULAR;



    /* Static Methods for setting and getting the current sort order */

    static public void setSortOrder(Context context, @MovieSorting.Sorting int sorting) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PREF_SORT_ORDER, String.valueOf(sorting));
        editor.apply();
    }

    static public @MovieSorting.Sorting int getSortOrder(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        // ListPreference uses Strings so we have to parse them to Integers
        @MovieSorting.Sorting int sortOrder = Integer.parseInt(sp.getString(PREF_SORT_ORDER, String.valueOf(DEFAULT_SORT_ORDER)));
        // The integer we get from the Preferences will be used to retrieve the correct MovieSorting from the enum
        return sortOrder;
    }




}
