package ro.info.uaic.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ro.info.uaic.models.Candidate;
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

    @PostConstruct
    public void init()
    {
        candidates = persistenceService.getAllCandidates();
    }

    public List<Candidate> getCandidates()
    {
        return candidates;
    }
}
