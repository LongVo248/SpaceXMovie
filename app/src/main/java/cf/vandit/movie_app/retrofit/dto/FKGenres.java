package cf.vandit.movie_app.retrofit.dto;

public class FKGenres {
    private int movieDetailId;
    private int movieGenreId;

    public FKGenres() {
    }

    public FKGenres(int movieDetailId, int movieGenreId) {
        this.movieDetailId = movieDetailId;
        this.movieGenreId = movieGenreId;
    }

    public int getMovieDetailId() {
        return movieDetailId;
    }

    public void setMovieDetailId(int movieDetailId) {
        this.movieDetailId = movieDetailId;
    }

    public int getMovieGenreId() {
        return movieGenreId;
    }

    public void setMovieGenreId(int movieGenreId) {
        this.movieGenreId = movieGenreId;
    }
}
