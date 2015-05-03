package ro.info.uaic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.info.uaic.definitions.models.DatabaseDefinition;
import ro.info.uaic.definitions.models.SchemaDefinition;

/**
 * Created by lotus on 03.05.2015.
 */
@Service
public class DataDefinitionService
{
    @Autowired private DatabaseService databaseService;

    public void createDatabase(String schemaName, String schemaPassword)
    {
        DatabaseDefinition dd = databaseService.getDatabaseDefinition();
        if (dd.getSchemas().stream().anyMatch(s -> s.getName().equalsIgnoreCase(schemaName)))
        {
            throw new RuntimeException("Schema " + schemaName + " exists");
        }

        SchemaDefinition schemaDefinition = new SchemaDefinition();
        schemaDefinition.setName(schemaName);
        schemaDefinition.setPassword(schemaPassword);
        dd.getSchemas().add(schemaDefinition);


    }
}
