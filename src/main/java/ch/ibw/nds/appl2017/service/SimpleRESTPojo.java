package ch.ibw.nds.appl2017.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/x")
public class SimpleRESTPojo {

    // http://localhost:8080/web_war_exploded/test/x
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {
        return "Hello JAX-RS ---> "  + new Date().toString();
    }

    // http://localhost:8080/web_war_exploded/test/x/hallo
    @GET
    @Path("/{param}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMessage(@PathParam("param") String msg) {
        String output = "Jersey say : " + msg;
        return Response.status(200).entity(output).build();
    }


}