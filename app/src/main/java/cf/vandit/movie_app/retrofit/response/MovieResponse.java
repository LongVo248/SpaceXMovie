package cf.vandit.movie_app.retrofit.response;

import java.sql.Time;
import java.time.LocalDate;

public class MovieResponse {
    private int id;

    private String title;

    private String poster;

    private String detail;

    private Boolean movieStatus;

    private String linkTrailer;

    private String linkMovie;

    private LocalDate releaseDate;

    private Time movieDuration;

    private int viewNumber;

    public MovieResponse() {
    }

    public MovieResponse(int id, String title, String poster, String detail, Boolean movieStatus, String linkTrailer, String linkMovie, LocalDate releaseDate, Time movieDuration, int viewNumber) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.detail = detail;
        this.movieStatus = movieStatus;
        this.linkTrailer = linkTrailer;
        this.linkMovie = linkMovie;
        this.releaseDate = releaseDate;
        this.movieDuration = movieDuration;
        this.viewNumber = viewNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getMovieStatus() {
        return movieStatus;
    }

    public void setMovieStatus(Boolean movieStatus) {
        this.movieStatus = movieStatus;
    }

    public String getLinkTrailer() {
        return linkTrailer;
    }

    public void setLinkTrailer(String linkTrailer) {
        this.linkTrailer = linkTrailer;
    }

    public String getLinkMovie() {
        return linkMovie;
    }

    public void setLinkMovie(String linkMovie) {
        this.linkMovie = linkMovie;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Time getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(Time movieDuration) {
        this.movieDuration = movieDuration;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }
}
