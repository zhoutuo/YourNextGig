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
public interface ConcertQuery {
    
    @GET
    @Produces("application/json")
    public Response search(@QueryParam("lat") Double lat, @QueryParam("lon") Double lon,
    @QueryParam("starttime") Long starttime, @QueryParam("endtime") Long endtime);
}
