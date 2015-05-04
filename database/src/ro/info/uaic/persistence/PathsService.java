package ro.info.uaic.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.info.uaic.definitions.models.SchemaDefinition;
import ro.info.uaic.definitions.models.TableDefinition;
import ro.info.uaic.launcher.Parameters;

import java.io.File;

/**
 * Created by lotus on 04.05.2015.
 */
@Service
public class PathsService
{
    private static final String DEFINITIONS_FILE = "definitions";

    @Autowired private Parameters parameters;

    public String getSchemaDirectory(SchemaDefinition schemaDefinition)
    {
        return parameters.getStorageDirectory() + File.separator + schemaDefinition.getName().toLowerCase();
    }

    public String getTableFile(SchemaDefinition schemaDefinition, TableDefinition tableDefinition)
    {
        return getSchemaDirectory(schemaDefinition) + File.separator + tableDefinition.getName().toLowerCase();
    }


    public String getDefinitionsFilePath(String dir)
    {
        return dir + "/" + DEFINITIONS_FILE;
    }


}
