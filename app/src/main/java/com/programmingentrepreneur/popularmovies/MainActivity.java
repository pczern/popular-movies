package com.programmingentrepreneur.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

import com.programmingentrepreneur.popularmovies.data.PopularMoviesPreferences;
import com.programmingentrepreneur.popularmovies.utilities.NetworkUtils;
import com.programmingentrepreneur.popularmovies.utilities.TMDBJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{




    private static final String TAG = MainActivity.class.getSimpleName();

    // Views
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorTextView;

    // Holds the Movies loaded from TMDB
    private Movie[] mMovies;

    // Holds the sort order
    MovieSorting sortOrder;


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
        mMovieAdapter = new MovieAdapter(this);

        // pass the Adapter to the RecyclerView
        mRecyclerView.setAdapter(mMovieAdapter);


        // get the loading indicator which shows up on low connections
        mLoadingIndicator = (ProgressBar) findViewById(R.id.progressBar);


        // If we already loaded the Movies, get them back from the savedInstanceState
        if(savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.onsave_movies))) {
            mMovies = (Movie[]) savedInstanceState.getParcelableArray(getString(R.string.onsave_movies));
            setMovies(mMovies);
        }

        sortOrder = PopularMoviesPreferences.getSortOrder(this);
        // If we couldn't get the Movies back from savedInstanceState then load the movies from TMDB
        if(mMovies == null && isOnline()) {
            loadMovies(sortOrder);
        }else{
            showErrorMessage(this.getString(R.string.no_internet));
        }


    }


    /**
     * Loads the Movies from TMDB using specified sorting and shows them
     * @param sorting Responsible for the sorting method we'll apply
     */
    private void loadMovies(MovieSorting sorting) {
        mRecyclerView.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.GONE);
        new FetchMoviesTask().execute(sorting);

    }


    /**
     * Makes the RecyclerView visible
     */
    public void showMovieList(){
        mRecyclerView.setVisibility(View.VISIBLE);
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


    /**
     * AsyncTask responsible for fetching data from TMDB outside of the UI Thread
     */
    public class FetchMoviesTask extends AsyncTask<MovieSorting, Void, Movie[]> {

        /**
         * Show the Loading Circle when starting loading
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }


        /**
         * fetched data from TMDB and parses it in an Movies Array
         * @param params The sorting method we'll use to fetch the Movies
         * @return a Movie Array
         */
        @Override
        protected Movie[] doInBackground(MovieSorting... params) {

            if (params.length == 0) {
                return null;
            }

            MovieSorting sorting = params[0];

            URL url = NetworkUtils.buildMovieUrl(getString(R.string.tmdb_api_key), sorting);

            try{
                String response = NetworkUtils.getResponseFromHttpUrl(url);
                return TMDBJsonUtils.getSimpleMoviesFromJson(response);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
                return null;
            }


        }


        /**
         * Displays the movies
         * @param movies The Movie Array we we'll use to show the current available movies
         */
        @Override
        protected void onPostExecute(Movie[] movies) {

            mLoadingIndicator.setVisibility(View.GONE);

            if(movies != null) {
                showMovieList();
                setMovies(movies);
            }else{
                showErrorMessage(getString(R.string.no_movies_from_tmdb));
            }

            Log.d(TAG, "item count: " +  mRecyclerView.getAdapter().getItemCount());
        }
    }


    /**
     * Set the movies in the Adapter and in the member variable
     * @param movies The movies you want to set
     */
    private void setMovies(Movie[] movies){
        mMovies = movies;
        mMovieAdapter.setMovies(movies);
    }



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

    @Override
    protected void onRestart() {
        super.onRestart();
        // if the sort order changed then use a the new one and load the movies with it
        MovieSorting movieSorting = PopularMoviesPreferences.getSortOrder(this);
        if(movieSorting != sortOrder){
            sortOrder = movieSorting;
            loadMovies(movieSorting);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
