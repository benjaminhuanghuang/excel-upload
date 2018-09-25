package excelupload;

import excelupload.resources.FileService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class RESTApp extends Application<RESTServiceConfig> {

    public static void main(String[] args) throws Exception {
        new RESTApp().run(args);
    }

    @Override
    public void run(RESTServiceConfig config, Environment env) {
        // Register services
        env.jersey().register(new FileService());

        // Register health check
        env.healthChecks().register("template", new RESTCheck("0.01"));
    }
}
