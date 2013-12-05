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
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class AwardSparqlRESTQuery implements AwardRESTQuery {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AwardSparqlRESTQuery.class);
  
    @Override
    public Response search(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response searchByArtist(String artistId) {
        LOG.trace(artistId);
        AwardSparqlQuery instance = AwardSparqlQuery.getInstance();
        List<Award> awards = instance.searchByArtist(artistId);
        JSONArray results = new JSONArray();
        for(Award a : awards)
        {
            results.put(new JSONObject(a));
        }
        
        return Response.ok(results.toString(), MediaType.APPLICATION_JSON).build();
    }

    public Response searchByAlbum(String albumId) {
        LOG.trace(albumId);
        AwardSparqlQuery instance = AwardSparqlQuery.getInstance();
        List<Award> awards = instance.searchByAlbum(albumId);
        JSONArray results = new JSONArray();
        for(Award a : awards)
        {
            results.put(new JSONObject(a));
        }
        
        return Response.ok(results.toString(), MediaType.APPLICATION_JSON).build();
    }
    
}
