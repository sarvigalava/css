package ro.info.uaic.webservices;

import org.springframework.stereotype.Service;
import ro.info.uaic.definitions.models.ColumnDefinition;
import ro.info.uaic.definitions.models.DataType;
import ro.info.uaic.definitions.models.TableDefinition;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by lotus on 04.05.2015.
 */
@Service
public class RestServiceArgumentsReader
{
    TableDefinition readTableDefinition(String definition)
    {
        String[] tokens = definition.split("[|]");
        TableDefinition tableDefinition = new TableDefinition();
        for (int i=0; i < tokens.length/2; ++i)
        {
            String column = tokens[i*2];
            String type = tokens[i*2+1];
            ColumnDefinition columnDefinition = new ColumnDefinition();
            columnDefinition.setName(column);
            columnDefinition.setSize(60);
            columnDefinition.setDataType(DataType.valueOf(type));
            tableDefinition.getColumns().add(columnDefinition);
        }

        return tableDefinition;
    }
}
