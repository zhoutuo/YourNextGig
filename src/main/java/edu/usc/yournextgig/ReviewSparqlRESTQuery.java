/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class ReviewSparqlRESTQuery implements ReviewRESTQuery {

    private static Logger LOG = LoggerFactory.getLogger(ReviewSparqlRESTQuery.class);
       
    @Override
    public Response search(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response searchByAlbum(String albumId) {
         LOG.trace(albumId);
        ReviewSparqlQuery instance = ReviewSparqlQuery.getInstance();
        List<Review> reviews = instance.searchByAlbum(albumId);
        JSONArray array = new JSONArray();
        for(Review review : reviews)
        {
            array.put(new JSONObject(review));
        }
        return Response.ok(array.toString(), MediaType.APPLICATION_JSON).build();        
    }
    
}
