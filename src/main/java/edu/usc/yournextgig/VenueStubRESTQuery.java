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
@Path("/stubvenue")
public class VenueStubRESTQuery implements VenueRESTQuery{

    @Override
    public Response search(String id) {
        Venue venue = new Venue();
        venue.setId(id);
        venue.setInfo("a good place");
        venue.setName("The Greek Theatre");
        Location location = new Location();
        location.setCity("Los Angeles");
        location.setCountry("USA");
        location.setState("CA");
        Geo geo = new Geo();
        geo.setLatitude(45.0);
        geo.setLongitude(-118.0);
        location.setGeo(geo);
        venue.setLocation(location);
        JSONObject obj = new JSONObject(venue);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }
    
}
