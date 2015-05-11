package ro.info.uaic.persistence;

import org.springframework.stereotype.Service;
import ro.info.uaic.models.Candidate;
import ro.info.uaic.models.TestType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lotus on 11.05.2015.
 */
@Service
public class PersistenceService
{
    public List<Candidate> getAllCandidates()
    {
        List<Candidate> candidates = new ArrayList<>();
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
            candidate.setTestMark(i);
            candidate.setAvgBac(i);
            candidate.setBacDomain(TestType.MATH);
            candidate.setBacDomainMark(i);
            candidate.setApplicationDate(new Date());
            candidates.add(candidate);
        }

        return candidates;
    }
}
