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
@Path("/venue")
public class StubVenueQuery implements VenueQuery{

    @Override
    public Response search(String id) {
        Venue venue = new Venue();
        venue.setId(id);
        venue.setInfo("a good place");
        venue.setName("The Greek Theatre");
        Geo geo = new Geo();
        geo.setLatitude(40.0);
        geo.setLongitude(-115.0);
        venue.setGeo(geo);
        JSONObject obj = new JSONObject(venue);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }
    
}
