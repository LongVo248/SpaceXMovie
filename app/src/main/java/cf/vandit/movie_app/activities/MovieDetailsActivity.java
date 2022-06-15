package cf.vandit.movie_app.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.adapters.CommentAdapter;
import cf.vandit.movie_app.adapters.MovieCastsAdapter;
import cf.vandit.movie_app.database.DatabaseHelper;
import cf.vandit.movie_app.database.movies.FavMovie;
import cf.vandit.movie_app.database.movies.MovieDatabase;
import cf.vandit.movie_app.retrofit.RetrofitService;
import cf.vandit.movie_app.retrofit.dto.MovieCastDTO;
import cf.vandit.movie_app.retrofit.dto.MovieDetailDTO;
import cf.vandit.movie_app.retrofit.dto.MovieEvaluateLoad;
import cf.vandit.movie_app.retrofit.dto.MovieEvaluateResponse;
import cf.vandit.movie_app.retrofit.dto.MovieGenreDTO;
import cf.vandit.movie_app.retrofit.dto.MovieRate;
import cf.vandit.movie_app.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieDetailDTO movieDetail = new MovieDetailDTO();

    private ImageView movie_poster;
    private AVLoadingIndicatorView movie_progress_bar;
    private ImageView movie_back_btn;
    private ImageView movie_favourite_btn;

    private FloatingActionButton movie_stream_fab;

    private TextView movie_title;
    private TextView movie_year;
    private TextView movie_genre;
    private TextView movie_duration;
    private TextView movie_story_line;

    private TextView movie_year_separator;
    private TextView movie_genre_separator;

    private TextView movie_story_line_heading;
    private TextView movie_star_cast_heading;

    private TextView ratingNumber;

    private RatingBar movie_rating;
    private int ratingChoose;
    private ImageView acc_img;
    private EditText acc_evaluate_content;
    private Date time_evaluate;
    private Button btn_add_evalaute;
    private List<MovieEvaluateLoad> evaluateLoads;

    private RecyclerView movie_cast;

    private RecyclerView movie_recommended;

    private Call<MovieDetailDTO> mMovieDetailsCall;
    private Call<List<MovieCastDTO>> mMovieCreditsCall;

    private List<MovieCastDTO> mCasts;

    private MovieCastsAdapter mCastAdapter;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movie_back_btn = findViewById(R.id.movie_details_back_btn);
        movie_favourite_btn = findViewById(R.id.movie_details_favourite_btn);

        movie_stream_fab = findViewById(R.id.movie_details_fab);
        movie_story_line_heading = findViewById(R.id.movie_details_storyline_heading);

        movie_progress_bar = findViewById(R.id.movie_details_progress_bar);

        movie_poster = findViewById(R.id.movie_details_imageView);
        movie_title = findViewById(R.id.movie_details_title);
        movie_year = findViewById(R.id.movie_details_year);
        movie_genre = findViewById(R.id.movie_details_genre);
        movie_duration = findViewById(R.id.movie_details_duration);
        movie_story_line = findViewById(R.id.movie_details_storyline);
        movie_cast = findViewById(R.id.movie_details_cast);
        movie_recommended = findViewById(R.id.movie_details_reviewcomment);
        ratingNumber = findViewById(R.id.movie_details_rating_default);
        movie_year_separator = findViewById(R.id.movie_details_year_separator);
        movie_genre_separator = findViewById(R.id.movie_details_genre_separator);

        movie_star_cast_heading = (TextView) findViewById(R.id.movie_details_cast_heading);
        movie_cast = (RecyclerView) findViewById(R.id.movie_details_cast);
        mCasts = new ArrayList<>();
        mCastAdapter = new MovieCastsAdapter(MovieDetailsActivity.this, mCasts);
        movie_cast.setAdapter(mCastAdapter);
        movie_cast.setLayoutManager(new LinearLayoutManager(MovieDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));

        movie_rating = findViewById(R.id.movie_details_rating);
        acc_evaluate_content = findViewById(R.id.acc_detail_comment);
        acc_img = findViewById(R.id.acc_img);
        btn_add_evalaute = findViewById(R.id.movie_detail_add_comment_btn);

        evaluateLoads = new ArrayList<>();
        commentAdapter = new CommentAdapter(MovieDetailsActivity.this, evaluateLoads);
        movie_recommended = (RecyclerView) findViewById(R.id.movie_details_reviewcomment);
        movie_recommended.setAdapter(commentAdapter);
        movie_recommended.setLayoutManager(new LinearLayoutManager(MovieDetailsActivity.this, LinearLayoutManager.VERTICAL, false));

        Intent receivedIntent = getIntent();
        movieDetail = (MovieDetailDTO) receivedIntent.getSerializableExtra("movie_detail");

        if (movieDetail == null) finish();

        final FavMovie favMovie = (FavMovie) getIntent().getSerializableExtra("name");

        movie_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingChoose = (int) rating;
            }
        });
        btn_add_evalaute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSendComment();

            }
        });

        movie_stream_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StreamMovie();
            }
        });

        movie_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        loadActivity(favMovie);
    }

    private void loadActivity(FavMovie mFavMovie) {
        final MovieDetailDTO movieDetailDTO = new MovieDetailDTO();
        mMovieDetailsCall = RetrofitService.getMovieService().getMovieDetailById(movieDetail.getId());
        mMovieDetailsCall.enqueue(new Callback<MovieDetailDTO>() {
            @Override
            public void onResponse(Call<MovieDetailDTO> call, Response<MovieDetailDTO> response) {
                if (!response.isSuccessful()) {
                    mMovieDetailsCall = call.clone();
                    mMovieDetailsCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;

                movie_stream_fab.setEnabled(true);

                Glide.with(getApplicationContext())
                        .load(response.body().getPoster())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                movie_progress_bar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                movie_progress_bar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(movie_poster);

                if (response.body().getTitle() != null)
                    movie_title.setText(response.body().getTitle());
                else
                    movie_title.setText("");

                if (response.body().getDetail() != null && !response.body().getDetail().trim().isEmpty()) {
                    movie_story_line_heading.setVisibility(View.VISIBLE);
                    movie_story_line.setText(response.body().getDetail());
                } else {
                    movie_story_line.setText("");
                }
                String day = new StringBuilder().append(response.body().getReleaseDate().get(2))
                        .append("-")
                        .append(response.body().getReleaseDate().get(1))
                        .append("-")
                        .append(response.body().getReleaseDate().get(0)).toString();
                setStarRating(response.body().getId());
                setFavourite(response.body().getId(), response.body().getPoster(), response.body().getTitle(), mFavMovie);
                setYear(day);
                setGenres(response.body().getId());
                setDuration(response.body().getMovieDuration());
                setCasts(response.body().getId());
                loadEvaluate(response.body().getId());
            }

            @Override
            public void onFailure(Call<MovieDetailDTO> call, Throwable t) {

            }
        });
    }

    private void setStarRating(int id) {
        Call<MovieRate> movieRateCall = RetrofitService.getMovieService().getMoveRate(id);
        movieRateCall.enqueue(new Callback<MovieRate>() {
            @Override
            public void onResponse(Call<MovieRate> call, Response<MovieRate> response) {
                if (response.isSuccessful()) {
                    ratingNumber.setText(String.format("%.1f", response.body().getRate()));
                }
            }

            @Override
            public void onFailure(Call<MovieRate> call, Throwable t) {

            }
        });
        Glide.with(getApplicationContext()).load(MainActivity.accountInfo.getAvatar())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(acc_img);
    }


    private void setFavourite(final Integer movieId, final String posterPath, final String movieTitle, final FavMovie mFavMovie) {
        if (DatabaseHelper.isFavMovie(MovieDetailsActivity.this, movieId)) {
            movie_favourite_btn.setTag(Constants.TAG_FAV);
            movie_favourite_btn.setImageResource(R.drawable.ic_favourite_filled);
            movie_favourite_btn.setColorFilter(Color.argb(1, 236, 116, 85));
        } else {
            movie_favourite_btn.setTag(Constants.TAG_NOT_FAV);
            movie_favourite_btn.setImageResource(R.drawable.ic_favourite_outlined);
        }

        movie_favourite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                class SaveMovie extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        FavMovie favMovie = new FavMovie(movieId, movieTitle, posterPath);
                        MovieDatabase.getInstance(getApplicationContext())
                                .movieDao()
                                .insertMovie(favMovie);

                        return null;
                    }
                }

                class DeleteMovie extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        MovieDatabase.getInstance(getApplicationContext())
                                .movieDao()
                                .deleteMovieById(movieId);

                        return null;
                    }
                }

                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if ((int) movie_favourite_btn.getTag() == Constants.TAG_FAV) {
                    movie_favourite_btn.setTag(Constants.TAG_NOT_FAV);
                    movie_favourite_btn.setImageResource(R.drawable.ic_favourite_outlined);
                    DeleteMovie deleteMovie = new DeleteMovie();
                    deleteMovie.execute();
                } else {
                    movie_favourite_btn.setTag(Constants.TAG_FAV);
                    movie_favourite_btn.setImageResource(R.drawable.ic_favourite_filled);
                    movie_favourite_btn.setColorFilter(Color.argb(1, 236, 116, 85));
                    SaveMovie saveMovie = new SaveMovie();
                    saveMovie.execute();
                }
            }
        });
    }

    private void setYear(String releaseDateString) {
        movie_year.setText(releaseDateString);
        if (releaseDateString != null && !releaseDateString.trim().isEmpty()) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            try {
                Date releaseDate = sdf1.parse(releaseDateString);
                movie_year.setText("2022");
//                movie_year.setText(sdf2.format(releaseDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            movie_year.setText("");
        }
    }

    private void setGenres(int id) {
        Call<List<MovieGenreDTO>> getListGenre = RetrofitService.getMovieService().getGenReByMovie(id);
        getListGenre.enqueue(new Callback<List<MovieGenreDTO>>() {
            @Override
            public void onResponse(Call<List<MovieGenreDTO>> call, Response<List<MovieGenreDTO>> response) {
                if (response.isSuccessful()) {
                    String genres = "";
                    if (response.body() != null) {
                        if (response.body().size() < 3) {
                            for (int i = 0; i < response.body().size(); i++) {
                                if (response.body().get(i) == null) continue;
                                if (i == response.body().size() - 1) {
                                    if (response.body().get(i).getName().equals("Science Fiction")) {
                                        genres = genres.concat("Sci-Fi");
                                    } else {
                                        genres = genres.concat(response.body().get(i).getName());
                                    }
                                } else {
                                    if (response.body().get(i).getName().equals("Science Fiction")) {
                                        genres = genres.concat("Sci-Fi" + ", ");
                                    } else {
                                        genres = genres.concat(response.body().get(i).getName() + ", ");
                                    }
                                }
                            }
                        } else {
                            for (int i = 0; i < 3; i++) {
                                if (response.body().get(i) == null) continue;
                                if (i == 2) {
                                    if (response.body().get(i).getName().equals("Science Fiction")) {
                                        genres = genres.concat("Sci-Fi");
                                    } else {
                                        genres = genres.concat(response.body().get(i).getName());
                                    }
                                } else {
                                    if (response.body().get(i).getName().equals("Science Fiction")) {
                                        genres = genres.concat("Sci-Fi" + ", ");
                                    } else {
                                        genres = genres.concat(response.body().get(i).getName() + ", ");
                                    }
                                }
                            }
                        }
                    }
                    String x = movie_year.getText().toString();
                    if (!x.equals("") && !genres.equals("")) {
                        movie_year_separator.setVisibility(View.VISIBLE);
                    }
                    movie_genre.setText(genres);
                }
            }

            @Override
            public void onFailure(Call<List<MovieGenreDTO>> call, Throwable t) {

            }
        });
    }

    private void setDuration(String runtime) {
        movie_duration.setText(runtime);
    }

//    private void setTrailers() {
//        ApiInterface apiService = ApiClient.getMovieApi();
//        mMovieTrailersCall = apiService.getMovieVideos(movieId, Constants.API_KEY);
//        mMovieTrailersCall.enqueue(new Callback<TrailersResponse>() {
//            @Override
//            public void onResponse(Call<TrailersResponse> call, Response<TrailersResponse> response) {
//                if (!response.isSuccessful()) {
//                    mMovieTrailersCall = call.clone();
//                    mMovieTrailersCall.enqueue(this);
//                    return;
//                }
//
//                if (response.body() == null) return;
//                if (response.body().getVideos() == null) return;
//
//                for (Trailer video : response.body().getVideos()) {
//                    if (video != null && video.getSite() != null && video.getSite().equals("YouTube") && video.getType() != null && video.getType().equals("Trailer"))
//                        mTrailers.add(video);
//                }
//
//                if(!mTrailers.isEmpty()) {
//                    movie_trailer_heading.setVisibility(View.VISIBLE);
//                }
//
//                mTrailerAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<TrailersResponse> call, Throwable t) {
//
//            }
//        });
//    }

    private void setCasts(int id) {
        mMovieCreditsCall = RetrofitService.getMovieService().getCastByMovie(id);
        mMovieCreditsCall.enqueue(new Callback<List<MovieCastDTO>>() {
            @Override
            public void onResponse(Call<List<MovieCastDTO>> call, Response<List<MovieCastDTO>> response) {
                if (!response.isSuccessful()) {
                    mMovieCreditsCall = call.clone();
                    mMovieCreditsCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;

                for (MovieCastDTO castDTO : response.body()) {
                    if (castDTO != null && castDTO.getName() != null)
                        mCasts.add(castDTO);
                }

                if (!mCasts.isEmpty()) {
                    movie_star_cast_heading.setVisibility(View.VISIBLE);
                }

                mCastAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MovieCastDTO>> call, Throwable t) {

            }
        });
    }

    private void setSendComment() {
        List<MovieEvaluateResponse> movieEvalutes = new ArrayList<>();
        MovieEvaluateResponse movieEvalute = new MovieEvaluateResponse();
        movieEvalute.setMovieDetailDTO(movieDetail);
        movieEvalute.setAccountInfo(MainActivity.accountInfo);
        movieEvalute.setEvaluateContent(acc_evaluate_content.getText().toString());
        movieEvalute.setEvaluateTime(new Date().getTime());
        movieEvalute.setEvaluateRate(ratingChoose);
        movieEvalutes.add(movieEvalute);
        Call<MovieDetailDTO> sendEvaluate = RetrofitService.getMovieService().updateEvaluate(
                movieEvalute, movieDetail.getId(), MainActivity.accountInfo.getId());
        sendEvaluate.enqueue(new Callback<MovieDetailDTO>() {
            @Override
            public void onResponse(Call<MovieDetailDTO> call, Response<MovieDetailDTO> response) {
                if (response.isSuccessful()){
                    Toast.makeText(MovieDetailsActivity.this, "Successful evaluation!", Toast.LENGTH_SHORT).show();
                    for (MovieEvaluateLoad movieEvaluateLoad: evaluateLoads) {
                        if (movieEvaluateLoad.getAccountInfo() == movieEvalute.getAccountInfo()){
                            evaluateLoads.remove(movieEvaluateLoad);
                            MovieEvaluateLoad movieEvaluateLoad1 = new MovieEvaluateLoad();
                            movieEvaluateLoad1.setAccountInfo(movieEvalute.getAccountInfo());
                            movieEvaluateLoad1.setEvaluateRate(movieEvalute.getEvaluateRate());
                            movieEvaluateLoad1.setEvaluateContent(movieEvalute.getEvaluateContent());
                            movieEvaluateLoad1.setEvaluateTime(movieEvalute.getEvaluateTime());
                            evaluateLoads.add(movieEvaluateLoad1);
                            commentAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDetailDTO> call, Throwable t) {

            }
        });
    }

    private void loadEvaluate(int id) {
        Call<List<MovieEvaluateLoad>> loadEvaluateResponseCall = RetrofitService.getMovieService().loadEvaluate(id, MainActivity.accountInfo.getId());
        loadEvaluateResponseCall.enqueue(new Callback<List<MovieEvaluateLoad>>() {
            @Override
            public void onResponse(Call<List<MovieEvaluateLoad>> call, Response<List<MovieEvaluateLoad>> response) {
                if (response.isSuccessful()) {
                    List<MovieEvaluateLoad> movieEvaluateLoads = response.body();
                    for (MovieEvaluateLoad movieEvaluateLoad : movieEvaluateLoads) {
                        evaluateLoads.add(movieEvaluateLoad);
                    }
                }
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MovieEvaluateLoad>> call, Throwable t) {

            }
        });
    }



    private void StreamMovie() {
        Intent intent = new Intent(this, MovieStreamActivity.class);
        intent.putExtra("movie_stream", movieDetail);
        startActivity(intent);
    }
}