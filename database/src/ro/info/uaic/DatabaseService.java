package ro.info.uaic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.info.uaic.definitions.DatabaseDefinition;
import ro.info.uaic.definitions.reader.DefinitionsReader;

import javax.annotation.PostConstruct;

/**
 * Created by lotus on 26.04.2015.
 */
@Component
public class DatabaseService
{
    @Autowired private DefinitionsReader definitionsReader;

    private DatabaseDefinition databaseDefinition;

    public void init(String storageFile)
    {
        databaseDefinition = definitionsReader.read(storageFile);
    }
}
