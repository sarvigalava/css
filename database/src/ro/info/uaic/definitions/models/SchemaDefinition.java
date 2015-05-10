package ro.info.uaic.definitions.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lotus on 03.05.2015.
 */
public class SchemaDefinition implements Serializable
{
    private String name;
    private String password;
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

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Schema : ");
        sb.append(name);
        sb.append("\tTables : ");
        for (TableDefinition td : tables)
        {
            sb.append(td);
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

        SchemaDefinition that = (SchemaDefinition) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (password != null ? !password.equals(that.password) : that.password != null) {
            return false;
        }
        return !(tables != null ? !tables.equals(that.tables) : that.tables != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (tables != null ? tables.hashCode() : 0);
        return result;
    }
}
