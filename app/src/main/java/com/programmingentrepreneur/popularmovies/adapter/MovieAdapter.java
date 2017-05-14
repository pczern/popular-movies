package com.programmingentrepreneur.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.programmingentrepreneur.popularmovies.Movie;
import com.programmingentrepreneur.popularmovies.R;
import com.squareup.picasso.Picasso;

/**
 * Created by phili on 1/30/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    Movie[] mMovies;

    private final MovieAdapterOnClickHandler mClickHandler;


    /**
     * Standard constructer
     * @param clickHandler Handler for handling the item clicks
     */

    public MovieAdapter(Context context, MovieAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    /**
     * Interface for handling item click events with Movie objects
     */
    public interface MovieAdapterOnClickHandler{
        void onClick(Movie movie);
    }


    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If the RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new MovieAdapterViewHolder that holds the View for each list item
     */
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieAdapterViewHolder(view);
    }



    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the movie
     * details for this particular position, using the "position" argument that is conveniently
     * passed into for us.
     *
     * @param holder The ViewHolder which should be updated to represent the
     *               contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Movie movie = mMovies[position];
        Picasso.with(holder.itemView.getContext()).load("http://image.tmdb.org/t/p/w185" + movie.getPosterPath()).into(holder.mImageView);
    }


    /**
     * Sets the amount of items our RecyclerView will contain
     * @return The amount of movies
     */
    @Override
    public int getItemCount() {
        if(mMovies != null)
            return mMovies.length;
        return 0;
    }




    /**
     * ViewHolder which holds our ImageView and sets the ClickListener
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mImageView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        /**
         * When an item is clicked call the onClick method of the interface defined above
         * @param v The clicked view
         */
        @Override
        public void onClick(View v) {
            mClickHandler.onClick(mMovies[this.getAdapterPosition()]);
        }
    }


    /**
     * Set the Movies on the Adapter
     * @param movies The movies you want to display
     */
    public void setMovies(Movie[] movies){
        this.mMovies = movies;
        notifyDataSetChanged();
    }
}
