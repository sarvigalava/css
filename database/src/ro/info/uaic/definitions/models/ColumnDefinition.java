package ro.info.uaic.definitions.models;

import java.io.Serializable;

/**
 * Created by lotus on 26.04.2015.
 */
public class ColumnDefinition implements Serializable
{
    private String name;
    private DataType dataType;
    private int size;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public DataType getDataType()
    {
        return dataType;
    }

    public void setDataType(DataType dataType)
    {
        this.dataType = dataType;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    @Override
    public String toString() {
        return name + " " + dataType + "(" + size + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ColumnDefinition that = (ColumnDefinition) o;

        if (size != that.size) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        return dataType == that.dataType;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        result = 31 * result + size;
        return result;
    }
}
