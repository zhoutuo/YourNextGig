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
@Path("/stubartist")
public class ArtistStubRESTQuery implements ArtistRESTQuery {

    @Override
    public Response search(String id) {
        Artist artist = new Artist();
        Award award = new Award();
        award.setName("Grammy Award for Album of the Year");
        award.setDate(new Date(System.currentTimeMillis()));
        artist.getAwards().add(award);
        Album album = new Album();
        album.setName("So Beautiful or So What");
        album.setId("http://rdf.freebase.com/ns/m.0g59_v_");
        album.setReleaseDate(new Date(System.currentTimeMillis() + 1000000));
        artist.getAlbums().add(album);
        artist.setId("05517043-ff78-4988-9c22-88c68588ebb9");
        artist.setInfo("http://en.wikipedia.org/wiki?curid=50745");
        artist.setName("Paul Simon");
        JSONObject obj = new JSONObject(artist);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response searchByEvent(String eventId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
