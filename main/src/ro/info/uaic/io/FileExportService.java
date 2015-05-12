package ro.info.uaic.io;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import ro.info.uaic.models.Candidate;
import ro.info.uaic.models.Result;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by lotus on 03.05.2015.
 */
@Service
public class FileExportService
{
    private static final String CSV_SEPARATOR = ",";
    private static final String CSV_ENDLINE = "\r\n";

    public String export(List<Candidate> candidates)
    {
        StringBuilder output = new StringBuilder();
        Field[] candidateFields = Candidate.class.getDeclaredFields();
        // Generate line with field of candidate
        for(int i = 0; i < candidateFields.length; i++)
        {
            String value = candidateFields[i].getName().toString();
            output.append(checkCsvValueRules(value));
            if(i != candidateFields.length-1)
                output.append(CSV_SEPARATOR);
        }
        output.append(CSV_ENDLINE);

        // Insert each candidate data
        for (Candidate candidate : candidates) {
            BeanWrapper candidateWrapper = new BeanWrapperImpl(candidate);

            for (int i = 0; i < candidateFields.length; i++) {
                String csvValue = "";

                Object value = candidateWrapper.getPropertyValue(candidateFields[i].getName());
                if (value != null) {
                    csvValue = value.toString();
                }

                output.append(checkCsvValueRules(csvValue));
                if (i != candidateFields.length - 1)
                    output.append(CSV_SEPARATOR);
            }

            output.append(CSV_ENDLINE);
        }

        return output.toString();
    }

    public String exportResults(List<Result> results)
    {
        StringBuilder output = new StringBuilder();
        Field[] resultFields = Result.class.getDeclaredFields();

        // Insert blank line
        output.append(CSV_ENDLINE);

        for(int i = 0; i < resultFields.length; i++)
        {
            String value = resultFields[i].getName().toString();
            output.append(checkCsvValueRules(value));
            if(i != resultFields.length-1)
                output.append(CSV_SEPARATOR);
        }
        output.append(CSV_ENDLINE);

        for (Result result : results) {
            BeanWrapper resultWrapper = new BeanWrapperImpl(result);

            for(int i = 0; i < resultFields.length; i++)
            {
                String value = resultWrapper.getPropertyValue(resultFields[i].getName()).toString();
                output.append(checkCsvValueRules(value));
                if(i != resultFields.length-1)
                    output.append(CSV_SEPARATOR);
            }

            output.append(CSV_ENDLINE);
        }

        return output.toString();

    }

    public String export(List<Candidate> candidates, List<Result> results)
    {
        // Return result
        return export(candidates) + exportResults(results);
    }

    private String checkCsvValueRules(String value)
    {
        if(value.contains("\""))
        {
            value = value.replaceAll("\"", "\"\"");
        }

        if(value.contains("\'"))
        {
            value = value.replaceAll("\'", "\'\'");
        }

        if(value.contains("\n") ||
                value.contains("\r") ||
                value.contains("\"") ||
                value.contains("\'") ||
                value.contains(",") ||
                value.contains(" "))
        {
            value = "\"" + value + "\"";
        }

        return value;
    }
}
