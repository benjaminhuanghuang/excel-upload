package excelupload.resources;

import com.codahale.metrics.annotation.Timed;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/file")
public class FileService {
    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public String Test() {
        return "Hello";
    }

}
