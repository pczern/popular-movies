package com.programmingentrepreneur.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.programmingentrepreneur.popularmovies.adapter.MovieAdapter;
import com.programmingentrepreneur.popularmovies.adapter.ReviewAdapter;
import com.programmingentrepreneur.popularmovies.adapter.TrailerAdapter;
import com.programmingentrepreneur.popularmovies.data.MovieContract;
import com.programmingentrepreneur.popularmovies.utilities.MovieUtils;
import com.programmingentrepreneur.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class MovieDetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler, ReviewAdapter.ReviewAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Movie>{


    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private static final int ID_DETAIL_LOADER = 444;

    private boolean mIsFavorite = false;

    private Movie mMovie;
    // Views
    private ImageView mCoverView;
    private TextView mTitleView;
    private TextView mReleaseYearView;
    private TextView mDescriptonView;
    private TextView mRatingView;


    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;
    private RecyclerView mTrailerRecyclerView;
    private RecyclerView mReviewRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get views
        mCoverView = (ImageView) findViewById(R.id.iv_cover);
        mTitleView = (TextView) findViewById(R.id.tv_name);
        mReleaseYearView = (TextView) findViewById(R.id.tv_release_year);
        mDescriptonView = (TextView) findViewById(R.id.tv_description);
        mRatingView = (TextView) findViewById(R.id.tv_rating);

        mTrailerAdapter = new TrailerAdapter(this, this);
        mReviewAdapter = new ReviewAdapter(this, this);

        mTrailerRecyclerView = (RecyclerView) findViewById(R.id.rv_trailer);
        mReviewRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews);

        mTrailerRecyclerView.setAdapter(mTrailerAdapter);
        mReviewRecyclerView.setAdapter(mReviewAdapter);

        mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Set the different views according to the movie passed in through the intent
        Intent intent = getIntent();
        mMovie = intent.getParcelableExtra(getString(R.string.onsave_movie));

        if(mMovie != null) {
            mIsFavorite = mMovie.isFavorite();
            Picasso.with(this).load("http://image.tmdb.org/t/p/w780" + mMovie.getPosterPath()).into(mCoverView);

            mTitleView.setText(mMovie.getTitle());
            Log.d(TAG, mMovie.getReleaseDate());

            // set release year not date, tmdb gives 2016-08-05 we just need 2016
            mReleaseYearView.setText(mMovie.getReleaseDate().split("-")[0]);
            mRatingView.setText(mMovie.getVoteAverage() + "/10");

            mDescriptonView.setText(mMovie.getOverview());


            getSupportLoaderManager().initLoader(ID_DETAIL_LOADER, null, this);

        }










    }





    /**
     *
     * Inflates the toolbar menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_detail, menu);
        if(mIsFavorite)
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_star_white_48dp);
        else
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_star_border_white_48dp);
        return true;
    }


    /**
     *
     * If the star icon is clicked the movie gets saved as a favorite
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Makes the Back/Return Button in the Toolbar working
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }else if(id == R.id.action_favorite){

            if(!mIsFavorite) {
                // Defines an object to contain the new values to insert
                ContentValues mNewValues = new ContentValues();

                /*
                 * Sets the values of each column and inserts the movie.
                 */
                mNewValues.put(MovieContract.FavoriteEntry.COLUMN_TITLE, mMovie.getTitle());
                mNewValues.put(MovieContract.FavoriteEntry.COLUMN_DESCRIPTION, mMovie.getOverview());
                mNewValues.put(MovieContract.FavoriteEntry.COLUMN_RELEASE, mMovie.getReleaseDate());
                mNewValues.put(MovieContract.FavoriteEntry.COLUMN_POSTER_PATH, mMovie.getPosterPath());
                mNewValues.put(MovieContract.FavoriteEntry.COLUMN_VOTE_AVERAGE, mMovie.getVoteAverage());
                mNewValues.put(MovieContract.FavoriteEntry.COLUMN_TMDB_ID, mMovie.getId());

                Uri uri = getContentResolver().insert(
                        MovieContract.FavoriteEntry.CONTENT_URI,
                        mNewValues
                );
                mIsFavorite = true;

            }else{
                getContentResolver().delete(
                        MovieContract.FavoriteEntry.CONTENT_URI,   // the user dictionary content URI
                        MovieContract.FavoriteEntry.COLUMN_TMDB_ID + " = ?",
                        new String[] {String.valueOf(mMovie.getId())});
                mIsFavorite = false;
            }
            invalidateOptionsMenu();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Movie> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Movie>(this) {

            /* This String will contain the raw JSON from the results of our Github search */

            @Override
            protected void onStartLoading() {
                super.onStartLoading();





                forceLoad();



            }

            @Override
            public Movie loadInBackground() {


                Movie movie = mMovie;
                try{
                    URL trailerUrl = NetworkUtils.buildTrailerUrl(getString(R.string.tmdb_api_key), movie.getId());
                    String trailerResponse = NetworkUtils.getResponseFromHttpUrl(trailerUrl);
                    mMovie.setTrailerList(MovieUtils.getSimpleTrailersFromJson(trailerResponse));


                    URL reviewUrl = NetworkUtils.buildReviewsUrl(getString(R.string.tmdb_api_key), movie.getId());
                    String reviewResponse = NetworkUtils.getResponseFromHttpUrl(reviewUrl);
                    mMovie.setReviewList(MovieUtils.getSimpleReviewsFromJson(reviewResponse));
                    return mMovie;

                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();

                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie data) {
        if(data != null){
            mMovie = data;
            mTrailerAdapter.setTrailer(data.getTrailerList());


            mReviewAdapter.setReviews(data.getReviewList());

        }

    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {


    }


    @Override
    public void onClick(Review Review) {
        // nothing to do for the current app
    }

    @Override
    public void onClick(Trailer trailer) {
        Intent intent = null;
        if(trailer.getSite().equalsIgnoreCase("youtube"))
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
        else
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getKey()));
        startActivity(intent);
    }
}
