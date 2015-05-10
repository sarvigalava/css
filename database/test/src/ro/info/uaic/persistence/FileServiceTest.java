package ro.info.uaic.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by lotus on 10.05.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class FileServiceTest
{
    @Autowired private FileService fileService;
    private static final String TEST_DIR = "test.dir";
    private static final String TEST_FILE = TEST_DIR + File.separator + "test.bin";
    private static final byte[] BYTES = {110, 115, 120, 125, 58, 99, 79};;

    @Before
    public void init()
    {

        new File(TEST_DIR).mkdir();
        createFile(TEST_FILE, BYTES);
    }

    @After
    public void cleanup() throws IOException
    {
        fileService.deleteRecursively(TEST_DIR);
    }


    @Test
    public void testReadBytes() throws Exception
    {
        byte[] bytes = fileService.readBytes(TEST_FILE);
        assertArrayEquals(BYTES, bytes);
    }

    @Test
    public void testWriteBytes() throws Exception
    {
        byte[] bytes = {1, 23, 45, 22, 67, 11};
        fileService.writeBytes(bytes, TEST_FILE);

        byte[] read = fileService.readBytes(TEST_FILE);
        assertArrayEquals(bytes, read);
    }

    @Test
    public void testFileExists() throws Exception
    {
        boolean exists = fileService.fileExists(TEST_FILE);
        assertTrue(exists);

        exists = fileService.fileExists(TEST_FILE + "unique");
        assertFalse(exists);
    }

    @Test
    public void testCreateDirectory() throws Exception
    {
        String path = getPath("directory");
        assertFalse(fileService.fileExists(path));
        fileService.createDirectory(path);
        assertTrue(fileService.fileExists(path));
        assertTrue(fileService.isDirectory(path));
    }

    @Test
    public void testDeleteRecursively() throws Exception
    {
        String dir1 = getPath("directory1");
        String dir2 = dir1 + File.separator + "directory2";
        assertFalse(fileService.fileExists(dir1));
        assertFalse(fileService.fileExists(dir2));
        fileService.createDirectory(dir2);
        assertTrue(fileService.fileExists(dir2));
        assertTrue(fileService.isDirectory(dir2));

        fileService.deleteRecursively(dir1);
        assertFalse(fileService.fileExists(dir1));
        assertFalse(fileService.fileExists(dir2));
    }

    @Test
    public void testIsDirectory() throws Exception
    {
        assertTrue(fileService.isDirectory(TEST_DIR));
        assertFalse(fileService.isDirectory(TEST_FILE));
        assertFalse(fileService.isDirectory(TEST_DIR + "unique"));
    }

    @Test
    public void testCreateFile() throws Exception
    {
        String path = getPath("file");
        assertFalse(fileService.fileExists(path));
        fileService.createFile(path);
        assertTrue(fileService.fileExists(path));
        assertFalse(fileService.isDirectory(path));
    }

    @Test
    public void testOpenFile() throws Exception
    {
        RandomAccessFile file = fileService.openFile(TEST_FILE, true);
        try
        {
            file.write(45);
            fail("Not readonly");

        }
        catch (Exception e)
        {
            //ok
            file.close();
        }

        file = fileService.openFile(TEST_FILE, false);
        file.write(45);
        file.close();
    }

    private void createFile(String name, byte[] bytes)
    {
        try
        {
            File file = new File(name);
            file.createNewFile();
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.close();
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private String getPath(String filename)
    {
        return TEST_DIR + File.separator + filename;
    }
}