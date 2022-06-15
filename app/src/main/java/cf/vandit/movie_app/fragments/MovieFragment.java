package cf.vandit.movie_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.ViewAllMoviesActivity;
import cf.vandit.movie_app.adapters.MovieBriefSmallAdapter;
import cf.vandit.movie_app.adapters.MovieCarouselAdapter;
import cf.vandit.movie_app.adapters.MoviesNestedRecViewAdapter;
import cf.vandit.movie_app.retrofit.RetrofitService;
import cf.vandit.movie_app.retrofit.dto.MovieDetailDTO;
import cf.vandit.movie_app.retrofit.dto.MovieRate;
import cf.vandit.movie_app.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private ProgressBar mProgressBar;

    private TextView mViewTopRated;

    private Timer timer;
    private TimerTask timerTask;
    private int position;
    private LinearLayoutManager carouselLayoutManager;

    private RecyclerView mNowShowingRecyclerView;
    private List<MovieDetailDTO> mNowShowingMovies;
    private MovieCarouselAdapter mNowShowingAdapter;


    private RecyclerView mTopRatedRecyclerView;
    private List<MovieDetailDTO> mTopRatedMovies;
    private MovieBriefSmallAdapter mTopRatedAdapter;


    private RecyclerView mNestedRecView;
    private List<MovieDetailDTO> mNestedList;
    private MoviesNestedRecViewAdapter mMoviesNestedRecViewAdapter;

    private ConstraintLayout mTopRatedHeading;

    private boolean mNowShowingMoviesLoaded;
    private boolean mTopRatedMoviesLoaded;

    Call<List<MovieDetailDTO>> mNowShowingMoviesCall;
    Call<List<MovieRate>> mTopRatedMoviesCall;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar = view.findViewById(R.id.movie_progressBar);

        mViewTopRated = view.findViewById(R.id.view_top_rated);

        mNowShowingRecyclerView = view.findViewById(R.id.carousel_recView);

        mTopRatedRecyclerView = view.findViewById(R.id.top_rated_recView);

        mTopRatedHeading = view.findViewById(R.id.top_rated_heading);

        mNestedRecView = view.findViewById(R.id.movie_nested_recView);

        mNowShowingMovies = new ArrayList<>();
        mTopRatedMovies = new ArrayList<>();

        mNestedList = new ArrayList<>();

        mNowShowingMoviesLoaded = false;
        mTopRatedMoviesLoaded = false;

        mNowShowingAdapter = new MovieCarouselAdapter(mNowShowingMovies, getContext());
        carouselLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mNowShowingRecyclerView.setLayoutManager(carouselLayoutManager);
        mNowShowingRecyclerView.setAdapter(mNowShowingAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mNowShowingRecyclerView);
        mNowShowingRecyclerView.smoothScrollBy(5, 0);

        mTopRatedAdapter = new MovieBriefSmallAdapter(mTopRatedMovies, getContext());
        mTopRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mTopRatedRecyclerView.setAdapter(mTopRatedAdapter);

        mMoviesNestedRecViewAdapter = new MoviesNestedRecViewAdapter(mNestedList, getContext());
        mNestedRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNestedRecView.setAdapter(mMoviesNestedRecViewAdapter);

        mNowShowingRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == 1) {
                    stopAutoScrollCarousel();
                } else if (newState == 0) {
                    position = carouselLayoutManager.findFirstCompletelyVisibleItemPosition();
                    runAutoScrollingCarousel();
                }
            }
        });


        mViewTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllMoviesActivity.class);
                intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.TOP_RATED_MOVIES_TYPE);
                startActivity(intent);
            }
        });

        initViews();
    }

    private void stopAutoScrollCarousel() {
        if (timer != null && timerTask != null) {
            timerTask.cancel();
            timer.cancel();
            timer = null;
            timerTask = null;
            position = carouselLayoutManager.findFirstCompletelyVisibleItemPosition();
        }
    }

    private void runAutoScrollingCarousel() {
        if (timer == null && timerTask == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (position == mNowShowingMovies.size() - 1) {
                        mNowShowingRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                position = 0;
                                mNowShowingRecyclerView.smoothScrollToPosition(position);
                                mNowShowingRecyclerView.smoothScrollBy(5, 0);
                            }
                        });
                    } else {
                        position++;
                        mNowShowingRecyclerView.smoothScrollToPosition(position);
                    }
                }
            };
            timer.schedule(timerTask, 4000, 4000);
        }
    }

    private void initViews() {
        loadNowShowingMovies();
        loadTopRatedMovies();
    }

    private void loadNowShowingMovies() {
        mNowShowingMoviesCall = RetrofitService.getMovieService().getListMovie();
        mNowShowingMoviesCall.enqueue(new Callback<List<MovieDetailDTO>>() {
            @Override
            public void onResponse(Call<List<MovieDetailDTO>> call, Response<List<MovieDetailDTO>> response) {
                if (!response.isSuccessful()) {
                    mNowShowingMoviesCall = call.clone();
                    mNowShowingMoviesCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;

                for (MovieDetailDTO movieDetailDTO : response.body()) {
                    if (movieDetailDTO != null && movieDetailDTO.getPoster() != null)
                        mNowShowingMovies.add(movieDetailDTO);
                }
                mNowShowingAdapter.notifyDataSetChanged();
                mNowShowingMoviesLoaded = true;
                checkAllDataLoaded();
            }

            @Override
            public void onFailure(Call<List<MovieDetailDTO>> call, Throwable t) {

            }
        });
    }
    private void loadTopRatedMovies() {
        mTopRatedMoviesCall = RetrofitService.getMovieService().getMoveRates();
        mTopRatedMoviesCall.enqueue(new Callback<List<MovieRate>>() {
            @Override
            public void onResponse(Call<List<MovieRate>> call, Response<List<MovieRate>> response) {
                if (response.isSuccessful()) {
                    for (MovieRate movieRate : response.body()) {
                        mTopRatedMovies.add(movieRate.getMovieDetailDTO());
                    }
                    mTopRatedAdapter.notifyDataSetChanged();
                    mTopRatedMoviesLoaded = true;
                    checkAllDataLoaded();
                }
            }

            @Override
            public void onFailure(Call<List<MovieRate>> call, Throwable t) {
                System.out.println("\n\n\n\n throw:   "+ t);
            }
        });
    }

    private void checkAllDataLoaded() {
        if (mNowShowingMoviesLoaded && mTopRatedMoviesLoaded) {
            mProgressBar.setVisibility(View.GONE);
            mNowShowingRecyclerView.setVisibility(View.VISIBLE);
            mTopRatedHeading.setVisibility(View.VISIBLE);
            mTopRatedRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScrollCarousel();
    }

    @Override
    public void onResume() {
        super.onResume();
        runAutoScrollingCarousel();
    }
}