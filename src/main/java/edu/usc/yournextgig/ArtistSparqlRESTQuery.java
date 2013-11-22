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
@Path("/artist")
public class ArtistSparqlRESTQuery implements ArtistRESTQuery {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ArtistSparqlRESTQuery.class);
  
    @Override
    public Response search(String id) {
        LOG.trace(id);
        ArtistSparqlQuery instance = ArtistSparqlQuery.getInstance();
        Artist artist = instance.search(id);
        JSONObject obj = new JSONObject(artist);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }
    
}
