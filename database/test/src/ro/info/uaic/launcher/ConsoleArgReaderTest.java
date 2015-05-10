package ro.info.uaic.launcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by lotus on 10.05.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class ConsoleArgReaderTest
{
    @Autowired private ConsoleArgReader argReader;
    @Autowired private Parameters parameters;

    @Test
    public void testRead() throws Exception
    {
        String[] args = {"-p", "9999", "-d", "/home/user", "-pass", "password"};
        argReader.read(args, parameters);

        assertEquals(parameters.get(Parameter.STORAGE_DIR), "/home/user");
        assertEquals(parameters.getStorageDirectory(), "/home/user");
        assertEquals(parameters.get(Parameter.PORT), "9999");
        assertEquals(parameters.get(Parameter.PASSWORD), "password");
    }
}