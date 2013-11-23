/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author jason
 */
public interface ReviewRESTQuery {
      @GET
    @Produces("application/json")
    public Response search(@QueryParam("id") String id);
    
    @GET
    @Path("/artist")
    @Produces("application/json")
    public Response searchByAlbum(@QueryParam("albumId") String albumId);
    
}
