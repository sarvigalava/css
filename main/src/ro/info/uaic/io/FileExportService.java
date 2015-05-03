package ro.info.uaic.io;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import ro.info.uaic.models.Candidate;
import ro.info.uaic.models.Result;

import java.beans.Introspector;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lotus on 03.05.2015.
 */
@Service
public class FileExportService
{
    public String export(List<Candidate> candidates, List<Result> results)
    {
        String CSV_SEPARATOR = ",";
        String CSV_ENDLINE = "\r\n";

        StringBuilder output = new StringBuilder();

        Field[] candidateFields = Candidate.class.getDeclaredFields();
        Field[] resultFields = Result.class.getDeclaredFields();

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

            for(int i = 0; i < candidateFields.length; i++)
            {
                String csvValue = "";

                Object value = candidateWrapper.getPropertyValue(candidateFields[i].getName());
                if(value != null)
                {
                    csvValue = value.toString();
                }

                output.append(checkCsvValueRules(csvValue));
                if(i != candidateFields.length-1)
                    output.append(CSV_SEPARATOR);
            }

            output.append(CSV_ENDLINE);
        }

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

        // Return result
        return output.toString();
    }

    private String checkCsvValueRules(String value)
    {
        if(value.contains("\""))
        {
            value = value.replaceAll("\"", "\"\"");
//            for(int index = 0; index < value.length(); index++)
//            {
//                if(value.charAt(index) == '\"')
//                {
//                    value = value.substring(0, index) + '\"' + value.substring(index, value.length());
//                }
//            }
        }

        if(value.contains("\'"))
        {
            value = value.replaceAll("\'", "\'\'");
//            for(int index = 0; index < value.length(); index++)
//            {
//                if(value.charAt(index) == '\'')
//                {
//                    value = value.substring(0, index) + '\'' + value.substring(index, value.length());
//                }
//            }
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
