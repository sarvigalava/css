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
        assert file.length() % dataSizeService.getRowSize(tableDefinition) == 0;
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

    public void insert(SchemaDefinition schemaDefinition, TableDefinition tableDefinition, List<Record> records) throws IOException
    {
        String tablePath = pathsService.getTableFile(schemaDefinition, tableDefinition);
        RandomAccessFile file = fileService.openFile(tablePath, true);
        long initialFileLength = file.length();
        long rowSize = dataSizeService.getRowSize(tableDefinition);
        assert initialFileLength % rowSize  == 0;
        assert columnsExists(tableDefinition, records);
        writeRecords(file, tableDefinition, records);
        long fileLength = file.length();
        assert fileLength % rowSize == 0;
        assert fileLength > initialFileLength;
    }

    public void delete(SchemaDefinition schemaDefinition, TableDefinition tableDefinition, Cell cell) throws IOException
    {
        String tablePath = pathsService.getTableFile(schemaDefinition, tableDefinition);
        RandomAccessFile file = fileService.openFile(tablePath, true);
        long initialFileLength = file.length();
        long rowSize = dataSizeService.getRowSize(tableDefinition);
        assert initialFileLength % rowSize  == 0;
        assert columnExists(tableDefinition, cell.getColumn());

        ColumnDefinition columnDefinition = tableDefinition.getColumns()
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(cell.getColumn()))
                .findFirst()
                .orElse(null);

        assert sameType(cell, columnDefinition);
        long columnOffset = dataSizeService.getOffsetTo(tableDefinition, columnDefinition);
        long records = initialFileLength / rowSize;
        assert columnOffset > 0 && columnOffset < rowSize;

        List<Integer> recordsToDelete = new ArrayList<>();
        for (int i = 1; i < records; ++i)
        {
            file.seek(columnOffset * i);
            Cell storedCell = readCell(file, columnDefinition);
            if (cell.getData().equals(storedCell.getData()))
            {
                recordsToDelete.add(i);
            }
        }

        deleteRecords(file, rowSize, recordsToDelete);
    }

    private void deleteRecords(RandomAccessFile file, long rowSize, List<Integer> recordsToDelete) throws IOException
    {
        for (Integer record : recordsToDelete)
        {
            assert record >= 0;
            file.seek(record * rowSize);
            fileService.resizeFromCurrent(file, -rowSize);
        }
    }

    private void writeRecords(RandomAccessFile file, TableDefinition tableDefinition, List<Record> records) throws IOException
    {
        for (Record record : records)
        {
            writeRecord(file, tableDefinition, record);
        }
    }

    private void writeRecord(RandomAccessFile file, TableDefinition tableDefinition, Record record) throws IOException
    {
        for (ColumnDefinition columnDefinition : tableDefinition.getColumns())
        {
            Cell cell = getCellByColumnName(columnDefinition.getName(), record);
            writeCell(file, cell, columnDefinition);
        }
    }

    private void writeCell(RandomAccessFile file, Cell cell, ColumnDefinition columnDefinition) throws IOException
    {
        assert sameType(cell, columnDefinition);
        switch (columnDefinition.getDataType())
        {
            case DATE: file.writeLong(((Date) cell.getData()).getTime()); break;
            case INTEGER: file.writeLong((Long) cell.getData()); break;
            case FLOAT: file.writeDouble((Double) cell.getData()); break;
            case STRING:
                byte[] bytes = new byte[dataSizeService.getCellSize(columnDefinition)];
                byte[] str = ((String) cell.getData()).getBytes("UTF-8");
                System.arraycopy(str, 0, bytes, 0, bytes.length);
                file.write(str); break;
            case RAW:
                byte[] rawBytes = new byte[dataSizeService.getCellSize(columnDefinition)];
                byte[] rawCellData = ((byte[]) cell.getData());
                System.arraycopy(rawCellData, 0, rawBytes, 0, rawBytes.length);
                file.write(rawBytes); break;
        }
    }

    private boolean sameType(Cell cell, ColumnDefinition columnDefinition)
    {
        if (cell != null && cell.getData() != null)
        {
            return DataType.getType(cell.getData()) == columnDefinition.getDataType();
        }

        return true;
    }

    private Cell getCellByColumnName(String column, Record record)
    {
        return record.getCells().stream().filter(c -> c.getColumn().equalsIgnoreCase(column)).findFirst().orElse(null);
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
                    assert cellSize % 2 == 0; //2 bytes per character
                    byte[] buff = new byte[cellSize];
                    assert buff.length == file.read(buff);
                    String str = new String(buff, "UTF-8");
                    assert str.length() == cellSize / 2;
                    cell.setData(str);
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

    private boolean columnExists(TableDefinition tableDefinition, String columnName)
    {
        return tableDefinition.getColumns().stream().anyMatch(c -> c.getName().equalsIgnoreCase(columnName));
    }

    private boolean columnsExists(TableDefinition tableDefinition, List<Record> records)
    {
        return records.stream().flatMap(r -> r.getCells().stream()).allMatch(c -> columnExists(tableDefinition, c.getColumn()));
    }
}
