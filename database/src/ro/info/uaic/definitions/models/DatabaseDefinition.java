package ro.info.uaic.definitions.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lotus on 26.04.2015.
 */
public class DatabaseDefinition implements Serializable
{
    private List<SchemaDefinition> schemas = new ArrayList<>();

    public List<SchemaDefinition> getSchemas()
    {
        return schemas;
    }

    public void setSchemas(List<SchemaDefinition> schemas)
    {
        this.schemas = schemas;
    }

    @Override
    public String toString()
    {
        return "DatabaseDefinition{" +
                "schemas=" + schemas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DatabaseDefinition that = (DatabaseDefinition) o;

        return !(schemas != null ? !schemas.equals(that.schemas) : that.schemas != null);

    }

    @Override
    public int hashCode() {
        return schemas != null ? schemas.hashCode() : 0;
    }
}
