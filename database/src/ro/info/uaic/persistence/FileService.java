package ro.info.uaic.persistence;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;

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
                resizeFromCurrent(file, newSize - oldSize);
            }
            catch (IOException e)
            {
                throw new RuntimeException("Cannot resize storage file. Reason :\n" + e);
            }
        }
    }

    public void resizeFromCurrent(RandomAccessFile file, long bytes) throws IOException
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

    public boolean fileExists(String path)
    {
        Path p = Paths.get(path);
        return Files.exists(p);
    }

    public boolean isDirectory(String path)
    {
        Path p = Paths.get(path);
        return Files.isDirectory(p);
    }

    public void createDirectory(String path)
    {
        new File(path).mkdirs();
    }

    public void createFile(String path)
    {
        try
        {
            Path p = Paths.get(path);
            Files.createFile(p);
        }
        catch (IOException ex)
        {
            handleWriteError(ex);
        }
    }

    public RandomAccessFile openFile(String path, boolean readonly)
    {
        try
        {
            return new RandomAccessFile(path, readonly ? "r" : "rw");
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void deleteRecursively(String path) throws IOException
    {
        Path directory = Paths.get(path);
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

        });
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
