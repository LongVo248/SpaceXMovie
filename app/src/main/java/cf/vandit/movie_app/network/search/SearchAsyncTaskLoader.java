package cf.vandit.movie_app.network.search;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.List;

import cf.vandit.movie_app.retrofit.RetrofitService;
import cf.vandit.movie_app.retrofit.dto.MovieDetailDTO;
import retrofit2.Call;

public class SearchAsyncTaskLoader extends AsyncTaskLoader<List<MovieDetailDTO>> {
    private Context mContext;
    private String mQuery;
    public List<MovieDetailDTO> movieDetailDTOS;
    Call<List<MovieDetailDTO>> searchMovie;


    public SearchAsyncTaskLoader(Context context, String query) {
        super(context);
        this.mContext = context;
        this.mQuery = query;
    }

    @Override
    public List<MovieDetailDTO> loadInBackground() {
        mQuery = mQuery.trim();
        mQuery = mQuery.replace(" ", "+");
        searchMovie = RetrofitService.getMovieService().searchMovie(mQuery);
        try {
            return searchMovie.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}