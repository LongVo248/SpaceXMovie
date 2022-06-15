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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.MovieDetailsActivity;
import cf.vandit.movie_app.retrofit.RetrofitService;
import cf.vandit.movie_app.retrofit.dto.MovieDetailDTO;
import cf.vandit.movie_app.retrofit.dto.MovieRate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieCarouselAdapter extends RecyclerView.Adapter<MovieCarouselAdapter.MovieViewHolder> {

    private List<MovieDetailDTO> mMovies;
    private Context mContext;
    Call<MovieRate> movieRateCall;

    public MovieCarouselAdapter(List<MovieDetailDTO> mMovies, Context mContext) {
        this.mMovies = mMovies;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MovieCarouselAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.carousel_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCarouselAdapter.MovieViewHolder holder, int position) {
        Glide.with(mContext.getApplicationContext()).load(mMovies.get(position).getPoster())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.movie_imageView);

        if (mMovies.get(position).getTitle() != null)
            holder.movie_title.setText(mMovies.get(position).getTitle());
        else
            holder.movie_title.setText("");

        movieRateCall = RetrofitService.getMovieService().getMoveRate(mMovies.get(position).getId());
        movieRateCall.enqueue(new Callback<MovieRate>() {
            @Override
            public void onResponse(Call<MovieRate> call, Response<MovieRate> response) {
                if (response.isSuccessful()) {
                    holder.movie_rating.setText(String.format("%.1f", response.body().getRate()));
                }
            }

            @Override
            public void onFailure(Call<MovieRate> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView movie_imageView;
        public TextView movie_title;
        public TextView movie_rating;
        public CardView movie_cardView;
        public FloatingActionButton movie_fab;
//        public TextView movie_counter;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_imageView = itemView.findViewById(R.id.carousel_imageView);
            movie_title = itemView.findViewById(R.id.carousel_title);
            movie_rating = itemView.findViewById(R.id.carousel_rating);
            movie_cardView = itemView.findViewById(R.id.carousel_main_card);
            movie_fab = itemView.findViewById(R.id.carousel_play_btn);
//            movie_counter = itemView.findViewById(R.id.carousel_counter);

            movie_title.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.7);

            movie_cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                    intent.putExtra("movie_detail", mMovies.get(getAdapterPosition()));
                    mContext.startActivity(intent);
                }
            });

            movie_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                    intent.putExtra("movie_detail", mMovies.get(getAdapterPosition()));
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
