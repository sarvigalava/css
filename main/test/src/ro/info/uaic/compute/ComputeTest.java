package ro.info.uaic.compute;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.info.uaic.models.Candidate;
import ro.info.uaic.models.Result;
import ro.info.uaic.models.Status;
import ro.info.uaic.models.TestType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by proman on 11.05.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class ComputeTest {

    @Autowired private Compute compute;

    @Test
    public void testComputeResults() throws Exception {
        Candidate candidate1 = createCandidate
                (1, "Ion", "Maranduca", "123456789", TestType.INFO, 5.0f, 5.0f, TestType.NONE, 0.0f);
        Candidate candidate2 = createCandidate
                (2, "Ion", "Maranduca", "123456789", TestType.MATH, 6.0f, 6.0f, TestType.INFO, 6.0f);
        Candidate candidate3 = createCandidate
                (3, "Ion", "Maranduca", "123456789", TestType.INFO, 7.0f, 7.0f, TestType.MATH, 7.0f);
        Candidate candidate4 = createCandidate
                (4, "Ion", "Maranduca", "123456789", TestType.MATH, 8.0f, 8.0f, TestType.MATH, 8.0f);
        Candidate candidate5 = createCandidate
                (5, "Ion", "Maranduca", "123456789", TestType.INFO, 9.0f, 9.0f, TestType.INFO, 9.0f);

        List<Candidate> inputCandidates = new ArrayList<>();
        inputCandidates.add(candidate1);
        inputCandidates.add(candidate2);
        inputCandidates.add(candidate3);
        inputCandidates.add(candidate4);
        inputCandidates.add(candidate5);

        List<Result> testResults = new ArrayList<>();

        testResults = compute.computeResults(inputCandidates, 2, 1);

        Result result1 = createResult(1, 5.0f, Status.REJECTED, "");
        Result result2 = createResult(2, 6.0f, Status.REJECTED, "");
        Result result3 = createResult(3, 7.0f, Status.TAX_ONLY, "");
        Result result4 = createResult(4, 8.0f, Status.SPONSORED, "");
        Result result5 = createResult(5, 9.0f, Status.SPONSORED, "");
        List<Result> expectedResults = new ArrayList<>();
        expectedResults.add(result5);
        expectedResults.add(result4);
        expectedResults.add(result3);
        expectedResults.add(result2);
        expectedResults.add(result1);

        Collections.sort(expectedResults, new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                if (o1.getAdmissionMark() < o2.getAdmissionMark()) {
                    return 1;
                } else if (o1.getAdmissionMark() == o2.getAdmissionMark()) {
                    return 0;
                }
                return -1;
            }
        });

        for(int i = 0; i < testResults.size(); i++) {
            assertEquals(expectedResults.get(i).getCandidateId(), testResults.get(i).getCandidateId());
            assertEquals(expectedResults.get(i).getAdmissionMark(), testResults.get(i).getAdmissionMark(), 0.1f);
            assertEquals(expectedResults.get(i).getStatus(), testResults.get(i).getStatus());
        }
    }

    private Candidate createCandidate(
            int id,
            String firstName,
            String lastName,
            String cnp,
            TestType testType,
            float testMark,
            float avgBac,
            TestType bacDomain,
            float bacDomainMark
    )
    {
        Candidate returnCandidate = new Candidate();

        returnCandidate.setId(id);
        returnCandidate.setFirstName(firstName);
        returnCandidate.setLastName(lastName);
        returnCandidate.setCnp(cnp);
        returnCandidate.setTestType(testType);
        returnCandidate.setTestMark(testMark);
        returnCandidate.setAvgBac(avgBac);
        returnCandidate.setBacDomain(bacDomain);
        returnCandidate.setBacDomainMark(bacDomainMark);

        return returnCandidate;
    }

    private Result createResult(
            int candidateId,
            float admissionMark,
            Status status,
            String remarks
    )
    {
        Result returnResult = new Result();

        returnResult.setCandidateId(candidateId);
        returnResult.setAdmissionMark(admissionMark);
        returnResult.setStatus(status);
        returnResult.setRemarks(remarks);

        return returnResult;
    }
}