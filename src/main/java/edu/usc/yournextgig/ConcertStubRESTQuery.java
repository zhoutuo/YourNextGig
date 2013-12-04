/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.util.Date;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
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
        concert.setId("18581349");
        concert.setDtstart(new Date(System.currentTimeMillis()));
        concert.setName("Paul Simon and Sting at The Forum");
        Venue venue = new Venue();
        venue.setId("987654");
        venue.setInfo("a good place");
        Location location = new Location();
        location.setCity("Los Angeles");
        location.setCountry("USA");
        location.setState("CA");
        Geo geo = new Geo();
        geo.setLatitude(33.9582047);
        geo.setLongitude(-118.3405316);
        location.setGeo(geo);
        venue.setLocation(location);
        venue.setName("The Forum");
        concert.setVenue(venue);
        Artist artist = new Artist();
        artist.setId("05517043-ff78-4988-9c22-88c68588ebb9");
        artist.setInfo("http://en.wikipedia.org/wiki?curid=50745");
        artist.setName("Paul Simon");
        concert.getArtists().add(artist);
        Concert concert2 = new Concert();
        concert2.setId("18637324");
        concert2.setDtstart(new Date(System.currentTimeMillis() + 100000));
        concert2.setName("KROQ Almost Acoustic Christmas 2013");
        Venue venue2 = new Venue();
        venue2.setId("3034");
        Geo geo2 = new Geo();
        geo2.setLongitude(-118.2813035);
        geo2.setLatitude(34.0225788);
        Location location2 = new Location();
        location2.setGeo(geo2);
        venue2.setLocation(location2);
        venue2.setName("Shrine Auditorium");
        concert2.setVenue(venue2);
        Artist[] artists = new Artist[5];
        artists[0] = new Artist();
        artists[0].setId("52074ba6-e495-4ef3-9bb4-0703888a9f68");
        artists[0].setName("Arcade Fire");
        concert2.getArtists().add(artists[0]);
        artists[1] = new Artist();
        artists[1].setId("52074ba6-e495-4ef3-9bb4-0703888a9f68");
        artists[1].setName("Bastille");
        concert2.getArtists().add(artists[1]);
        artists[2] = new Artist();
        artists[2].setId("52074ba6-e495-4ef3-9bb4-0703888a9f68");
        artists[2].setName("Capital Cities");
        concert2.getArtists().add(artists[2]);
        artists[3] = new Artist();
        artists[3].setId("52074ba6-e495-4ef3-9bb4-0703888a9f68");
        artists[3].setName("Phoenix");
        concert2.getArtists().add(artists[3]);
        artists[4] = new Artist();
        artists[4].setId("52074ba6-e495-4ef3-9bb4-0703888a9f68");
        artists[4].setName("Portugal. The Man");
        concert2.getArtists().add(artists[4]);   
        
        JSONArray obj = new JSONArray();
        obj.put(new JSONObject(concert));
        obj.put(new JSONObject(concert2));
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response search(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
