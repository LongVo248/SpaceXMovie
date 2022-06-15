package cf.vandit.movie_app.retrofit.dto;

public class FKDirector {
    private int movieDetailId;
    private int movieDirectorId;

    public FKDirector() {
    }

    public FKDirector(int movieDetailId, int movieGenreId) {
        this.movieDetailId = movieDetailId;
        this.movieDirectorId = movieGenreId;
    }

    public int getMovieDetailId() {
        return movieDetailId;
    }

    public void setMovieDetailId(int movieDetailId) {
        this.movieDetailId = movieDetailId;
    }

    public int getMovieDirectorId() {
        return movieDirectorId;
    }

    public void setMovieDirectorId(int movieDirectorId) {
        this.movieDirectorId = movieDirectorId;
    }
}
