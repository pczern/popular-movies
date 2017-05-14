package com.programmingentrepreneur.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

 import android.provider.BaseColumns;

/**
 * Created by phili on 4/29/2017.
 */

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.programmingentrepreneur.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class FavoriteEntry implements BaseColumns{


        // table name
        public static final String TABLE_NAME = "favorite";
        // columns
        public static final String _ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_TMDB_ID = "tmdb_id";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_RELEASE = "release";
        // create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(FavoriteEntry.TABLE_NAME).build();

        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + FavoriteEntry.TABLE_NAME;

        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + FavoriteEntry.TABLE_NAME;

        // for building URIs on insertion
        public static Uri buildFavoriteUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }





//    public static final class MovieEntry implements BaseColumns{
//        // table name
//        public static final String TABLE_NAME = "movie";
//        // columns
//        public static final String _ID = "id";
//        public static final String COLUMN_TITLE = "title";
//        public static final String COLUMN_OVERVIEW = "description";
//        public static final String COLUMN_POSTER_PATH = "poster_path";
//        public static final String COLUMN_RELEASE_DATE = "release_date";
//        public static final String COLUMN_GENRE_IDS = "genre_ids";
//        public static final String COLUMN_IS_FOR_ADULTS = "is_for_adults";
//        public static final String COLUMN_TMDB_ID = "tmdb_id";
//        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
//        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
//        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
//        public static final String COLUMN_POPULARITY = "popularity";
//        public static final String COLUMN_VOTE_COUNT = "vote_count";
//        public static final String COLUMN_HAS_VIDEO = "has_video";
//        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
//        public static final String COLUMN_VERSION_NAME = "version_name";
//
//
//        // create content uri
//        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
//                .appendPath(MovieEntry.TABLE_NAME).build();
//
//        // create cursor of base type directory for multiple entries
//        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MovieEntry.TABLE_NAME;
//
//        // create cursor of base type item for single entry
//        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MovieEntry.TABLE_NAME;
//
//        // for building URIs on insertion
//        public static Uri buildMoviesUri(long id){
//            return ContentUris.withAppendedId(CONTENT_URI, id);
//        }
//
//    }
//
//
//
//    public static final class TrailerEntry implements BaseColumns {
//        // table name
//        public static final String TABLE_NAME = "trailer";
//        // columns
//        public static final String _ID = "id";
//        public static final String COLUMN_KEY = "key";
//        public static final String COLUMN_NAME = "name";
//        public static final String COLUMN_SITE = "site";
//        public static final String COLUMN_SIZE = "size";
//        public static final String COLUMN_TYPE = "type";
//        public static final String COLUMN_MOVIE_ID = "movie_id";
//        public static final String COLUMN_TMDB_ID = "tmdb_id";
//
//
//
//        // create content uri
//        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
//                .appendPath(TrailerEntry.TABLE_NAME).build();
//
//        // create cursor of base type directory for multiple entries
//        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TrailerEntry.TABLE_NAME;
//
//        // create cursor of base type item for single entry
//        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TrailerEntry.TABLE_NAME;
//
//        // for building URIs on insertion
//        public static Uri buildTrailersUri(long id){
//            return ContentUris.withAppendedId(CONTENT_URI, id);
//        }
//    }
//
//
//    public static final class ReviewEntry implements BaseColumns {
//        // table name
//        public static final String TABLE_NAME = "review";
//        // columns
//        public static final String _ID = "id";
//        public static final String COLUMN_AUTHOR = "author";
//        public static final String COLUMN_CONTENT = "content";
//        public static final String COLUMN_URL = "site";
//        public static final String COLUMN_MOVIE_ID = "movie_id";
//        public static final String COLUMN_TMDB_ID = "tmdb_id";
//
//
//
//        // create content uri
//        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
//                .appendPath(ReviewEntry.TABLE_NAME).build();
//
//        // create cursor of base type directory for multiple entries
//        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ReviewEntry.TABLE_NAME;
//
//        // create cursor of base type item for single entry
//        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ReviewEntry.TABLE_NAME;
//
//        // for building URIs on insertion
//        public static Uri buildReviewsUri(long id){
//            return ContentUris.withAppendedId(CONTENT_URI, id);
//        }
//
//    }


}
