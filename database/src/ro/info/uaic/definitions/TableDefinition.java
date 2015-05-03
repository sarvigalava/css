package ro.info.uaic.definitions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lotus on 26.04.2015.
 */
public class TableDefinition
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
}
