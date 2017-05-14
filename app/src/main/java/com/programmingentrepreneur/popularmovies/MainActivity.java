package com.programmingentrepreneur.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.programmingentrepreneur.popularmovies.adapter.MovieAdapter;
import com.programmingentrepreneur.popularmovies.data.MovieContract;
import com.programmingentrepreneur.popularmovies.data.PopularMoviesPreferences;
import com.programmingentrepreneur.popularmovies.utilities.NetworkUtils;
import com.programmingentrepreneur.popularmovies.utilities.MovieUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Movie[]>{




    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int ID_MOVIE_LOADER = 44;

    // Views
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorTextView;

    // Holds the Movies loaded from TMDB
    private Movie[] mMovies;

    // Holds the sort order
    @MovieSorting.Sorting int sortOrder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mErrorTextView = (TextView) findViewById(R.id.tv_error);

        // RecyclerView specific settings
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);



        // Create the RecyclerView.Adapter for Movies which we'll pass to our RecyclerView
        mMovieAdapter = new MovieAdapter(this, this);

        // pass the Adapter to the RecyclerView
        mRecyclerView.setAdapter(mMovieAdapter);


        // get the loading indicator which shows up on low connections
        mLoadingIndicator = (ProgressBar) findViewById(R.id.progressBar);


        // If we already loaded the Movies, get them back from the savedInstanceState
        if(savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.onsave_movies))) {
            mMovies = (Movie[]) savedInstanceState.getParcelableArray(getString(R.string.onsave_movies));
            setMovies(mMovies);
            showMovieList();
        }

        sortOrder = PopularMoviesPreferences.getSortOrder(this);
        // If we couldn't get the Movies back from savedInstanceState then load the movies from TMDB
        if(mMovies == null && isOnline()) {
            loadMovies();
        }else if(!isOnline()){ // if we don't have network connection show an error
            showErrorMessage(this.getString(R.string.no_internet));
        }


    }


    /**
     * Loads the Movies from TMDB and shows the loading indicator
     */
    private void loadMovies() {

        showLoading();



        // Start the loader which will load the movies

        getSupportLoaderManager().restartLoader(ID_MOVIE_LOADER, null, this);
    }


    /**
     * Makes the RecyclerView visible
     */
    public void showMovieList(){
        mLoadingIndicator.setVisibility(View.GONE); mErrorTextView.setVisibility(View.GONE); mRecyclerView.setVisibility(View.VISIBLE);
    }


    /**
     * Saves our current application state for restoring if necessary
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray(getString(R.string.onsave_movies), mMovies);
        super.onSaveInstanceState(outState);
    }


    /**
     * Responsible for the click action on the item in the RecyclerView
     * @param movie The movie we clicked in the RecyclerView
     */
    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(getString(R.string.onsave_movie), movie);
        startActivity(intent);
    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Movie[]>(this) {

            /* This String will contain the raw JSON from the results of our Github search */

            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                /*
                 * When we initially begin loading in the background, we want to display the
                 * loading indicator to the user
                 */
                mLoadingIndicator.setVisibility(View.VISIBLE);



                forceLoad();

                Log.d(TAG, "test2");

            }

            @Override
            public Movie[] loadInBackground() {

                Log.d(TAG, "test");

                if(sortOrder == MovieSorting.SORT_POPULAR || sortOrder == MovieSorting.SORT_TOP_RATED) {
                    URL url = NetworkUtils.buildMovieUrl(getString(R.string.tmdb_api_key), sortOrder);
                    try{
                        String response = NetworkUtils.getResponseFromHttpUrl(url);
                        return MovieUtils.getSimpleMoviesFromJson(response);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        e.printStackTrace();
                        return null;
                    }
                }else if(sortOrder == MovieSorting.SORT_FAVORITE){
                    Cursor cursor = getContentResolver().query(
                            MovieContract.FavoriteEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                    return MovieUtils.getMoviesFromCursor(cursor);
                }


                return null;
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<Movie[]> loader, Movie[] movies) {
        mLoadingIndicator.setVisibility(View.GONE);

        if(movies != null) {
            showMovieList();
            setMovies(movies);
        }else if(sortOrder == MovieSorting.SORT_FAVORITE){
            showErrorMessage(getString(R.string.no_favorite_movie));

        }else{
            showErrorMessage(getString(R.string.no_movies_from_tmdb));
        }

    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) {
        showLoading();
        setMovies(null);
    }


    public void showLoading(){

        // Remove any errors before something is loaded
        mRecyclerView.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.GONE);


        mLoadingIndicator.setVisibility(View.VISIBLE);
    }


    /**
     * AsyncTask responsible for fetching data from TMDB outside of the UI Thread
     */
//    public class FetchMoviesTask extends AsyncTask<MovieSorting, Void, Movie[]> {
//
//        /**
//         * Show the Loading Circle when starting loading
//         */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mLoadingIndicator.setVisibility(View.VISIBLE);
//        }
//
//
//        /**
//         * fetched data from TMDB and parses it in an Movies Array
//         * @param params The sorting method we'll use to fetch the Movies
//         * @return a Movie Array
//         */
//        @Override
//        protected Movie[] doInBackground(MovieSorting... params) {
//
//            if (params.length == 0) {
//                return null;
//            }
//
//            MovieSorting sorting = params[0];
//
//            URL url = NetworkUtils.buildMovieUrl(getString(R.string.tmdb_api_key), sorting);
//
//            try{
//                String response = NetworkUtils.getResponseFromHttpUrl(url);
//                return MovieUtils.getSimpleMoviesFromJson(response);
//            } catch (Exception e) {
//                Log.e(TAG, e.getMessage());
//                e.printStackTrace();
//                return null;
//            }
//
//
//        }
//
//
//        /**
//         * Displays the movies
//         * @param movies The Movie Array we we'll use to show the current available movies
//         */
//        @Override
//        protected void onPostExecute(Movie[] movies) {
//
//            mLoadingIndicator.setVisibility(View.GONE);
//
//            if(movies != null) {
//                showMovieList();
//                setMovies(movies);
//            }else{
//                showErrorMessage(getString(R.string.no_movies_from_tmdb));
//            }
//
//            Log.d(TAG, "item count: " +  mRecyclerView.getAdapter().getItemCount());
//        }
//    }


    /**
     * Set the movies in the Adapter and in the member variable
     * @param movies The movies you want to set
     */
    private void setMovies(Movie[] movies){
        mMovies = movies;
        mMovieAdapter.setMovies(movies);
    }


    /**
     * Shows the Error TextView with the error message, makes the mRecyclerView and mLoadingIndicator invisible/gone
     * @param message
     */
    private void showErrorMessage(String message) {
        mErrorTextView.setText(message);
        mErrorTextView.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
    }


    /**
     *
     * Inflates the toolbar menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    /**
     *
     * Launches the Settings Activity if the settings icon is clicked in the toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_settings){
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * If the activity restarts and the sort order (we used to load the movies) differs e.g. changed in settings activity then load the movies using the new sort order
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        // if the sort order changed then use a the new one and load the movies with it
        @MovieSorting.Sorting int movieSorting = PopularMoviesPreferences.getSortOrder(this);
        if(movieSorting != sortOrder){
            sortOrder = movieSorting;
            Log.d(TAG, "what" + sortOrder);
            loadMovies();
        }
    }


    /**
     * A method for checking if we are online
     * @return network connectivity as boolean
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
