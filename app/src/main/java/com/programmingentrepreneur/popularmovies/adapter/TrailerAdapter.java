package com.programmingentrepreneur.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.programmingentrepreneur.popularmovies.Movie;
import com.programmingentrepreneur.popularmovies.R;
import com.programmingentrepreneur.popularmovies.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by phili on 5/14/2017.
 */

public class TrailerAdapter  extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    List<Trailer> mTrailer;

    private final TrailerAdapter.TrailerAdapterOnClickHandler mClickHandler;


    /**
     * Standard constructer
     * @param clickHandler Handler for handling the item clicks
     */

    public TrailerAdapter(Context context, TrailerAdapter.TrailerAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    /**
     * Interface for handling item click events with Movie objects
     */
    public interface TrailerAdapterOnClickHandler{
        void onClick(Trailer trailer);
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
    public TrailerAdapter.TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerAdapter.TrailerAdapterViewHolder(view);
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
    public void onBindViewHolder(TrailerAdapter.TrailerAdapterViewHolder holder, int position) {
        Trailer trailer = mTrailer.get(position);
        holder.mTextView.setText(trailer.getName());



        //Picasso.with(holder.itemView.getContext()).load("http://image.tmdb.org/t/p/w185" + .getPosterPath()).into(holder.mImageView);
    }


    /**
     * Sets the amount of items our RecyclerView will contain
     * @return The amount of movies
     */
    @Override
    public int getItemCount() {
        if(mTrailer != null)
            return mTrailer.size();
        return 0;
    }




    /**
     * ViewHolder which holds our ImageView and sets the ClickListener
     */
    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTextView;

        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(this);
        }

        /**
         * When an item is clicked call the onClick method of the interface defined above
         * @param v The clicked view
         */
        @Override
        public void onClick(View v) {
            mClickHandler.onClick(mTrailer.get(this.getAdapterPosition()));
        }
    }


    /**
     * Set the Trailers on the Adapter
     * @param trailers The trailers you want to display
     */
    public void setTrailer(List<Trailer> trailers){
        this.mTrailer = trailers;
        notifyDataSetChanged();
    }
}
