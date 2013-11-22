/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
@Path("/venue")
public class VenueSparqlRESTQuery implements VenueRESTQuery{
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(VenueSparqlRESTQuery.class);
   
    @Override
    public Response search(String id) {
        VenueSparqlQuery instance = VenueSparqlQuery.getInstance();
        Venue v = instance.search(id);        
        JSONObject obj = new JSONObject(v);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }

 
 
}
