/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.util.Date;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 *
 * @author jason
 */
@Path("/stubconcert")
public class ConcertStubRESTQuery implements ConcertRESTQuery {

    @Override
    public Response search(Double lat, Double lon, Long starttime, Long endtime) {
        Concert concert = new Concert();
        concert.setId("1234356");
        concert.setDtend(new Date(System.currentTimeMillis()));
        concert.setDtend(new Date(System.currentTimeMillis()+1000));
        concert.setInfo("a good show");
        concert.setName("The 50 Annniversary Show");
        Venue venue = new Venue();
        venue.setId("987654");
        venue.setInfo("a good place");
        Location location = new Location();
        location.setCity("Los Angeles");
        location.setCountry("USA");
        location.setState("CA");
        Geo geo = new Geo();
        geo.setLatitude(45.0);
        geo.setLongitude(-118.0);
        location.setGeo(geo);
        venue.setLocation(location);
        venue.setName("The Greek Theater");
        concert.setVenue(venue);
        Artist artist = new Artist();
        artist.setId("5553333");
        artist.setInfo("a good band");
        artist.setName("The Beatles");
        concert.getArtists().add(artist);
        JSONObject obj = new JSONObject(concert);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response search(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}