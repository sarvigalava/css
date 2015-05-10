package ro.info.uaic.compute;

import ro.info.uaic.models.Candidate;
import ro.info.uaic.models.Result;
import ro.info.uaic.models.Status;
import ro.info.uaic.models.TestType;

import java.util.*;

/**
 * Created by proman on 10.05.2015.
 */
public class Compute {
    public List<Result> computeResults(List<Candidate> candidates, int numOfSponsored, int numOfTaxOnly) {
        List<Result> results = new ArrayList<>();
        for(Candidate candidate: candidates) {
            Result result = new Result();
            result.setCandidateId(candidate.getId());
            float partOfTestMark = candidate.getTestMark()*0.5f;
            float partOfBacAvgMark = candidate.getAvgBac()*0.25f;
            float partOfBacDomainMark;
            if(candidate.getBacDomain() == TestType.NONE) {
                partOfBacDomainMark = candidate.getTestMark()*0.25f;
            }
            else {
                partOfBacDomainMark = candidate.getBacDomainMark()*0.25f;
            }
            float admissionMark = partOfTestMark + partOfBacAvgMark + partOfBacDomainMark;
            result.setAdmissionMark(admissionMark);
            results.add(result);
        }

        Collections.sort(results, new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                if(o1.getAdmissionMark() < o2.getAdmissionMark()) {
                    return -1;
                }
                else if(o1.getAdmissionMark() == o2.getAdmissionMark()) {
                    return 0;
                }
                return 1;
            }
        });

        for(int i = 0; i < results.size(); i++) {
            if(i <= numOfSponsored) {
                results.get(i).setStatus(Status.SPONSORED);
            }
            else if((i > numOfSponsored) && (i <= (numOfTaxOnly+numOfSponsored))) {
                results.get(i).setStatus(Status.TAX_ONLY);
            }
            else {
                results.get(i).setStatus(Status.REJECTED);
            }
        }
        return results;
    }
}
