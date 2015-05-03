package ro.info.uaic.structures;

import ro.info.uaic.definitions.models.ColumnDefinition;
import ro.info.uaic.definitions.models.DataType;

import java.io.Serializable;

/**
 * Created by lotus on 26.04.2015.
 */
public class Cell implements Serializable
{
    private ColumnDefinition columnDefinition;
    private Object data;

    public ColumnDefinition getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(ColumnDefinition columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
