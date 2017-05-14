package com.programmingentrepreneur.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.camera2.params.Face;
import android.util.Log;

import static com.programmingentrepreneur.popularmovies.data.MovieContract.*;
/**
 * Created by phili on 4/29/2017.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = MovieDBHelper.class.getSimpleName();

    // name & version
    public static final String DATABASE_NAME = "movies.db";
    public static final int DATABASE_VERSION = 12;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // create the database
    @Override
    public void onCreate(SQLiteDatabase db) {


        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " +
                FavoriteEntry.TABLE_NAME + "(" + FavoriteEntry._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoriteEntry.COLUMN_TMDB_ID + " INTEGER UNIQUE NOT NULL, " +  // Int
                FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_RELEASE + " TEXT NOT NULL);";


        db.execSQL(SQL_CREATE_FAVORITE_TABLE);

//        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
//                MovieEntry.TABLE_NAME + "(" + MovieEntry._ID +
//                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                MovieEntry.COLUMN_TMDB_ID + " INTEGER UNIQUE NOT NULL, " +  // Int
//                MovieEntry.COLUMN_VOTE_COUNT + " INT NOT NULL, " +          // Int
//                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +              // Text
//                MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +           // Text
//                MovieEntry.COLUMN_GENRE_IDS + " TEXT NOT NULL, " +          // Text
//                MovieEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +      // Text
//                MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +        // Text
//                MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +      // Text
//                MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +  // Text
//                MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +         // Real
//                MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +       // Real
//                MovieEntry.COLUMN_RELEASE_DATE + " DATE NOT NULL, " +       // Date
//                MovieEntry.COLUMN_IS_FOR_ADULTS + " BOOLEAN NOT NULL, " +   // Boolean
//                MovieEntry.COLUMN_HAS_VIDEO + " BOOLEAN NOT NULL);";        // Boolean
//
//
//        final String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE " +
//                TrailerEntry.TABLE_NAME + "(" + TrailerEntry._ID +
//                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                ReviewEntry.COLUMN_TMDB_ID + " INTEGER UNIQUE NOT NULL, " +   // Int
//                TrailerEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +   // Int
//                TrailerEntry.COLUMN_SIZE + " INT NOT NULL, " +                  // Int
//                TrailerEntry.COLUMN_KEY + " TEXT NOT NULL, " +                  // Text
//                TrailerEntry.COLUMN_NAME + " TEXT NOT NULL, " +                 // Text
//                TrailerEntry.COLUMN_SITE + " TEXT NOT NULL, " +                 // Text
//                TrailerEntry.COLUMN_TYPE + " TEXT NOT NULL);";                  // Text
//
//        final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE " +
//                ReviewEntry.TABLE_NAME + "(" + ReviewEntry._ID +
//                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                ReviewEntry.COLUMN_TMDB_ID + " INTEGER UNIQUE NOT NULL, " +   // Int
//                ReviewEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +   // Int
//                ReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +                  // Text
//                ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +                 // Text
//                ReviewEntry.COLUMN_URL + " TEXT NOT NULL);";                 // Text
//
//        db.execSQL(SQL_CREATE_MOVIE_TABLE);
//        db.execSQL(SQL_CREATE_TRAILER_TABLE);
//        db.execSQL(SQL_CREATE_REVIEW_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " top " +
            newVersion + ". OLD DATA WILL BE DESTROYED");
        // Drop the table
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME);
        onCreate(db);

    }
}
