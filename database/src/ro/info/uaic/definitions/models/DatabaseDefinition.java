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
}
