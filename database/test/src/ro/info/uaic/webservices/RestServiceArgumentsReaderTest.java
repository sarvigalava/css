package ro.info.uaic.webservices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.info.uaic.definitions.models.ColumnDefinition;
import ro.info.uaic.definitions.models.DataType;
import ro.info.uaic.definitions.models.TableDefinition;

import static org.junit.Assert.*;

/**
 * Created by lotus on 10.05.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class RestServiceArgumentsReaderTest
{
    @Autowired private RestServiceArgumentsReader argumentsReader;

    @Test
    public void testReadTableDefinition() throws Exception
    {
        String args = "column1|inTeger |column2|floAt|column3|date|column4|RAW|128|column5|STRING|64";
        TableDefinition tableDefinition = argumentsReader.readTableDefinition(args);

        ColumnDefinition columnDefinition1 = new ColumnDefinition();
        columnDefinition1.setName("column1");
        columnDefinition1.setDataType(DataType.INTEGER);
        columnDefinition1.setSize(8);
        assertEquals(columnDefinition1, tableDefinition.getColumns().get(0));

        ColumnDefinition columnDefinition2 = new ColumnDefinition();
        columnDefinition2.setName("column2");
        columnDefinition2.setDataType(DataType.FLOAT);
        columnDefinition2.setSize(8);
        assertEquals(columnDefinition2, tableDefinition.getColumns().get(1));


        ColumnDefinition columnDefinition3 = new ColumnDefinition();
        columnDefinition3.setName("column3");
        columnDefinition3.setDataType(DataType.DATE);
        columnDefinition3.setSize(8);
        assertEquals(columnDefinition3, tableDefinition.getColumns().get(2));

        ColumnDefinition columnDefinition4 = new ColumnDefinition();
        columnDefinition4.setName("column4");
        columnDefinition4.setDataType(DataType.RAW);
        columnDefinition4.setSize(128);
        assertEquals(columnDefinition4, tableDefinition.getColumns().get(3));

        ColumnDefinition columnDefinition5 = new ColumnDefinition();
        columnDefinition5.setName("column5");
        columnDefinition5.setDataType(DataType.STRING);
        columnDefinition5.setSize(64);
        assertEquals(columnDefinition5, tableDefinition.getColumns().get(4));
    }
}