package ro.info.uaic.persistence;

import org.springframework.stereotype.Service;
import ro.info.uaic.models.*;

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
    public List<Candidate> getAllCandidates()
    {
        return getMockCandidatesList();
    }


    public List<ResultWrapper> getAllResultsWrapper()
    {
        return getMockResultsList();
    }

    public List<Result> getAllResults()
    {
        List<Result> results = new ArrayList<>();
        for (ResultWrapper resultWrapper : getAllResultsWrapper())
        {
            results.add(resultWrapper.getResult());
        }

        return results;
    }

    private List<Candidate> getMockCandidatesList()
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


    private List<ResultWrapper> getMockResultsList()
    {
        List<ResultWrapper> results = new ArrayList<>();
        Random random = new Random();
        for (Candidate candidate : getMockCandidatesList())
        {
            ResultWrapper resultWrapper = new ResultWrapper(candidate);
            Result result = new Result();
            result.setAdmissionMark(random.nextFloat() * 10);
            result.setStatus(Status.SPONSORED);
            result.setRemarks("REMARK");
            resultWrapper.setResult(result);
            result.setCandidateId(candidate.getId());
            results.add(resultWrapper);
        }

        return results;
    }

}
