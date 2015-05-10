package ro.info.uaic.definitions.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lotus on 26.04.2015.
 */
public class TableDefinition implements Serializable
{
    private String name;
    private List<ColumnDefinition> columns = new ArrayList<>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<ColumnDefinition> getColumns()
    {
        return columns;
    }

    public void setColumns(List<ColumnDefinition> columns)
    {
        this.columns = columns;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("\tTable : ");
        sb.append(name);
        sb.append("\t\tColumns : ");
        for (ColumnDefinition cd : columns)
        {
            sb.append(cd);
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TableDefinition that = (TableDefinition) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        return !(columns != null ? !columns.equals(that.columns) : that.columns != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (columns != null ? columns.hashCode() : 0);
        return result;
    }
}
