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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lotus on 04.05.2015.
 */
@Service
public class RestServiceArgumentsReader
{
    TableDefinition readTableDefinition(String definition)
    {
        List<String> tokens = Arrays.asList(definition.split("[|]"));
        Iterator<String> iterator = tokens.iterator();
        TableDefinition tableDefinition = new TableDefinition();
        while (iterator.hasNext())
        {
            String column = iterator.next();
            String type = iterator.next();
            ColumnDefinition columnDefinition = new ColumnDefinition();
            columnDefinition.setName(column);
            columnDefinition.setSize(8);
            columnDefinition.setDataType(DataType.parse(type));
            if (columnDefinition.getDataType() == DataType.RAW
                    || columnDefinition.getDataType() == DataType.STRING)
            {
                columnDefinition.setSize(Integer.valueOf(iterator.next()));
            }
            tableDefinition.getColumns().add(columnDefinition);
        }

        return tableDefinition;
    }
}
