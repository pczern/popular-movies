package com.programmingentrepreneur.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by phili on 1/30/2017.
 */

public class Movie implements Parcelable{


    /* Member variables for storing current Movie */
    private String title;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private int[] genreIds;
    private boolean adult;
    private int id;
    private String originalTitle;
    private String originalLanguage;
    private String backdropPath;
    private String popularity;
    private int voteCount;
    private boolean video;
    private String voteAverage;
    private List<Trailer> trailerList;
    private List<Review> reviewList;
    private boolean isFavorite;


    /* Standard Constructor expecting title */
    public Movie(String title){
        setTitle(title);
    }



    /* Getter and Setter */

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }


/* Parcelable Implementations */

    protected Movie(Parcel in) {
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        genreIds = in.createIntArray();
        adult = in.readByte() != 0;
        isFavorite = in.readByte() != 0;
        id = in.readInt();
        originalTitle = in.readString();
        originalLanguage = in.readString();
        backdropPath = in.readString();
        popularity = in.readString();
        voteCount = in.readInt();
        video = in.readByte() != 0;
        voteAverage = in.readString();
        trailerList = in.readArrayList(Trailer.class.getClassLoader());
        reviewList = in.readArrayList(Review.class.getClassLoader());
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeIntArray(genreIds);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeInt(id);
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeString(backdropPath);
        dest.writeString(popularity);
        dest.writeInt(voteCount);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(voteAverage);
        dest.writeList(trailerList);
        dest.writeList(reviewList);
    }








}
