package ro.info.uaic.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.*;

/**
 * Created by lotus on 03.05.2015.
 */
@Service
public class FileService
{
    public void shiftContent(RandomAccessFile file, long oldSize, long newSize)
    {
        if (oldSize != newSize)
        {
            try
            {
                addBytesToCurrent(file, newSize - oldSize);
            }
            catch (IOException e)
            {
                throw new RuntimeException("Cannot resize storage file. Reason :\n" + e);
            }
        }
    }

    public void addBytesToCurrent(RandomAccessFile file, long bytes) throws IOException
    {
        long initialPosition = file.getFilePointer();
        long position = initialPosition + bytes;
        long fileSize = file.length();

        for (int i=0; i<fileSize; ++i)
        {
            file.seek(position);
            byte b = file.readByte();
            file.seek(position - initialPosition);
            file.writeByte(b);
        }

        file.seek(initialPosition);
    }

    public byte[] readBytes(String filePath)
    {
        Path path = Paths.get(filePath);
        try
        {
            return Files.readAllBytes(path);
        }
        catch (IOException e)
        {
            handleReadError(e);
        }

        return null;
    }

    public void writeBytes(byte[] bytes, String filePath)
    {
        Path path = Paths.get(filePath);
        try
        {
            Files.write(path, bytes, StandardOpenOption.TRUNCATE_EXISTING);
        }
        catch (IOException e)
        {
            handleWriteError(e);
        }
    }

    private void handleReadError(IOException ex)
    {
        throw new RuntimeException("Error reading definitions. Reason : \n" + ex.toString());
    }

    private void handleWriteError(IOException ex)
    {
        throw new RuntimeException("Error writing definitions. Reason : \n" + ex.toString());
    }
}
