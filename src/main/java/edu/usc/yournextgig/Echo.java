package edu.usc.yournextgig;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author zhoutuoyang
 */
@Path("/echo")
public class Echo {
    @GET
    public Response echo(@QueryParam("message") String msg) {
        return Response.status(Response.Status.OK).entity(msg).build();
        
    }
}
