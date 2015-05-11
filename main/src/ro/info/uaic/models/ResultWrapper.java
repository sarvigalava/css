package ro.info.uaic.models;

import java.io.Serializable;

/**
 * Created by lotus on 11.05.2015.
 */
public class ResultWrapper implements Serializable
{
    private Candidate candidate;
    private Result result;

    public ResultWrapper(Candidate candidate, Result result) {
        this.candidate = candidate;
        this.result = result;
    }

    public ResultWrapper(Candidate candidate) {
        this.candidate = candidate;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
