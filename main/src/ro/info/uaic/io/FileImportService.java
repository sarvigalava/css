package ro.info.uaic.io;

import org.springframework.stereotype.Service;
import ro.info.uaic.models.Candidate;

import java.util.List;

/**
 * Created by lotus on 03.05.2015.
 */
@Service
public class FileImportService
{
    public List<Candidate> importCandidates(String fileContent)
    {
        String[] lines = fileContent.split(System.getProperty("line.separator"));

        for(int i = 0; i < lines.length; i++)
        {
            String variables[] = lines[i].split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

            if(variables[0].equals("")) {

            }
        }

        return null;
    }
}
