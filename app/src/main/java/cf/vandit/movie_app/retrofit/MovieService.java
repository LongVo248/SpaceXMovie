package cf.vandit.movie_app.retrofit;

import java.util.List;

import cf.vandit.movie_app.retrofit.dto.MovieCastDTO;
import cf.vandit.movie_app.retrofit.dto.MovieDetailDTO;
import cf.vandit.movie_app.retrofit.dto.MovieEvaluateLoad;
import cf.vandit.movie_app.retrofit.dto.MovieEvaluateResponse;
import cf.vandit.movie_app.retrofit.dto.MovieGenreDTO;
import cf.vandit.movie_app.retrofit.dto.MovieRate;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("/api/movieDetail/getMovieDetailAll")
    Call<List<MovieDetailDTO>> getListMovie();

    @GET("/api/movieDetail/getMovieDetail/{id}")
    Call<MovieDetailDTO> getMovieDetailById(@Path("id") int id);

    @GET("/api/movieDetail/getMovieRates")
    Call<List<MovieRate>> getMoveRates();

    @GET("/api/movieDetail/getMovieRate/{id}")
    Call<MovieRate> getMoveRate(@Path("id") int id);

    @GET("/api/movieDetail/getGenreByMovieId/{id}")
    Call<List<MovieGenreDTO>> getGenReByMovie(@Path("id") int id);

    @GET("/api/movieDetail/getCastByMovieId/{id}")
    Call<List<MovieCastDTO>> getCastByMovie(@Path("id") int id);

    @GET("/api/movieDetail/search/{searchKey}")
    Call<List<MovieDetailDTO>> searchMovie(@Path("searchKey") String searchKey);

    @GET("/api/fkCast/movie/{castId}")
    Call<List<MovieDetailDTO>> getMovieByCastId(@Path("castId") int castId);

    @PUT("/api/movieDetail/saveEvaluate")
    Call<MovieDetailDTO> updateEvaluate(@Body MovieEvaluateResponse movieEvaluateResponse, @Query("movieId") int movieId, @Query("accId") int accId);

    @GET("/api/movieDetail/loadEvaluate")
    Call<List<MovieEvaluateLoad>> loadEvaluate(@Query("movieId") int movieId, @Query("accId") int accId);


}
