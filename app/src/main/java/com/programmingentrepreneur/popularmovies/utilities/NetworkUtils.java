package com.programmingentrepreneur.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.programmingentrepreneur.popularmovies.MovieSorting;
import com.programmingentrepreneur.popularmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by phili on 1/30/2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();


    private static final String TMDB_BASE_URL = "http://api.themoviedb.org";

    private static final String language = "en";
    private static final String region = "US";

    /* TMDB query constants */
    final static String API_KEY_PARAM = "api_key";
    final static String LANGUAGE_PARAM = "language";
    final static String REGION_PARAM = "region";


    //http://api.themoviedb.org/3/movie/popular?api_key=xxxx&region=US&language=en


    /**
     * Builds the URL which we'll use to fetch TMDB
     *
     * @param apiKey The API Key we got from TMDB
     * @param sorting An enum which determines in what order the movies should be fetched
     * @return The complete URL which will be used to fetch TMDB
     */

    public static URL buildMovieUrl(String apiKey, @MovieSorting.Sorting int sorting){


        Uri.Builder builder = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(REGION_PARAM, region);

        if(sorting == MovieSorting.SORT_POPULAR){
            builder.path("3/movie/popular");
        }else if(sorting == MovieSorting.SORT_TOP_RATED){
            builder.path("3/movie/top_rated");
        }else{
            return null; // not supported sort method
        }

        URL url = null;
        try{
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, url.toString());

        return url;
    }

    public static URL buildTrailerUrl(String apiKey, int id){

        if(id == 0) return null;

        Uri.Builder builder = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(REGION_PARAM, region)
                .path("3/movie/" + id + "/videos");




        URL url = null;
        try{
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, url.toString());

        return url;
    }

    public static URL buildReviewsUrl(String apiKey, int id){

        if(id == 0) return null;

        Uri.Builder builder = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(REGION_PARAM, region)
                .path("3/movie/" + id + "/reviews");




        URL url = null;
        try{
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, url.toString());

        return url;
    }





    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response
     * @return The contents of the HTTP response
     * @throws IOException Related to network and stream reading
     */

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            }else{
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
