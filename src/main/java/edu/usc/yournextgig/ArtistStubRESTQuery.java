/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 *
 * @author jason
 */
@Path("/stubartist")
public class ArtistStubRESTQuery implements ArtistRESTQuery {

    @Override
    public Response search(String id) {
        Artist artist = new Artist();
        artist.setId(id);
        artist.setInfo("Here's some background info");
        artist.setName("Beatles");
        JSONObject obj = new JSONObject(artist);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }
    
}
