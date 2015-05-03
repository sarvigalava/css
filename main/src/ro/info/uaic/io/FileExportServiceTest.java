package ro.info.uaic.io;

import org.junit.Test;
import ro.info.uaic.models.Candidate;
import ro.info.uaic.models.Result;
import ro.info.uaic.models.TestType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by proman on 03.05.2015.
 */
public class FileExportServiceTest {

    @Test
    public void testExport() throws Exception {
        FileExportService fileExportService = new FileExportService();

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

        String testResult = fileExportService.export(candidates, results);

        String exceptedResult =
                "id,firstName,lastName,cnp,address,birthDate,testType,testMark,avgBac,bacDomain,bacDomainMark,applicationDate\r\n" +
                "1,\"Ion\"\"\",\"Ma\"\"\"\"randuca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
                "2,\"Io\'\'n\",\"Ma\'\'ra\'\'nduca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
                "3,\"I\non\",\"Mar\nand\nuca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
                "4,\"\rIon\",\"Mar\randu\nca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
                "5,\"I on\",\"Mara nduca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
                "\r\n" +
                "candidateId,admissionMark,status,remarks" +
                "\r\n";

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
}