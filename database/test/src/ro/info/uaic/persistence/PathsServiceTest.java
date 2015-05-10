package ro.info.uaic.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.info.uaic.definitions.models.SchemaDefinition;
import ro.info.uaic.definitions.models.TableDefinition;
import ro.info.uaic.launcher.Parameter;
import ro.info.uaic.launcher.Parameters;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by lotus on 10.05.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class PathsServiceTest
{
    @Autowired private PathsService pathsService;
    @Autowired private Parameters parameters;

    @Test
    public void testGetSchemaDirectory() throws Exception
    {
        SchemaDefinition schemaDefinition = new SchemaDefinition();
        schemaDefinition.setName("sCHeMa");
        parameters.add(Parameter.STORAGE_DIR, "dir");
        String dir = pathsService.getSchemaDirectory(schemaDefinition);
        assertEquals(dir, "dir" + File.separator + "schema");
    }

    @Test
    public void testGetTableFile() throws Exception
    {
        SchemaDefinition schemaDefinition = new SchemaDefinition();
        schemaDefinition.setName("sCHeMa");
        TableDefinition tableDefinition = new TableDefinition();
        tableDefinition.setName("tAbLe");
        parameters.add(Parameter.STORAGE_DIR, "dir");
        String dir = pathsService.getTableFile(schemaDefinition, tableDefinition);
        assertEquals(dir, "dir" + File.separator + "schema" + File.separator + "table");
    }

    @Test
    public void testGetDefinitionsFilePath() throws Exception
    {
        String dir = pathsService.getDefinitionsFilePath("dir");
        assertEquals(dir, "dir" + File.separator + "definitions");
    }
}