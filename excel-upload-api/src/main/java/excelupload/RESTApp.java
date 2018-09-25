package excelupload;

import excelupload.resources.FileService;
import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class RESTApp extends Application<RESTServiceConfig> {

    public static void main(String[] args) throws Exception {
        new RESTApp().run(args);
    }


    @Override
    public void initialize(Bootstrap<RESTServiceConfig> bootstrap) {
        bootstrap.addBundle(new MultiPartBundle());
    }

    @Override
    public void run(RESTServiceConfig config, Environment env) {

        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
                env.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");


        // Register services
        env.jersey().register(new FileService());

        // Register health check
        env.healthChecks().register("template", new RESTCheck("0.01"));
    }
}
