package ro.info.uaic.io;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.info.uaic.models.Candidate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by proman on 11.05.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class FileImportServiceTest {

    @Autowired private FileImportService fileImportService;

    @Test
    public void testImportCandidates() throws Exception {
        String fileContent =
            "id,firstName,lastName,cnp,address,birthDate,testType,testMark,avgBac,bacDomain,bacDomainMark,applicationDate\r\n" +
            "1,\"Ion\"\"\",\"Ma\"\"\"\"randuca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
            "2,\"Io\'\'n\",\"Ma\'\'ra\'\'nduca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
            "3,\"I\non\",\"Mar\nand\nuca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
            "4,\"\rIon\",\"Mar\randu\nca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
            "5,\"I on\",\"Mara nduca\",123456789,,,,0.0,0.0,,0.0,\r\n" +
            "\r\n";

        List<Candidate> testCandidates = new ArrayList<>();

        testCandidates = fileImportService.importCandidates(fileContent);

        List<Candidate> expectedCandidates = new ArrayList<>();

        Candidate candidate1 = createCandidate(1, "Ion\"", "Ma\"\"randuca", "123456789");
        Candidate candidate2 = createCandidate(2, "Io\'n", "Ma\'ra\'nduca", "123456789");
        Candidate candidate3 = createCandidate(3, "I\non", "Mar\nand\nuca", "123456789");
        Candidate candidate4 = createCandidate(4, "\rIon", "Mar\randu\nca", "123456789");
        Candidate candidate5 = createCandidate(5, "I on", "Mara nduca", "123456789");

        expectedCandidates.add(candidate1);
        expectedCandidates.add(candidate2);
        expectedCandidates.add(candidate3);
        expectedCandidates.add(candidate4);
        expectedCandidates.add(candidate5);

        for(int i = 0; i < testCandidates.size(); i++) {
            assertSame(expectedCandidates.get(i), testCandidates.get(i));
        }
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