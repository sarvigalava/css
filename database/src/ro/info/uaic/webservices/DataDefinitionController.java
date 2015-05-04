package ro.info.uaic.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ro.info.uaic.DataDefinitionService;
import ro.info.uaic.DatabaseService;
import ro.info.uaic.definitions.models.TableDefinition;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by lotus on 03.05.2015.
 */
@Path("/rest/data-definition")
@Component
public class DataDefinitionController
{
    @Autowired private DatabaseService databaseService;
    @Autowired private DataDefinitionService dataDefinitionService;
    @Autowired private RestServiceArgumentsReader restServiceArgumentsReader;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDatabaseStructure()
    {
        return databaseService.getDatabaseDefinition().toString();
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("schema/{name}")
    public Response createSchema(@PathParam("name") String name, String password)
    {
        dataDefinitionService.createSchema(name, password);
        return Response.ok().build();
    }

    @POST
    @Path("schema/{schemaName}/{tableName}")
    public Response createTable(@PathParam("schemaName") String schemaName,
                                @PathParam("tableName") String tableName,
                                String tableDefinition)
    {
        TableDefinition definition = restServiceArgumentsReader.readTableDefinition(tableDefinition);
        definition.setName(tableName);
        dataDefinitionService.createTable(schemaName, definition);
        return Response.ok().build();
    }
}
