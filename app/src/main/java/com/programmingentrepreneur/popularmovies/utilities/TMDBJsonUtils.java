package com.programmingentrepreneur.popularmovies.utilities;

import android.content.Context;
import android.util.Log;

import com.programmingentrepreneur.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by phili on 1/30/2017.
 */

public class TMDBJsonUtils {


    private static final String TAG = TMDBJsonUtils.class.getSimpleName();

    /* TMDB specific JSON keys */
    private static final String HTTP_STATUS_MESSAGE = "status_message";
    private static final String TMDB_RESULTS = "results";
    private static final String TMDB_TOTAL_RESULTS = "total_results";
    private static final String TMDB_POSTER_PATH = "poster_path";
    private static final String TMDB_OVERVIEW = "overview";
    private static final String TMDB_RELEASE_DATE = "release_date";
    private static final String TMDB_GENRE_IDS = "genre_ids";
    private static final String TMDB_ID = "id";
    private static final String TMDB_ORIGINAL_TITLE = "original_title";
    private static final String TMDB_ORIGINAL_LANGUAGE = "original_language";
    private static final String TMDB_TITLE = "title";
    private static final String TMDB_BACKDROP_PATH = "backdrop_path";
    private static final String TMDB_POPULARITY = "popularity";
    private static final String TMDB_VOTE_COUNT = "vote_count";
    private static final String TMDB_VIDEO = "video";
    private static final String TMDB_VOTE_AVERAGE = "vote_average";
    private static final String TMDB_ADULT = "adult";


    /**
     * Creates an Movie Array from the JSON we get from TMDB
     * @param moviesJsonStr The JSON from TMDB
     * @return The new Movie Array
     * @throws JSONException If the JSON isn't formatted well throw an Exception
     */
    public static Movie[] getSimpleMoviesFromJson(String moviesJsonStr) throws JSONException{



        Movie[] movies = null;

        JSONObject moviesJSON = new JSONObject(moviesJsonStr);

        // TMDB returns status message when an error occured so we can check if that happens and return null if so
        if(moviesJSON.has(HTTP_STATUS_MESSAGE)){
            String error = moviesJSON.getString(HTTP_STATUS_MESSAGE);
            Log.e(TAG, error);
            return null;
        }


        // Get the Movies as JSONArray
        JSONArray moviesArray = moviesJSON.getJSONArray(TMDB_RESULTS);

        // Create a new Array with the length of the moviesArray(JSONArray)
        movies = new Movie[moviesArray.length()];

        // Parse every item in the array from an JSONObject to a Movie object and it to the Movies Array
        for(int i = 0; i < moviesArray.length(); i++){
            Movie movie = parseJsonObjectToMovie(moviesArray.getJSONObject(i));
            movies[i] = movie;
        }

        return movies;

    }


    /**
     * Parses an JSONObject to a Movie object
     * @param obj The JSONObject from which we get our Movie Data
     * @return Movie from JSONObject
     * @throws JSONException If JSON formatting is bad
     */
    public static Movie parseJsonObjectToMovie(JSONObject obj) throws JSONException{
        Movie movie = new Movie(obj.getString(TMDB_TITLE));
        movie.setOriginalTitle(obj.getString(TMDB_ORIGINAL_TITLE));
        movie.setOriginalLanguage(obj.getString(TMDB_ORIGINAL_LANGUAGE));
        movie.setId(obj.getInt(TMDB_ID));
        movie.setAdult(obj.getBoolean(TMDB_ADULT));
        movie.setBackdropPath(obj.getString(TMDB_BACKDROP_PATH) != null ? obj.getString(TMDB_BACKDROP_PATH) : null);
        movie.setVideo(obj.getBoolean(TMDB_VIDEO));
        JSONArray genreIdsJsonArray = obj.getJSONArray(TMDB_GENRE_IDS);
        int[] genreIds = new int[genreIdsJsonArray.length()];
        for(int i = 0; i < genreIdsJsonArray.length(); i++){
            genreIds[i] = genreIdsJsonArray.getInt(i);
        }
        movie.setReleaseDate(obj.getString(TMDB_RELEASE_DATE));
        movie.setGenreIds(genreIds);
        movie.setPopularity(obj.getString(TMDB_POPULARITY));
        movie.setVoteAverage(obj.getString(TMDB_VOTE_AVERAGE));
        movie.setVoteCount(obj.getInt(TMDB_VOTE_COUNT));
        movie.setOverview(obj.getString(TMDB_OVERVIEW));
        movie.setPosterPath(obj.getString(TMDB_POSTER_PATH));
        return movie;
    }
}
