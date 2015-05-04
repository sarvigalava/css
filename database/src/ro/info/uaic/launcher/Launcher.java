package ro.info.uaic.launcher;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.container.grizzly2.GrizzlyWebContainerFactory;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.spi.spring.container.SpringComponentProviderFactory;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ro.info.uaic.DatabaseService;

import javax.servlet.ServletRegistration;
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
    @Autowired private Parameters parameters;
    @Autowired private DatabaseService databaseService;

    private static final int DEFAULT_PORT = 9998;

    private static URI getBaseURI(Parameters parameters)
    {
        return UriBuilder.fromUri("http://localhost/").port(getPort(parameters)).build();
    }


    public static void main(String[] args) throws IOException
    {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml"); // Use annotated beans from the specified package
        Launcher launcher = ctx.getBean(Launcher.class);
        launcher.processArgs(args, ctx);
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

    protected static HttpServer startServer(Parameters parameters, ConfigurableApplicationContext ctx) throws IOException
    {
        ResourceConfig rc = new DefaultResourceConfig();
        rc.getProperties().put("com.sun.jersey.config.property.packages", "ro.info.uaic.webservices");
        IoCComponentProviderFactory factory = new SpringComponentProviderFactory(rc, ctx);
        return GrizzlyServerFactory.createHttpServer(getBaseURI(parameters), rc, factory);
    }


    public void processArgs(String[] args, ConfigurableApplicationContext ctx)
    {

        argReader.read(args, parameters);
        if (parameters.get(Parameter.STORAGE_DIR) == null)
        {
            printHelp();
        }
        else
        {
            launch(parameters, ctx);
        }
    }

    private static void printHelp()
    {
        String help = "Usage -f [storage-file] {-p [port], -pass [password]}\n";
        System.out.println(help);
    }

    private void launch(Parameters parameters, ConfigurableApplicationContext ctx) {
        databaseService.init();
        try {
            HttpServer httpServer = startServer(parameters, ctx);
            String msg = "Jersey app started with WADL available at %sapplication.wadl\n" +
                    "Try out %sdata-definition\nHit enter to stop it...";
            System.out.println(String.format(msg, getBaseURI(parameters), getBaseURI(parameters)));
            System.in.read();
            httpServer.stop();
        }
        catch (IOException ex) {
            throw new RuntimeException("Error launching Jersey. Reason :\n" + ex.toString());
        }
    }
}
