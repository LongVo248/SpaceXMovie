package cf.vandit.movie_app.retrofit.dto;

import java.io.Serializable;

public class MovieEvaluateResponse implements Serializable {
    private AccountInfo accountInfo;
    private MovieDetailDTO movieDetailDTO;
    private int evaluateRate;
    private long evaluateTime;
    private String evaluateContent;

    public MovieEvaluateResponse() {
    }

    public MovieEvaluateResponse(AccountInfo accountInfo, MovieDetailDTO movieDetailDTO, int evaluateRate, long evaluateTime, String evaluateContent) {
        this.accountInfo = accountInfo;
        this.movieDetailDTO = movieDetailDTO;
        this.evaluateRate = evaluateRate;
        this.evaluateTime = evaluateTime;
        this.evaluateContent = evaluateContent;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public MovieDetailDTO getMovieDetailDTO() {
        return movieDetailDTO;
    }

    public void setMovieDetailDTO(MovieDetailDTO movieDetailDTO) {
        this.movieDetailDTO = movieDetailDTO;
    }

    public int getEvaluateRate() {
        return evaluateRate;
    }

    public void setEvaluateRate(int evaluateRate) {
        this.evaluateRate = evaluateRate;
    }

    public long getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(long evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }
}
