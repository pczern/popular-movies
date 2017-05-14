package com.programmingentrepreneur.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.IllegalFormatPrecisionException;

import static com.programmingentrepreneur.popularmovies.data.MovieDBHelper.LOG_TAG;

/**
 * Created by phili on 4/30/2017.
 */

public class MovieProvider extends ContentProvider{
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDBHelper mMovieDBHelper;

//    private static final int MOVIE = 100;
//    private static final int MOVIE_WITH_ID = 101;
//    private static final int TRAILER = 200;
//    private static final int TRAILER_WITH_ID = 201;
//    private static final int REVIEW = 300;
//    private static final int REVIEW_WITH_ID = 301;
    private static final int FAVORITE = 400;
    private static final int FAVORITE_WITH_ID = 401;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;


        matcher.addURI(authority, MovieContract.FavoriteEntry.TABLE_NAME, FAVORITE);
        matcher.addURI(authority, MovieContract.FavoriteEntry.TABLE_NAME + "/#", FAVORITE_WITH_ID);

//        matcher.addURI(authority, MovieContract.MovieEntry.TABLE_NAME, MOVIE);
//        matcher.addURI(authority, MovieContract.MovieEntry.TABLE_NAME + "/#", MOVIE_WITH_ID);
//
//        matcher.addURI(authority, MovieContract.TrailerEntry.TABLE_NAME, TRAILER);
//        matcher.addURI(authority, MovieContract.TrailerEntry.TABLE_NAME + "/#", TRAILER_WITH_ID);
//
//        matcher.addURI(authority, MovieContract.ReviewEntry.TABLE_NAME, REVIEW);
//        matcher.addURI(authority, MovieContract.ReviewEntry.TABLE_NAME + "/#", REVIEW_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mMovieDBHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor retCursor;
        switch(sUriMatcher.match(uri)){
//            case MOVIE:
//            {
//                retCursor = mMovieDBHelper.getReadableDatabase().query(
//                        MovieContract.MovieEntry.TABLE_NAME,
//                        projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder);
//                return retCursor;
//            }
//            case MOVIE_WITH_ID:
//            {
//                retCursor = mMovieDBHelper.getReadableDatabase().query(
//                        MovieContract.MovieEntry.TABLE_NAME,
//                        projection,
//                        MovieContract.MovieEntry._ID + " = ?",
//                        new String[] {String.valueOf(ContentUris.parseId(uri))},
//                        null,
//                        null,
//                        sortOrder);
//                return retCursor;
//            }
//            case TRAILER:
//            {
//                retCursor = mMovieDBHelper.getReadableDatabase().query(
//                        MovieContract.MovieEntry.TABLE_NAME,
//                        projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder);
//                return retCursor;
//            }
//            case TRAILER_WITH_ID:
//            {
//                retCursor = mMovieDBHelper.getReadableDatabase().query(
//                        MovieContract.TrailerEntry.TABLE_NAME,
//                        projection,
//                        MovieContract.TrailerEntry._ID + " = ?",
//                        new String[] {String.valueOf(ContentUris.parseId(uri))},
//                        null,
//                        null,
//                        sortOrder);
//                return retCursor;
//            }
//            case REVIEW:
//            {
//                retCursor = mMovieDBHelper.getReadableDatabase().query(
//                        MovieContract.ReviewEntry.TABLE_NAME,
//                        projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder);
//                return retCursor;
//            }
//            case REVIEW_WITH_ID:
//            {
//                retCursor = mMovieDBHelper.getReadableDatabase().query(
//                        MovieContract.ReviewEntry.TABLE_NAME,
//                        projection,
//                        MovieContract.ReviewEntry._ID + " = ?",
//                        new String[] {String.valueOf(ContentUris.parseId(uri))},
//                        null,
//                        null,
//                        sortOrder);
//                return retCursor;
//            }
            case FAVORITE:
            {
                retCursor = mMovieDBHelper.getReadableDatabase().query(
                        MovieContract.FavoriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case FAVORITE_WITH_ID:
            {
                retCursor = mMovieDBHelper.getReadableDatabase().query(
                        MovieContract.FavoriteEntry.TABLE_NAME,
                        projection,
                        MovieContract.FavoriteEntry._ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
//            case MOVIE:
//                return MovieContract.MovieEntry.CONTENT_DIR_TYPE;
//            case MOVIE_WITH_ID:
//                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
//            case TRAILER:
//                return MovieContract.TrailerEntry.CONTENT_DIR_TYPE;
//            case TRAILER_WITH_ID:
//                return MovieContract.TrailerEntry.CONTENT_ITEM_TYPE;
//            case REVIEW:
//                return MovieContract.ReviewEntry.CONTENT_DIR_TYPE;
//            case REVIEW_WITH_ID:
//                return MovieContract.ReviewEntry.CONTENT_ITEM_TYPE;
            case FAVORITE:
                return MovieContract.FavoriteEntry.CONTENT_DIR_TYPE;
            case FAVORITE_WITH_ID:
                return MovieContract.FavoriteEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)){
//            case TRAILER:
//            {
//                long _id = db.insert(MovieContract.TrailerEntry.TABLE_NAME, null, values);
//                // insert unless it is already contained in the database
//                if(_id > 0) {
//                    returnUri = MovieContract.TrailerEntry.buildTrailersUri(_id);
//                }else{
//                    throw new android.database.SQLException("Failed to insert row into " + uri);
//                }
//                break;
//            }
//            case REVIEW:
//            {
//                long _id = db.insert(MovieContract.ReviewEntry.TABLE_NAME, null, values);
//                // insert unless it is already contained in the database
//                if(_id > 0) {
//                    returnUri = MovieContract.ReviewEntry.buildReviewsUri(_id);
//                }else{
//                    throw new android.database.SQLException("Failed to insert row into " + uri);
//                }
//                break;
//            }
//            case MOVIE:
//            {
//                long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
//                // insert unless it is already contained in the database
//                if(_id > 0) {
//                    returnUri = MovieContract.MovieEntry.buildMoviesUri(_id);
//                }else{
//                    throw new android.database.SQLException("Failed to insert row into " + uri);
//                }
//                break;
//            }
             case FAVORITE:
            {
                long _id = db.insert(MovieContract.FavoriteEntry.TABLE_NAME, null, values);
                // insert unless it is already contained in the database
                if(_id > 0) {
                    returnUri = MovieContract.FavoriteEntry.buildFavoriteUri(_id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
            {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch(match){
//            case MOVIE:
//                numDeleted = db.delete(
//                        MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
//                // reset _ID
//                db.execSQL("DELETE FROM SQLITE SEQUENCE WHERE NAME = '" + MovieContract.MovieEntry.TABLE_NAME + "'");
//                break;
//
//            case MOVIE_WITH_ID:
//                numDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME,
//                        MovieContract.MovieEntry._ID + " = ?",
//                        new String[]{String.valueOf(ContentUris.parseId(uri))});
//                // reset _ID
//                db.execSQL("DELETE FROM SQLITE SEQUENCE WHERE NAME = '" + MovieContract.MovieEntry.TABLE_NAME + "'");
//                break;
//            case TRAILER:
//                numDeleted = db.delete(
//                        MovieContract.TrailerEntry.TABLE_NAME, selection, selectionArgs);
//                // reset _ID
//                db.execSQL("DELETE FROM SQLITE SEQUENCE WHERE NAME = '" + MovieContract.TrailerEntry.TABLE_NAME + "'");
//                break;
//            case TRAILER_WITH_ID:
//                numDeleted = db.delete(MovieContract.TrailerEntry.TABLE_NAME,
//                        MovieContract.TrailerEntry._ID + " = ?",
//                        new String[]{String.valueOf(ContentUris.parseId(uri))});
//                // reset _ID
//                db.execSQL("DELETE FROM SQLITE SEQUENCE WHERE NAME = '" + MovieContract.TrailerEntry.TABLE_NAME + "'");
//                break;
//            case REVIEW:
//                numDeleted = db.delete(
//                        MovieContract.ReviewEntry.TABLE_NAME, selection, selectionArgs);
//                // reset _ID
//                db.execSQL("DELETE FROM SQLITE SEQUENCE WHERE NAME = '" + MovieContract.ReviewEntry.TABLE_NAME + "'");
//                break;
//            case REVIEW_WITH_ID:
//                numDeleted = db.delete(MovieContract.ReviewEntry.TABLE_NAME,
//                        MovieContract.ReviewEntry._ID + " = ?",
//                        new String[]{String.valueOf(ContentUris.parseId(uri))});
//                // reset _ID
//                db.execSQL("DELETE FROM SQLITE SEQUENCE WHERE NAME = '" + MovieContract.ReviewEntry.TABLE_NAME + "'");
//                break;
            case FAVORITE:
                numDeleted = db.delete(
                        MovieContract.FavoriteEntry.TABLE_NAME, selection, selectionArgs);
                // reset _ID
//                db.execSQL("DELETE FROM SQLITE SEQUENCE WHERE NAME = '" + MovieContract.FavoriteEntry.TABLE_NAME + "'");
                break;
            case FAVORITE_WITH_ID:
                numDeleted = db.delete(MovieContract.FavoriteEntry.TABLE_NAME,
                        MovieContract.FavoriteEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                // reset _ID
               // db.execSQL("DELETE FROM SQLITE SEQUENCE WHERE NAME = '" + MovieContract.FavoriteEntry.TABLE_NAME + "'");
                break;
        }
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values){
        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch(match){
//            case MOVIE:
//                return myBulkInsert(db, uri, values, MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry._ID);
//            case TRAILER:
//                return myBulkInsert(db, uri, values, MovieContract.MovieEntry.TABLE_NAME, MovieContract.TrailerEntry._ID);
//            case REVIEW:
//                return myBulkInsert(db, uri, values, MovieContract.MovieEntry.TABLE_NAME, MovieContract.ReviewEntry._ID);
            case FAVORITE:
                return myBulkInsert(db, uri, values, MovieContract.FavoriteEntry.TABLE_NAME, MovieContract.FavoriteEntry._ID);
            default:
                return super.bulkInsert(uri, values);
        }

    }


    private int myBulkInsert(SQLiteDatabase db, Uri uri, final ContentValues[] values, String tableName, String idColumn){
        db.beginTransaction();
        int numInserted = 0;
        try{
            for(ContentValues value : values){
                if(value == null){
                    throw new IllegalArgumentException("Cannot have null content values");
                }

                long _id = -1;

                try{
                    _id = db.insertOrThrow(tableName, null, value);

                }catch(SQLiteConstraintException e){
                    Log.w(LOG_TAG, "Attempting to insert " + value.getAsString(idColumn + " but value is already in database."));
                }
                if(_id != -1){
                    numInserted++;
                }
            }
            if(numInserted > 0){
                // If no errors, declare a successful transaction.
                // database will not populate if this is not called
                db.setTransactionSuccessful();
            }
        } finally {
            // all transactions occur at once
            db.endTransaction();
        }
        if(numInserted > 0){
            // if there was successful insertion, notify the content resolver that there
            // was a change
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numInserted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        int numUpdated = 0;

        if(values == null){
            throw new IllegalArgumentException("Cannot have null content values");

        }

        switch(sUriMatcher.match(uri)) {
//            case MOVIE: {
//                numUpdated = db.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
//                break;
//            }
//            case MOVIE_WITH_ID: {
//                numUpdated = db.update(MovieContract.MovieEntry.TABLE_NAME, values, MovieContract.MovieEntry._ID + " = ?", new String[]{String.valueOf(ContentUris.parseId(uri))});
//                break;
//            }
//            case TRAILER: {
//                numUpdated = db.update(MovieContract.TrailerEntry.TABLE_NAME, values, selection, selectionArgs);
//            }
//            case TRAILER_WITH_ID: {
//                numUpdated = db.update(MovieContract.TrailerEntry.TABLE_NAME, values, MovieContract.TrailerEntry._ID + " = ?", new String[]{String.valueOf(ContentUris.parseId(uri))});
//            }
//            case REVIEW: {
//                numUpdated = db.update(MovieContract.ReviewEntry.TABLE_NAME, values, selection, selectionArgs);
//            }
//            case REVIEW_WITH_ID: {
//                numUpdated = db.update(MovieContract.ReviewEntry.TABLE_NAME, values, MovieContract.ReviewEntry._ID + " = ?", new String[]{String.valueOf(ContentUris.parseId(uri))});
//            }
            case FAVORITE: {
                numUpdated = db.update(MovieContract.FavoriteEntry.TABLE_NAME, values, selection, selectionArgs);
            }
            case FAVORITE_WITH_ID: {
                numUpdated = db.update(MovieContract.FavoriteEntry.TABLE_NAME, values, MovieContract.FavoriteEntry._ID + " = ?", new String[]{String.valueOf(ContentUris.parseId(uri))});
            }
        }
        if(numUpdated > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numUpdated;
    }
}
