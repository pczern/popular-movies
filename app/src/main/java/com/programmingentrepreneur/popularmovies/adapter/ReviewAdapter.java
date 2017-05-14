package com.programmingentrepreneur.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.programmingentrepreneur.popularmovies.R;
import com.programmingentrepreneur.popularmovies.Review;

import java.util.List;

/**
 * Created by phili on 5/14/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {


    List<Review> mReview;

    private final ReviewAdapter.ReviewAdapterOnClickHandler mClickHandler;


    /**
     * Standard constructer
     * @param clickHandler Handler for handling the item clicks
     */

    public ReviewAdapter(Context context, ReviewAdapter.ReviewAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    /**
     * Interface for handling item click events with Movie objects
     */
    public interface ReviewAdapterOnClickHandler{
        void onClick(Review Review);
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
    public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new ReviewAdapter.ReviewAdapterViewHolder(view);
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
    public void onBindViewHolder(ReviewAdapter.ReviewAdapterViewHolder holder, int position) {
        Review review = mReview.get(position);
        holder.mContentTextView.setText(review.getContent());
        holder.mAuthorTextView.setText(review.getAuthor());


        //Picasso.with(holder.itemView.getContext()).load("http://image.tmdb.org/t/p/w185" + .getPosterPath()).into(holder.mImageView);
    }


    /**
     * Sets the amount of items our RecyclerView will contain
     * @return The amount of movies
     */
    @Override
    public int getItemCount() {
        if(mReview != null)
            return mReview.size();
        return 0;
    }




/**
 * ViewHolder which holds our ImageView and sets the ClickListener
 */
public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView mContentTextView;
    private final TextView mAuthorTextView;
    public ReviewAdapterViewHolder(View itemView) {
        super(itemView);
        mContentTextView = (TextView) itemView.findViewById(R.id.tv_content);
        mAuthorTextView = (TextView) itemView.findViewById(R.id.tv_author);
        itemView.setOnClickListener(this);
    }

    /**
     * When an item is clicked call the onClick method of the interface defined above
     * @param v The clicked view
     */
    @Override
    public void onClick(View v) {
        mClickHandler.onClick(mReview.get(this.getAdapterPosition()));
    }
}


    /**
     * Set the Reviews on the Adapter
     * @param Reviews The Reviews you want to display
     */
    public void setReviews(List<Review> Reviews){
        this.mReview = Reviews;
        notifyDataSetChanged();
    }
}
