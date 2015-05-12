package ro.info.uaic.io;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import ro.info.uaic.models.Candidate;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * Created by lotus on 03.05.2015.
 */
@Service
public class FileImportService
{
    public List<Candidate> importCandidates(String fileContent) throws Exception {
        Field[] candidateFields = Candidate.class.getDeclaredFields();

        String[] lines = fileContent.split("\r\n(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

        for(int i = 0; i < lines.length; i++)
        {
            List<Candidate> candidates;
            String variables[] = lines[i].split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

            if(variables[0].equals("id")) {
                continue;
            }
            else if(variables.length == 11){
                Candidate candidate = new Candidate();
                BeanWrapper candidateWrapper = new BeanWrapperImpl(candidate);

                candidate.setId(Integer.parseInt(variables[0]));
                candidate.setFirstName(variables[1]);
                candidate.setLastName(variables[2]);

                for(int j = 0; j < variables.length; j++) {
                    if(candidateFields[j].getType().getName().equals("int")) {
                        candidateWrapper.setPropertyValue(candidateFields[i].getName(), Integer.parseInt(variables[j]));
                    }
                    else if(candidateFields[j].getType().getName().equals("float")) {
                        candidateWrapper.setPropertyValue(candidateFields[i].getName(), Float.parseFloat(variables[j]));
                    }
                    else if(candidateFields[j].getType().getName() == "Date") {
                        candidateWrapper.setPropertyValue(candidateFields[i].getName(), Date.parse(variables[j]));
                    }
                    else if(candidateFields[j].getType().getName() == "TestType") {
                        candidateWrapper.setPropertyValue(candidateFields[i].getName(), variables[j]);
                    }
                    else {
                        candidateWrapper.setPropertyValue(candidateFields[i].getName(), variables[j]);
                    }
                }
            }
            else {
                throw new Exception("Error in parse line of csv file, Not correct columns in line:" + i);
            }
        }

        return null;
    }
}
