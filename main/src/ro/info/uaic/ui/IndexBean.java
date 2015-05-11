package ro.info.uaic.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ro.info.uaic.models.Candidate;
import ro.info.uaic.models.ResultWrapper;
import ro.info.uaic.persistence.PersistenceService;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lotus on 03.05.2015.
 */
@Component
@Scope("session")
public class IndexBean implements Serializable
{
    @Autowired PersistenceService persistenceService;

    private List<Candidate> candidates;
    private List<ResultWrapper> results;

    @PostConstruct
    public void init()
    {
        candidates = persistenceService.getAllCandidates();
        results = persistenceService.getAllResultsWrapper();
    }

    public List<Candidate> getCandidates()
    {
        return candidates;
    }

    public List<ResultWrapper> getResults()
    {
        return results;
    }
}
