package com.programmingentrepreneur.popularmovies;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {


    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    // Views
    private ImageView mCoverView;
    private TextView titleView;
    private TextView releaseYearView;
    private TextView descriptonView;
    private TextView ratingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get views
        mCoverView = (ImageView) findViewById(R.id.iv_cover);
        titleView = (TextView) findViewById(R.id.tv_name);
        releaseYearView = (TextView) findViewById(R.id.tv_release_year);
        descriptonView = (TextView) findViewById(R.id.tv_description);
        ratingView = (TextView) findViewById(R.id.tv_rating);


        // Set the different views according to the movie passed in through the intent
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(getString(R.string.onsave_movie));
        if(movie != null) {
            Picasso.with(this).load("http://image.tmdb.org/t/p/w780" + movie.getPosterPath()).into(mCoverView);

            titleView.setText(movie.getTitle());
            Log.d(TAG, movie.getReleaseDate());

            // set release year not date, tmdb gives 2016-08-05 we just need 2016
            releaseYearView.setText(movie.getReleaseDate().split("-")[0]);
            ratingView.setText(movie.getVoteAverage() + "/10");

            descriptonView.setText(movie.getOverview());
        }



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Makes the Back/Return Button in the Toolbar working
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
