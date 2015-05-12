package ro.info.uaic.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ro.info.uaic.compute.Compute;
import ro.info.uaic.models.Candidate;
import ro.info.uaic.models.Result;
import ro.info.uaic.models.ResultWrapper;
import ro.info.uaic.persistence.PersistenceService;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lotus on 03.05.2015.
 */
@Component
@Scope("session")
public class IndexBean implements Serializable
{
    @Autowired PersistenceService persistenceService;
    @Autowired Compute compute;

    private List<Candidate> candidates;
    private List<Result> results;
    private boolean calculated;
    private int nrOfSponsored;
    private int nrOfTaxOnly;

    @PostConstruct
    public void init()
    {
        candidates = persistenceService.getAllCandidates();
    }

    public List<Candidate> getCandidates()
    {
        return candidates;
    }

    public List<Result> getResults()
    {
        return results;
    }

    public List<ResultWrapper> getResultsWrappers()
    {
        List<ResultWrapper> resultsWrapper = new ArrayList<>();
        for (Candidate candidate : candidates)
        {
            ResultWrapper wrapper = new ResultWrapper(candidate);
            wrapper.setResult(getResult(candidate.getId()));
            resultsWrapper.add(wrapper);
        }

        return resultsWrapper;
    }

    public void calculateResults(ActionEvent event)
    {
        results = compute.computeResults(candidates, nrOfSponsored, nrOfTaxOnly);
        calculated = true;
    }

    public boolean isCalculated()
    {
        return calculated;
    }

    public void setCalculated(boolean calculated)
    {
        this.calculated = calculated;
    }


    private Result getResult(int candidateId)
    {
        for (Result result : results)
        {
            if (result.getCandidateId() == candidateId)
            {
                return result;
            }
        }

        return null;
    }

    public int getNrOfSponsored()
    {
        return nrOfSponsored;
    }

    public void setNrOfSponsored(int nrOfSponsored) {
        this.nrOfSponsored = nrOfSponsored;
    }

    public int getNrOfTaxOnly() {
        return nrOfTaxOnly;
    }

    public void setNrOfTaxOnly(int nrOfTaxOnly) {
        this.nrOfTaxOnly = nrOfTaxOnly;
    }
}
