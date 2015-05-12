package ro.info.uaic.persistence;

import org.springframework.stereotype.Service;
import ro.info.uaic.models.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by lotus on 11.05.2015.
 */
@Service
public class PersistenceService
{
    private List<Candidate> candidates;
    private List<Result> results;


    @PostConstruct
    public void init()
    {
        candidates = getMockCandidatesList();
    }

    public List<Candidate> getAllCandidates()
    {
        return candidates;
    }


    public List<Result> getAllResults()
    {
        return results;
    }


    private List<Candidate> getMockCandidatesList()
    {
        List<Candidate> candidates = new ArrayList<>();
        Random random = new Random();
        for (int i=0; i<100; ++i)
        {
            Candidate candidate = new Candidate();
            candidate.setId(i);
            candidate.setAddress("Address " + i);
            candidate.setFirstName("First Name " + i);
            candidate.setLastName("Last Name " + i);
            candidate.setCnp("CNP " + i);
            candidate.setBirthDate(new Date());
            candidate.setTestType(TestType.NONE);
            candidate.setTestMark(4 + random.nextFloat() * 6);
            candidate.setAvgBac(4 + random.nextFloat() * 6);
            candidate.setBacDomain(TestType.MATH);
            candidate.setBacDomainMark(4 + random.nextFloat() * 6);
            candidate.setApplicationDate(new Date());
            candidates.add(candidate);
        }

        return candidates;
    }
}
