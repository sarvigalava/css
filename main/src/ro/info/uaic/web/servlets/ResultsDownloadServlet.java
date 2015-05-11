package ro.info.uaic.web.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ro.info.uaic.io.FileExportService;
import ro.info.uaic.persistence.PersistenceService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by lotus on 11.05.2015.
 */
@WebServlet("/files/results")
@Component
public class ResultsDownloadServlet extends HttpServlet
{
    @Autowired private PersistenceService persistenceService;
    @Autowired private FileExportService fileExportService;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String csv = fileExportService.export(persistenceService.getAllCandidates(), persistenceService.getAllResults());
        byte[] bytes = csv.getBytes(Charset.forName("UTF-8"));
        // modifies response
        response.setContentType("text/csv");
        response.setContentLength(bytes.length);

        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=results.csv";
        response.setHeader(headerKey, headerValue);

        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();
        outStream.write(bytes);
        outStream.close();
    }
}
