package cf.vandit.movie_app.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.adapters.MovieCastsOfPersonAdapter;
import cf.vandit.movie_app.retrofit.RetrofitService;
import cf.vandit.movie_app.retrofit.dto.MovieCastDTO;
import cf.vandit.movie_app.retrofit.dto.MovieDetailDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CastActivity extends AppCompatActivity {
    private MovieCastDTO mPersonId;

    private ImageButton cast_backBtn;

    private AppBarLayout cast_appbar;
    private Toolbar cast_toolbar;
    private CollapsingToolbarLayout cast_collapsingToolbar;

    private ImageView cast_imageView;
    private AVLoadingIndicatorView cast_progress_bar;
    private TextView cast_name;
    private TextView cast_age_heading;
    private TextView cast_age;

    private TextView cast_bio_heading;
    private TextView cast_bio;
    private TextView cast_read_more;
    private TextView cast_movie_heading;
    private RecyclerView cast_movie;

    private Call<List<MovieDetailDTO>> mMovieCasts;

    private List<MovieDetailDTO> mMovieCastOfPersons;
    private MovieCastsOfPersonAdapter mMovieCastsOfPersonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast);

        Intent receivedIntent = getIntent();
        mPersonId = (MovieCastDTO) receivedIntent.getSerializableExtra("person_cast");


        cast_backBtn = findViewById(R.id.cast_back_btn);

        cast_appbar = findViewById(R.id.cast_appBar);
        cast_toolbar = findViewById(R.id.cast_toolbar);
        cast_collapsingToolbar = findViewById(R.id.cast_collapsingToolbar);

        cast_imageView = findViewById(R.id.cast_imageView);
        cast_progress_bar = findViewById(R.id.cast_progress_bar);
        cast_name = findViewById(R.id.cast_name);
        cast_age_heading = findViewById(R.id.cast_age_heading);
        cast_age = findViewById(R.id.cast_age);

        cast_bio_heading = findViewById(R.id.cast_bio_heading);
        cast_bio = findViewById(R.id.cast_bio);

        cast_movie_heading = findViewById(R.id.cast_movie_heading);
        cast_movie = findViewById(R.id.cast_movie);
        cast_read_more = findViewById(R.id.cast_read_more);
        mMovieCastOfPersons = new ArrayList<>();
        mMovieCastsOfPersonAdapter = new MovieCastsOfPersonAdapter(CastActivity.this, mMovieCastOfPersons);
        cast_movie.setAdapter(mMovieCastsOfPersonAdapter);
        cast_movie.setLayoutManager(new LinearLayoutManager(CastActivity.this, LinearLayoutManager.HORIZONTAL, false));

        setSupportActionBar(cast_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadActivity();

        cast_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadActivity() {
        cast_appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (appBarLayout.getTotalScrollRange() + verticalOffset == 0) {
                if (mPersonId.getName() != null)
                    cast_collapsingToolbar.setTitle(mPersonId.getName());
                else
                    cast_collapsingToolbar.setTitle("");
                cast_toolbar.setVisibility(View.VISIBLE);
            } else {
                cast_collapsingToolbar.setTitle("");
                cast_toolbar.setVisibility(View.INVISIBLE);
            }
        });

        Glide.with(getApplicationContext()).load(mPersonId.getAvatar())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        cast_progress_bar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        cast_progress_bar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(cast_imageView);

        if (mPersonId.getName() != null)
            cast_name.setText(mPersonId.getName());
        else
            cast_name.setText("");

//        if (mPersonId.getPlaceOfBirth() != null && !response.body().getPlaceOfBirth().trim().isEmpty())
//            cast_birthplace.setText(response.body().getPlaceOfBirth());

        if (mPersonId.getStory() != null && !mPersonId.getStory().trim().isEmpty()) {
            cast_bio_heading.setVisibility(View.VISIBLE);
            cast_bio.setText(mPersonId.getStory());

            if (cast_bio.getLineCount() == 7) {
                cast_read_more.setVisibility(View.VISIBLE);
            } else {
                cast_read_more.setVisibility(View.GONE);
            }

            cast_read_more.setOnClickListener(view -> {
                if (cast_read_more.getText() == "Read more") {
                    cast_bio.setMaxLines(Integer.MAX_VALUE);
                    cast_read_more.setText("Read less");
                } else {
                    cast_bio.setMaxLines(7);
                    cast_read_more.setText("Read more");
                }
            });
        }

        setAge(mPersonId.getBirthday());
        setMovieCast(mPersonId.getId());
    }

    private void setAge(List<Integer> dateOfBirthString) {
        String birthday = new StringBuilder()
                .append(dateOfBirthString.get(2))
                .append("-")
                .append(dateOfBirthString.get(1))
                .append("-")
                .append(dateOfBirthString.get(0)).toString();
        cast_age.setText(birthday);
    }

    private void setMovieCast(int castId) {
        mMovieCasts = RetrofitService.getMovieService().getMovieByCastId(castId);
        mMovieCasts.enqueue(new Callback<List<MovieDetailDTO>>() {
            @Override
            public void onResponse(Call<List<MovieDetailDTO>> call, Response<List<MovieDetailDTO>> response) {
                if (response.body() == null) return;

                for (MovieDetailDTO movieCastOfPerson : response.body()) {
                    if (movieCastOfPerson == null) return;
                    if (movieCastOfPerson.getTitle() != null && movieCastOfPerson.getPoster() != null) {
                        cast_movie_heading.setVisibility(View.VISIBLE);
                        mMovieCastOfPersons.add(movieCastOfPerson);
                    }
                }
                mMovieCastsOfPersonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MovieDetailDTO>> call, Throwable t) {

            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}