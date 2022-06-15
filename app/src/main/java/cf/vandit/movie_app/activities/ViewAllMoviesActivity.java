package cf.vandit.movie_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.adapters.MovieBriefSmallAdapter;
import cf.vandit.movie_app.retrofit.RetrofitService;
import cf.vandit.movie_app.retrofit.dto.MovieDetailDTO;
import cf.vandit.movie_app.retrofit.dto.MovieRate;
import cf.vandit.movie_app.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllMoviesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<MovieDetailDTO> mMovies;
    private MovieBriefSmallAdapter mMoviesAdapter;

    private int mMovieType;

    private boolean pagesOver = false;
    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;

    Call<List<MovieRate>> mTopRatedMoviesCall;
    Call<List<MovieDetailDTO>> mMoviesResponseCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_movies);

        Toolbar toolbar = (Toolbar) findViewById(R.id.view_movies_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent receivedIntent = getIntent();
        mMovieType = receivedIntent.getIntExtra(Constants.VIEW_ALL_MOVIES_TYPE, -1);
        System.out.println("\n\n\n\nView: " + mMovieType);
//        if (mMovieType == -1) finish();

        switch (mMovieType) {
            case Constants.POPULAR_MOVIES_TYPE:
                setTitle("Popular Movies");
                break;
            case Constants.TOP_RATED_MOVIES_TYPE:
                setTitle("Top Rated Movies");
                break;
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.view_movies_recView);
        mMovies = new ArrayList<>();
        mMoviesAdapter = new MovieBriefSmallAdapter(mMovies, ViewAllMoviesActivity.this);
        mRecyclerView.setAdapter(mMoviesAdapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewAllMoviesActivity.this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    loadMovies(mMovieType);
                    loading = true;
                }
            }
        });

        loadMovies(mMovieType);
    }

    private void loadMovies(int movieType) {
        if (pagesOver) return;
        switch (movieType) {
            case Constants.TOP_RATED_MOVIES_TYPE:
                mTopRatedMoviesCall = RetrofitService.getMovieService().getMoveRates();
                mTopRatedMoviesCall.enqueue(new Callback<List<MovieRate>>() {
                    @Override
                    public void onResponse(Call<List<MovieRate>> call, Response<List<MovieRate>> response) {
                        if (response.isSuccessful()) {
                            if (response.body() == null) return;
                            for (MovieRate movieRate : response.body()) {
                                mMovies.add(movieRate.getMovieDetailDTO());
                            }
                            mMoviesAdapter.notifyDataSetChanged();
                        }
                        pagesOver = true;
                    }

                    @Override
                    public void onFailure(Call<List<MovieRate>> call, Throwable t) {

                    }
                });
                break;
            default:
                mMoviesResponseCall = RetrofitService.getMovieService().getListMovie();
                mMoviesResponseCall.enqueue(new Callback<List<MovieDetailDTO>>() {
                    @Override
                    public void onResponse(Call<List<MovieDetailDTO>> call, Response<List<MovieDetailDTO>> response) {
                        mMovies.clear();
                        if (response.isSuccessful()) {
                            for (MovieDetailDTO movieDetailDTO : response.body()) {
                                mMovies.add(movieDetailDTO);
                            }
                            mMoviesAdapter.notifyDataSetChanged();
                        }
                        pagesOver = true;
                    }

                    @Override
                    public void onFailure(Call<List<MovieDetailDTO>> call, Throwable t) {

                    }
                });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}