package ro.info.uaic.launcher;

import com.sun.jersey.api.container.grizzly2.GrizzlyWebContainerFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ro.info.uaic.DatabaseService;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lotus on 26.04.2015.
 */

@Component
public class Launcher
{
    @Autowired private ConsoleArgReader argReader;

    private static final int DEFAULT_PORT = 9998;

    private static URI getBaseURI(Parameters parameters)
    {
        return UriBuilder.fromUri("http://localhost/").port(getPort(parameters)).build();
    }


    public static void main(String[] args) throws IOException
    {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("ro.info.uaic"); // Use annotated beans from the specified package
        Launcher launcher = ctx.getBean(Launcher.class);
        launcher.processArgs(args);
    }

    private static int getPort(Parameters parameters)
    {
        String port = parameters.get(Parameter.PORT);
        if (null != port)
        {
            try
            {
                return Integer.parseInt(port);
            }
            catch (NumberFormatException e)
            {
            }
        }
        return DEFAULT_PORT;
    }

    protected static HttpServer startServer(Parameters parameters) throws IOException
    {
        Map<String, String> initParams = new HashMap<>();
        initParams.put("com.sun.jersey.config.property.packages",
                "ro.info.uaic.webservices");

        System.out.println("Starting grizzly...");
        return GrizzlyWebContainerFactory.create(getBaseURI(parameters), initParams);
    }


    public void processArgs(String[] args)
    {

        Parameters parameters = argReader.read(args);
        if (parameters.get(Parameter.STORAGE_DIR) == null)
        {
            printHelp();
        }
        else
        {
            launch(parameters);
        }
    }

    private static void printHelp()
    {
        String help = "Usage -f [storage-file] {-p [port], -pass [password]}\n";
        System.out.println(help);
    }

    private void launch(Parameters parameters) {
        try {
            HttpServer httpServer = startServer(parameters);
            String msg = "Jersey app started with WADL available at %sapplication.wadl\n" +
                    "Try out %shello\nHit enter to stop it...";
            System.out.println(String.format(msg, getBaseURI(parameters), getBaseURI(parameters)));
            System.in.read();
            httpServer.stop();
        }
        catch (IOException ex) {
            throw new RuntimeException("Error launching Jersey. Reason :\n" + ex.toString());
        }
    }
}
