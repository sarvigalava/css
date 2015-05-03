package ro.info.uaic.io;

import org.springframework.stereotype.Service;
import ro.info.uaic.models.Candidate;
import ro.info.uaic.models.Result;

import java.util.List;

/**
 * Created by lotus on 03.05.2015.
 */
@Service
public class FileExportService
{
    public String export(List<Candidate> candidates, List<Result> results)
    {
        return "";
    }
}
