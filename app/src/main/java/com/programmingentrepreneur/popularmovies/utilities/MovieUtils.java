package com.programmingentrepreneur.popularmovies.utilities;

import android.database.Cursor;
import android.util.Log;

import com.programmingentrepreneur.popularmovies.Movie;
import com.programmingentrepreneur.popularmovies.Review;
import com.programmingentrepreneur.popularmovies.Trailer;
import com.programmingentrepreneur.popularmovies.data.MovieContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phili on 1/30/2017.
 */

public class MovieUtils {


    private static final String TAG = MovieUtils.class.getSimpleName();

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



    private static final String TMDB_TRAILER_ID = "id";
    private static final String TMDB_TRAILER_LANGUAGE = "language";
    private static final String TMDB_TRAILER_COUNTRY = "country";
    private static final String TMDB_TRAILER_KEY = "key";
    private static final String TMDB_TRAILER_NAME = "name";
    private static final String TMDB_TRAILER_SITE = "site";
    private static final String TMDB_TRAILER_SIZE = "size";
    private static final String TMDB_TRAILER_TYPE = "type";


    private static final String TMDB_REVIEW_ID = "id";
    private static final String TMDB_REVIEW_AUTHOR = "author";
    private static final String TMDB_REVIEW_CONTENT = "content";
    private static final String TMDB_REVIEW_URL = "url";


    public static Movie[] getMoviesFromCursor(Cursor cursor){
        if(cursor == null) return null;

        List<Movie> movies = new ArrayList<>();
        while(cursor.moveToNext()){

            String title = cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_DESCRIPTION));
            String posterPath = cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_POSTER_PATH));
            String release = cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_RELEASE));
            String voteAverage = cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_VOTE_AVERAGE));

            int id = cursor.getInt(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_TMDB_ID));




            Movie movie = new Movie(title);
            movie.setId(id);
            movie.setVoteAverage(voteAverage);
            movie.setReleaseDate(release);
            movie.setOverview(description);
            movie.setFavorite(true);
            movie.setPosterPath(posterPath);
            movies.add(movie);
        }
        return (movies.isEmpty() == false ? movies.toArray(new Movie[movies.size()]) : null);

    }



    public static List<Trailer> getSimpleTrailersFromJson(String trailersJsonStr) throws JSONException {


        List<Trailer> trailers = new ArrayList<>();

        JSONObject trailersJSON = new JSONObject(trailersJsonStr);

        // TMDB returns status message when an error occured so we can check if that happens and return null if so
        if (trailersJSON.has(HTTP_STATUS_MESSAGE)) {
            String error = trailersJSON.getString(HTTP_STATUS_MESSAGE);
            Log.e(TAG, error);
            return null;
        }

        JSONArray trailersArray = trailersJSON.getJSONArray(TMDB_RESULTS);



        for(int i = 0; i < trailersArray.length(); i++){
            Trailer trailer = new Trailer();
            JSONObject trailerObj = trailersArray.getJSONObject(i);

            trailer.setName(trailerObj.getString(TMDB_TRAILER_NAME));
            trailer.setKey(trailerObj.getString(TMDB_TRAILER_KEY));
            trailer.setId(trailerObj.getString(TMDB_TRAILER_ID));
            trailer.setSite(trailerObj.getString(TMDB_TRAILER_SITE));
            trailer.setSize(trailerObj.getInt(TMDB_TRAILER_SIZE));
            trailer.setType(trailerObj.getString(TMDB_TRAILER_TYPE));


            trailers.add(trailer);
        }

        return trailers;

    }





    public static List<Review> getSimpleReviewsFromJson(String reviewsJsonStr) throws JSONException {

        List<Review> reviews = new ArrayList<>();


        JSONObject reviewsJSON = new JSONObject(reviewsJsonStr);

        // TMDB returns status message when an error occured so we can check if that happens and return null if so
        if (reviewsJSON.has(HTTP_STATUS_MESSAGE)) {
            String error = reviewsJSON.getString(HTTP_STATUS_MESSAGE);
            Log.e(TAG, error);
            return null;
        }

        JSONArray reviewsArray = reviewsJSON.getJSONArray(TMDB_RESULTS);


        for(int i = 0; i < reviewsArray.length(); i++){
            Review review = new Review();
            JSONObject trailerObj = reviewsArray.getJSONObject(i);


            review.setId(trailerObj.getString(TMDB_TRAILER_ID));
            if(trailerObj.has(TMDB_REVIEW_AUTHOR))
                review.setAuthor(trailerObj.getString(TMDB_REVIEW_AUTHOR));
            if(trailerObj.has(TMDB_REVIEW_CONTENT))
                review.setContent(trailerObj.getString(TMDB_REVIEW_CONTENT));
            if(trailerObj.has(TMDB_REVIEW_URL))
                review.setUrl(trailerObj.getString(TMDB_REVIEW_URL));

            reviews.add(review);
        }
        return reviews;

    }





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
