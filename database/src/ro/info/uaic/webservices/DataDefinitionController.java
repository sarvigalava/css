package ro.info.uaic.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.info.uaic.DataDefinitionService;
import ro.info.uaic.DatabaseService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by lotus on 03.05.2015.
 */
@Path("/data-definition")
@Service
public class DataDefinitionController
{
    @Autowired private DatabaseService databaseService;
    @Autowired private DataDefinitionService dataDefinitionService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("database")
    public String getDatabaseStructure()
    {
        return databaseService.getDatabaseDefinition().toString();
    }

    @POST
    @Path("database/{name}")
    public Response createDatabase(@PathParam("name") String name)
    {

        return null;
    }


    public Response createTable()
    {
        return null; //databaseService.getDatabaseDefinition().toString();
    }
}
