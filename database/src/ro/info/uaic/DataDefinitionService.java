package ro.info.uaic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.info.uaic.definitions.models.DatabaseDefinition;
import ro.info.uaic.definitions.models.SchemaDefinition;
import ro.info.uaic.definitions.models.TableDefinition;
import ro.info.uaic.launcher.Parameters;
import ro.info.uaic.persistence.DefinitionsStorageService;
import ro.info.uaic.persistence.FileService;
import ro.info.uaic.persistence.PathsService;

import java.util.Optional;

/**
 * Created by lotus on 03.05.2015.
 */
@Service
public class DataDefinitionService
{
    @Autowired private DatabaseService databaseService;
    @Autowired private DefinitionsStorageService definitionsStorageService;
    @Autowired private PathsService pathsService;
    @Autowired private Parameters parameters;
    @Autowired private FileService fileService;

    public void createSchema(String schemaName, String schemaPassword)
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
        definitionsStorageService.store(dd, parameters.getStorageDirectory());
        fileService.createDirectory(pathsService.getSchemaDirectory(schemaDefinition));
    }

    public void createTable(String schemaName, TableDefinition tableDefinition)
    {
        DatabaseDefinition dd = databaseService.getDatabaseDefinition();
        SchemaDefinition schemaDefinition = getSchemaDefinition(schemaName);
        if (schemaDefinition.getTables().stream()
                .anyMatch(t -> t.getName().equalsIgnoreCase(tableDefinition.getName())))
        {
            throw new RuntimeException("Table " + tableDefinition.getName() + " exists");
        }

        schemaDefinition.getTables().add(tableDefinition);
        definitionsStorageService.store(dd, parameters.getStorageDirectory());
        fileService.createFile(pathsService.getTableFile(schemaDefinition, tableDefinition));
    }

    private SchemaDefinition getSchemaDefinition(String schemaName)
    {
        DatabaseDefinition dd = databaseService.getDatabaseDefinition();
        SchemaDefinition schemaDefinition = dd.getSchemas()
                .stream()
                .filter(s -> s.getName().equalsIgnoreCase(schemaName))
                .findFirst()
                .orElse(null);
        if (schemaDefinition == null)
        {
            throw new RuntimeException("Schema " + schemaName + " does not exist");
        }

        return schemaDefinition;
    }
}
