package ro.info.uaic.persistence;

import org.springframework.stereotype.Service;
import ro.info.uaic.definitions.models.ColumnDefinition;
import ro.info.uaic.definitions.models.TableDefinition;

/**
 * Created by lotus on 03.05.2015.
 */
@Service
public class DataSizeService
{
    /**
     * Returns cell size in bytes
     * @param column
     * @return
     */
    public int getCellSize(ColumnDefinition column)
    {
        switch (column.getDataType())
        {
            case INTEGER:
            case DATE:
            case FLOAT: return 8;
            case RAW: return column.getSize();
            case STRING: return column.getSize() * 2;

            default: throw new IllegalArgumentException(column.getDataType().toString());
        }
    }

    /**
     * Returns Row Size in Bytes
     * @param td
     * @return
     */
    public long getRowSize(TableDefinition td)
    {
        long total = 0;
        for (ColumnDefinition columnDefinition : td.getColumns())
        {
            total += getCellSize(columnDefinition);
        }

        return total;
    }
}
