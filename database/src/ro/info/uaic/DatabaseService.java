package ro.info.uaic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.info.uaic.definitions.models.DatabaseDefinition;
import ro.info.uaic.persistence.DefinitionsStorageService;
import ro.info.uaic.launcher.Parameter;
import ro.info.uaic.launcher.Parameters;

import javax.annotation.PostConstruct;

/**
 * Created by lotus on 26.04.2015.
 */
@Component
public class DatabaseService
{
    @Autowired private DefinitionsStorageService definitionsStorageService;
    @Autowired private Parameters parameters;

    private DatabaseDefinition databaseDefinition;

    public void init()
    {
        databaseDefinition = definitionsStorageService.read(parameters.getStorageDirectory());
    }

    @Override
    public String toString()
    {
        System.out.println("dsds");
        return super.toString();
    }

    public DatabaseDefinition getDatabaseDefinition()
    {
        return databaseDefinition;
    }
}
