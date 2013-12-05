/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author zhoutuoyang
 */
@Path("/stubalbum")
public class AlbumStubRESTQuery implements AlbumRESTQuery {

    @Override
    public Response search(String id) {
        Album album = new Album();
        album.setId("http://rdf.freebase.com/ns/m.0g59_v_");
        album.setName("So Beautiful or So What");
        album.setRating("85");
        album.setReleaseDate(new Date(System.currentTimeMillis() + 100000));
        Review[] reviews = new Review[2];
        reviews[0] = new Review();
        reviews[0].setSource("The Telegraph (UK)");
        reviews[0].setGrade("100");
        reviews[0].setReview("This album is a musical gumbo: a rich, surprising and ultimately satisfying stew of Simon's folk, rock and pop influences from all over the world.");
        reviews[1] = new Review();
        reviews[1].setSource("Filter");
        reviews[1].setGrade("92");
        reviews[1].setReview("Decades beyond the point at which most of his peers peaked, Paul Simon is still discovering new ways of writing and conveying amazing work and discovering beautifully unexpected and often spiritual language, as well as new rhythms, melodies and instrumental textures.");
        album.getReviews().add(reviews[0]);
        album.getReviews().add(reviews[1]);
        JSONObject obj = new JSONObject(album);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response searchByArtist(String artistId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
