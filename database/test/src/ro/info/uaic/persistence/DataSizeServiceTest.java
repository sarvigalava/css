package ro.info.uaic.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.info.uaic.definitions.models.ColumnDefinition;
import ro.info.uaic.definitions.models.DataType;
import ro.info.uaic.definitions.models.TableDefinition;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by lotus on 10.05.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class DataSizeServiceTest
{
    @Autowired private DataSizeService dataSizeService;

    @Test
    public void testGetCellSize() throws Exception
    {
        ColumnDefinition definition = new ColumnDefinition();

        definition.setDataType(DataType.INTEGER);
        assertEquals(dataSizeService.getCellSize(definition), 8);
        definition.setSize(16);
        assertEquals(dataSizeService.getCellSize(definition), 8);

        definition.setDataType(DataType.FLOAT);
        assertEquals(dataSizeService.getCellSize(definition), 8);
        definition.setSize(16);
        assertEquals(dataSizeService.getCellSize(definition), 8);


        definition.setDataType(DataType.DATE);
        assertEquals(dataSizeService.getCellSize(definition), 8);
        definition.setSize(16);
        assertEquals(dataSizeService.getCellSize(definition), 8);

        definition.setDataType(DataType.RAW);
        assertEquals(dataSizeService.getCellSize(definition), 16);
        definition.setSize(128);
        assertEquals(dataSizeService.getCellSize(definition), 128);

        definition.setDataType(DataType.STRING);
        assertEquals(dataSizeService.getCellSize(definition), 256);
        definition.setSize(32);
        assertEquals(dataSizeService.getCellSize(definition), 64);
    }

    @Test
    public void testGetRowSize() throws Exception
    {
        TableDefinition tableDefinition = new TableDefinition();
        ColumnDefinition column1 = new ColumnDefinition();
        column1.setDataType(DataType.INTEGER);
        column1.setSize(5);

        ColumnDefinition column2 = new ColumnDefinition();
        column2.setDataType(DataType.FLOAT);
        column2.setSize(5);

        ColumnDefinition column3 = new ColumnDefinition();
        column3.setDataType(DataType.DATE);
        column3.setSize(5);

        ColumnDefinition column4 = new ColumnDefinition();
        column4.setDataType(DataType.RAW);
        column4.setSize(10);

        ColumnDefinition column5 = new ColumnDefinition();
        column5.setDataType(DataType.STRING);
        column5.setSize(20);

        tableDefinition.setColumns(Arrays.asList(column1, column2, column3, column4, column5));

        assertEquals(74, dataSizeService.getRowSize(tableDefinition));
    }
}