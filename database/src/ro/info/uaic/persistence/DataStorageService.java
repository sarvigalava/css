package ro.info.uaic.persistence;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.info.uaic.Serializer;
import ro.info.uaic.definitions.models.ColumnDefinition;
import ro.info.uaic.definitions.models.DataType;
import ro.info.uaic.definitions.models.SchemaDefinition;
import ro.info.uaic.definitions.models.TableDefinition;
import ro.info.uaic.structures.Cell;
import ro.info.uaic.structures.Record;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lotus on 03.05.2015.
 */
@Service
public class DataStorageService
{
    @Autowired private Serializer serializer;
    @Autowired private FileService fileService;
    @Autowired private PathsService pathsService;
    @Autowired private DataSizeService dataSizeService;

    public List<Record> getAll(SchemaDefinition schemaDefinition, TableDefinition tableDefinition) throws IOException
    {
        List<Record> records = new ArrayList<>();
        String tablePath = pathsService.getTableFile(schemaDefinition, tableDefinition);
        RandomAccessFile file = fileService.openFile(tablePath, true);
        while (true)
        {
            try
            {
                records.add(fromBytes(tableDefinition, file));
            }
            catch (EOFException ex)
            {
                file.close();
                break;
            }
        }

        return records;
    }

    private Record fromBytes(TableDefinition tableDefinition, RandomAccessFile file) throws EOFException
    {
        Record record = new Record();
        for (ColumnDefinition columnDefinition : tableDefinition.getColumns())
        {
            Cell cell = readCell(file, columnDefinition);
            record.getCells().add(cell);
        }

        return record;
    }

    private Cell readCell(RandomAccessFile file, ColumnDefinition columnDefinition) throws EOFException
    {
        int cellSize = dataSizeService.getCellSize(columnDefinition);
        assert cellSize > 0;
        Cell cell = new Cell();

        assert !Strings.isNullOrEmpty(columnDefinition.getName());
        cell.setColumn(columnDefinition.getName());
        try
        {
            switch (columnDefinition.getDataType())
            {
                case DATE: cell.setData(new Date(file.readLong())); break;
                case STRING:
                    byte[] buff = new byte[cellSize];
                    assert buff.length == file.read(buff);
                    cell.setData(new String(buff));
                    break;
                case INTEGER: cell.setData(file.readLong()); break;
                case RAW:
                    buff = new byte[cellSize];
                    assert buff.length == file.read(buff);
                    cell.setData(buff);
                    break;
                case FLOAT: cell.setData(file.readDouble()); break;

                default: throw new IllegalArgumentException(columnDefinition.getDataType().toString());
            }
        }
        catch (EOFException eof)
        {
            throw eof;
        }
        catch (IOException ioe)
        {
            throw new RuntimeException(ioe);
        }

        return cell;
    }
}
