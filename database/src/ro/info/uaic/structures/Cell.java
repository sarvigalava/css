package ro.info.uaic.structures;

import ro.info.uaic.definitions.models.ColumnDefinition;
import ro.info.uaic.definitions.models.DataType;

import java.io.Serializable;

/**
 * Created by lotus on 26.04.2015.
 */
public class Cell implements Serializable
{
    private String column;
    private Object data;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
