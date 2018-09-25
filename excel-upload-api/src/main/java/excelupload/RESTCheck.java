package excelupload;

import com.codahale.metrics.health.HealthCheck;

public class RESTCheck  extends HealthCheck {
    private final String version;

    public RESTCheck(String version) {
        this.version = version;
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy("Service is health");
    }
}