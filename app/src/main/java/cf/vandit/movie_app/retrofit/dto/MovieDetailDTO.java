package cf.vandit.movie_app.retrofit.dto;


import java.io.Serializable;
import java.util.List;

public class MovieDetailDTO implements Serializable {
    private int id;
    private String title;
    private String poster;
    private String detail;
    private Boolean movieStatus;
    private String linkTrailer;
    private String linkMovie;
    private List<Integer> releaseDate;
    private String movieDuration;
    private int viewNumber;
    private List<FKCast> fkCasts;
    private List<FKGenres> fkGenres;
    private List<FKDirector> fkDirectors;
    private List<MovieEvalute> movieEvaluates;

    public MovieDetailDTO() {
    }

    public MovieDetailDTO(int id, String title, String poster, String detail, Boolean movieStatus, String linkTrailer, String linkMovie, List<Integer> releaseDate, String movieDuration, int viewNumber) {
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

    public MovieDetailDTO(int id, String title, String poster, String detail, Boolean movieStatus, String linkTrailer, String linkMovie, List<Integer> releaseDate, String movieDuration, int viewNumber, List<FKCast> fkCasts, List<FKGenres> fkGenres, List<FKDirector> fkDirectors, List<MovieEvalute> movieEvaluates) {
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
        this.fkCasts = fkCasts;
        this.fkGenres = fkGenres;
        this.fkDirectors = fkDirectors;
        this.movieEvaluates = movieEvaluates;
    }

    public List<FKCast> getFkCasts() {
        return fkCasts;
    }

    public void setFkCasts(List<FKCast> fkCasts) {
        this.fkCasts = fkCasts;
    }

    public List<FKGenres> getFkGenres() {
        return fkGenres;
    }

    public void setFkGenres(List<FKGenres> fkGenres) {
        this.fkGenres = fkGenres;
    }

    public List<FKDirector> getFkDirectors() {
        return fkDirectors;
    }

    public void setFkDirectors(List<FKDirector> fkDirectors) {
        this.fkDirectors = fkDirectors;
    }

    public List<MovieEvalute> getMovieEvaluates() {
        return movieEvaluates;
    }

    public void setMovieEvaluates(List<MovieEvalute> movieEvaluates) {
        this.movieEvaluates = movieEvaluates;
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

    public List<Integer> getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(List<Integer> releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(String movieDuration) {
        this.movieDuration = movieDuration;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }

    @Override
    public String toString() {
        return "MovieDetailDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", poster='" + poster + '\'' +
                ", detail='" + detail + '\'' +
                ", movieStatus=" + movieStatus +
                ", linkTrailer='" + linkTrailer + '\'' +
                ", linkMovie='" + linkMovie + '\'' +
                ", releaseDate=" + releaseDate +
                ", movieDuration=" + movieDuration +
                ", viewNumber=" + viewNumber +
                '}';
    }
}
