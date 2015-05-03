package ro.info.uaic.definitions.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.ObjectInstantiator;
import org.springframework.stereotype.Service;
import ro.info.uaic.Serializer;
import ro.info.uaic.definitions.DatabaseDefinition;

import java.io.*;

/**
 *
 * File structure
 *
 * 1. 4 bytes definitions byte length
 * 2. definitions
 * 3. data
 *
 * Created by lotus on 26.04.2015.
 */
@Service
public class DefinitionsReader
{
    @Autowired Serializer serializer;

    public DatabaseDefinition read(String filePath)
    {
        RandomAccessFile file = openFile(filePath);

        if (file != null)
        {
            int definitionsLength = readDefinitionsLength(file);
            byte[] definitions = readBytes(definitionsLength, file);
            return deserializeDefinitions(definitions);
        }
        else
        {
            return new DatabaseDefinition();
        }
    }

    private RandomAccessFile openFile(String filePath)
    {
        try
        {
            return new RandomAccessFile(filePath, "r");
        }
        catch (FileNotFoundException ex)
        {
            return null;
        }
        catch (Exception ex)
        {
            String err = "Error opening storage file : %s. Reason :\n";
            throw new RuntimeException(String.format(err, filePath, ex.toString()));
        }
    }

    private int readDefinitionsLength(RandomAccessFile file)
    {
        int length = 0;
        try
        {
            length = file.readInt();
        }
        catch (IOException ex)
        {
            handleReadError(ex);
        }


        if (length < 0)
        {
            throw new RuntimeException("Corrupted storage file. Definitions length is a negative integer");
        }

        return length;
    }

    private byte[] readBytes(int bytes, RandomAccessFile file)
    {
        byte[] byteArray = new byte[bytes];
        try
        {
            int read = file.read(byteArray);
            assert read == bytes;
        }
        catch (IOException ex)
        {
            handleReadError(ex);
        }

        return byteArray;
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

    private void handleReadError(IOException ex)
    {
        throw new RuntimeException("Error reading definitions. Reason : \n" + ex.toString());
    }
}
