package ro.info.uaic.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.info.uaic.DataDefinitionService;
import ro.info.uaic.DatabaseService;
import ro.info.uaic.definitions.models.DatabaseDefinition;
import ro.info.uaic.definitions.models.SchemaDefinition;
import ro.info.uaic.definitions.models.TableDefinition;
import ro.info.uaic.persistence.DataStorageService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("rest/query")
@Component
public class QueryController
{
    @Autowired private DatabaseService databaseService;
    @Autowired private DataStorageService dataStorageService;
    @Autowired private RestServiceArgumentsReader restServiceArgumentsReader;

    @GET
    @Path("schema/{schemaName}/{tableName}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAll(@PathParam("schemaName") String schemaName,
                           @PathParam("tableName") String tableName)
    {

        SchemaDefinition schemaDefinition = getSchemaDefinition(schemaName);
        TableDefinition tableDefinition = getTableDefinition(schemaDefinition, tableName);
        try
        {
            return Response.ok().entity(dataStorageService.getAll(schemaDefinition, tableDefinition)).build();
        }
        catch (IOException ex)
        {
            return Response.serverError().entity(ex).build();
        }
    }

    private SchemaDefinition getSchemaDefinition(String schemaName)
    {
        DatabaseDefinition dd = databaseService.getDatabaseDefinition();
        SchemaDefinition schemaDefinition = dd.getSchemas()
                .stream()
                .filter(s -> s.getName().equalsIgnoreCase(schemaName))
                .findFirst()
                .orElse(null);
        if (schemaDefinition == null)
        {
            throw new RuntimeException("Schema " + schemaName + " does not exist");
        }

        return schemaDefinition;
    }

    private TableDefinition getTableDefinition(SchemaDefinition schemaDefinition, String tableName)
    {
        TableDefinition tableDefinition = schemaDefinition.getTables()
                .stream()
                .filter(s -> s.getName().equalsIgnoreCase(tableName))
                .findFirst()
                .orElse(null);
        if (tableDefinition == null)
        {
            throw new RuntimeException("Table " + schemaDefinition.getName() + "." + tableName +  " does not exist");
        }

        return tableDefinition;
    }

}
