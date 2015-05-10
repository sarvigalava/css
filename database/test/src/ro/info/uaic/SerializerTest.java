package ro.info.uaic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.info.uaic.definitions.models.DatabaseDefinition;
import ro.info.uaic.definitions.models.SchemaDefinition;
import ro.info.uaic.definitions.models.TableDefinition;
import ro.info.uaic.launcher.Parameters;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by lotus on 10.05.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class SerializerTest
{
    @Autowired private Serializer serializer;

    @Test
    public void test() throws Exception
    {
        DatabaseDefinition definition = new DatabaseDefinition();
        SchemaDefinition schemaDefinition = new SchemaDefinition();
        schemaDefinition.setName("name");
        schemaDefinition.setPassword("password");
        TableDefinition tableDefinition = new TableDefinition();
        tableDefinition.setName("table");
        schemaDefinition.setTables(Collections.singletonList(tableDefinition));
        definition.setSchemas(Collections.singletonList(schemaDefinition));
        byte[] bytes = serializer.serialize(definition);

        assertNotNull(bytes);
        assertNotEquals(bytes.length, 0);

        DatabaseDefinition definition1 = serializer.deserialize(bytes, DatabaseDefinition.class);
        assertTrue(definition.equals(definition1));

        definition1.getSchemas().get(0).setName("new name");
        assertFalse(definition.equals(definition1));

        definition.getSchemas().get(0).setName("new name");
        assertTrue(definition.equals(definition1));
    }
}