package cf.vandit.movie_app.retrofit.dto;

import java.util.Date;

public class MovieEvalute {
    private int userId;
    private int movieId;
    private Date evaluateTime;
    private String evaluateContent;
    private int evaluateRate;

    public MovieEvalute() {
    }

    public MovieEvalute(int userId, int movieId, Date evaluateTime, String evaluateContent, int evaluateRate) {
        this.userId = userId;
        this.movieId = movieId;
        this.evaluateTime = evaluateTime;
        this.evaluateContent = evaluateContent;
        this.evaluateRate = evaluateRate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Date getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(Date evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public int getEvaluateRate() {
        return evaluateRate;
    }

    public void setEvaluateRate(int evaluateRate) {
        this.evaluateRate = evaluateRate;
    }
}
