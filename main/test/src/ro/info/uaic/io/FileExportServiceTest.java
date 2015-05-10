package ro.info.uaic.io;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.info.uaic.io.FileExportService;
import ro.info.uaic.models.Candidate;
import ro.info.uaic.models.Result;
import ro.info.uaic.models.Status;
import ro.info.uaic.models.TestType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by proman on 03.05.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class FileExportServiceTest {

    @Autowired private FileExportService fileExportService;

    @Test
    public void testExport() throws Exception {
        List<Candidate> candidates = new ArrayList<>();
        List<Result> results = new ArrayList<>();

        Candidate candidate1 = createCandidate(1, "Ion\"", "Ma\"\"randuca", "123456789");
        Candidate candidate2 = createCandidate(2, "Io\'n", "Ma\'ra\'nduca", "123456789");
        Candidate candidate3 = createCandidate(3, "I\non", "Mar\nand\nuca", "123456789");
        Candidate candidate4 = createCandidate(4, "\rIon", "Mar\randu\nca", "123456789");
        Candidate candidate5 = createCandidate(5, "I on", "Mara nduca", "123456789");

        candidates.add(candidate1);
        candidates.add(candidate2);
        candidates.add(candidate3);
        candidates.add(candidate4);
        candidates.add(candidate5);

        Result result1 = createResult(1, 5.5f, Status.REJECTED, "Remark1, Remark");
        Result result2 = createResult(2, 7.5f, Status.REJECTED, "Re\"mar\"k2");
        Result result3 = createResult(3, 8.5f, Status.TAX_ONLY, "Re\'ma\'rk3");
        Result result4 = createResult(4, 9.5f, Status.TAX_ONLY, "Re mar k4");
        Result result5 = createResult(5, 10f, Status.SPONSORED, "Remark5");

        results.add(result1);
        results.add(result2);
        results.add(result3);
        results.add(result4);
        results.add(result5);

        String testResult = fileExportService.export(candidates, results);

        String exceptedResult =
                "id,firstName,lastName,cnp,address,birthDate,testType,testMark,avgBac,bacDomain,bacDomainMark,applicationDate\r\n" +
                "1,\"Ion\"\"\",\"Ma\"\"\"\"randuca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
                "2,\"Io\'\'n\",\"Ma\'\'ra\'\'nduca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
                "3,\"I\non\",\"Mar\nand\nuca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
                "4,\"\rIon\",\"Mar\randu\nca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
                "5,\"I on\",\"Mara nduca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
                "\r\n" +
                "candidateId,admissionMark,status,remarks\r\n" +
                "1,5.5,REJECTED,\"Remark1, Remark\"\r\n" +
                "2,7.5,REJECTED,\"Re\"\"mar\"\"k2\"\r\n" +
                "3,8.5,TAX_ONLY,\"Re\'\'ma\'\'rk3\"\r\n" +
                "4,9.5,TAX_ONLY,\"Re mar k4\"\r\n" +
                "5,10.0,SPONSORED,Remark5\r\n";

        assertEquals(exceptedResult, testResult);
    }

    private Candidate createCandidate(
            int id,
            String firstName,
            String lastName,
            String cnp
    )
    {
        Candidate returnCandidate = new Candidate();

        returnCandidate.setId(id);
        returnCandidate.setFirstName(firstName);
        returnCandidate.setLastName(lastName);
        returnCandidate.setCnp(cnp);

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