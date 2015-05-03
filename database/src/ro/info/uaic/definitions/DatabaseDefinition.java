package ro.info.uaic.definitions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lotus on 26.04.2015.
 */
public class DatabaseDefinition implements Serializable
{
    private String name;
    private List<TableDefinition> tables = new ArrayList<>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<TableDefinition> getTables()
    {
        return tables;
    }

    public void setTables(List<TableDefinition> tables)
    {
        this.tables = tables;
    }
}
