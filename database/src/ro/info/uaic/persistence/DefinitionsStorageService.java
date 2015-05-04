package ro.info.uaic.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.info.uaic.Serializer;
import ro.info.uaic.definitions.models.DatabaseDefinition;

import java.io.*;

/**
 *
 * Created by lotus on 26.04.2015.
 */
@Service
public class DefinitionsStorageService
{
    @Autowired Serializer serializer;
    @Autowired FileService fileService;
    @Autowired PathsService pathsService;

    public DatabaseDefinition read(String directory)
    {
        String file = pathsService.getDefinitionsFilePath(directory);
        if (!fileService.fileExists(file))
        {
            return new DatabaseDefinition();
        }
        byte[] definitions = fileService.readBytes(file);

        return deserializeDefinitions(definitions);
    }

    public void store(DatabaseDefinition databaseDefinition, String directory)
    {
        String file = pathsService.getDefinitionsFilePath(directory);
        if (!fileService.fileExists(file))
        {
            fileService.createFile(file);
        }
        byte[] serializedDefinitions = serializeDefinitions(databaseDefinition);
        fileService.writeBytes(serializedDefinitions, file);
    }

    DatabaseDefinition deserializeDefinitions(byte[] bytes)
    {
        try
        {
            return serializer.deserialize(bytes, DatabaseDefinition.class);
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException("Error deserializing definitions. Reason : \n" + e.toString());
        }
    }

    byte[] serializeDefinitions(DatabaseDefinition databaseDefinition)
    {
        try
        {
            return serializer.serialize(databaseDefinition);
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Error serializing definitions. Reason : \n" + ex.toString());
        }
    }
}
