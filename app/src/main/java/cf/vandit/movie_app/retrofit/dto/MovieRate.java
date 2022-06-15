package cf.vandit.movie_app.retrofit.dto;

public class MovieRate {
    public MovieRate(MovieDetailDTO movieDetailDTO, double rate) {
        this.movieDetailDTO = movieDetailDTO;
        this.rate = rate;
    }

    private MovieDetailDTO movieDetailDTO;
    private double rate;

    public MovieRate() {
    }

    public MovieDetailDTO getMovieDetailDTO() {
        return movieDetailDTO;
    }

    public void setMovieDetailDTO(MovieDetailDTO movieDetailDTO) {
        this.movieDetailDTO = movieDetailDTO;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
