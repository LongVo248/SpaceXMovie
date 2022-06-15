package cf.vandit.movie_app.retrofit.dto;

public class FKCast {
    private int movieDetailId;
    private int movieCastId;

    public FKCast() {
    }

    public FKCast(int movieDetailId, int movieGenreId) {
        this.movieDetailId = movieDetailId;
        this.movieCastId = movieGenreId;
    }

    public int getMovieDetailId() {
        return movieDetailId;
    }

    public void setMovieDetailId(int movieDetailId) {
        this.movieDetailId = movieDetailId;
    }

    public int getMovieCastId() {
        return movieCastId;
    }

    public void setMovieCastId(int movieCastId) {
        this.movieCastId = movieCastId;
    }
}
