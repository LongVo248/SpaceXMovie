package cf.vandit.movie_app.retrofit.dto;

import java.io.Serializable;

public class MovieEvaluateLoad implements Serializable {
    private AccountInfo accountDTO;
    private int evaluateRate;
    private long evaluateTime;
    private String evaluateContent;

    public MovieEvaluateLoad() {
    }

    public MovieEvaluateLoad(AccountInfo accountInfo, int evaluateRate, long evaluateTime, String evaluateContent) {
        this.accountDTO = accountInfo;
        this.evaluateRate = evaluateRate;
        this.evaluateTime = evaluateTime;
        this.evaluateContent = evaluateContent;
    }

    public AccountInfo getAccountInfo() {
        return accountDTO;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountDTO = accountInfo;
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
