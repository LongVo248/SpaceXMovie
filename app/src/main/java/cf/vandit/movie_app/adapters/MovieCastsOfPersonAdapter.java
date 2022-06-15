package cf.vandit.movie_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.MovieDetailsActivity;
import cf.vandit.movie_app.retrofit.dto.MovieDetailDTO;

public class MovieCastsOfPersonAdapter extends RecyclerView.Adapter<MovieCastsOfPersonAdapter.MovieViewHolder> {
    private Context mContext;
    private List<MovieDetailDTO> mMovieCasts;

    public MovieCastsOfPersonAdapter(Context mContext, List<MovieDetailDTO> mMovieCasts) {
        this.mContext = mContext;
        this.mMovieCasts = mMovieCasts;
    }

    @NonNull
    @Override
    public MovieCastsOfPersonAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.casts_movie_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCastsOfPersonAdapter.MovieViewHolder holder, int position) {
        Glide.with(mContext.getApplicationContext()).load(mMovieCasts.get(position).getPoster())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.moviePosterImageView);

        if (mMovieCasts.get(position).getTitle() != null)
            holder.movieTitleTextView.setText(mMovieCasts.get(position).getTitle());
        else
            holder.movieTitleTextView.setText("");

    }

    @Override
    public int getItemCount() {
        return mMovieCasts.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public CardView movieCard;
        public ImageView moviePosterImageView;
        public TextView movieTitleTextView;
        public TextView castCharacterTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movieCard = (CardView) itemView.findViewById(R.id.card_view_show_cast);
            moviePosterImageView = (ImageView) itemView.findViewById(R.id.movie_cast_imageView);
            movieTitleTextView = (TextView) itemView.findViewById(R.id.movie_cast_title);
            castCharacterTextView = (TextView) itemView.findViewById(R.id.movie_cast_character);

            moviePosterImageView.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.31);
            moviePosterImageView.getLayoutParams().height = (int) ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31) / 0.66);

            movieCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                    intent.putExtra("movie_detail", mMovieCasts.get(getAdapterPosition()));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
