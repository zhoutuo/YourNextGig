/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.util.List;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
@Path("/album")
public class AlbumSparqlRESTQuery implements AlbumRESTQuery {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AlbumSparqlRESTQuery.class);
  
    @Override
    public Response search(String id) {
        
        LOG.trace(id);
        AlbumSparqlQuery instance = AlbumSparqlQuery.getInstance();
        Album album = instance.search(id);
        JSONObject obj = new JSONObject(album);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response searchByArtist(String artistId) {
        LOG.trace(artistId);
        AlbumSparqlQuery instance = AlbumSparqlQuery.getInstance();
        List<Album> albums = instance.searchByArtist(artistId);
        JSONArray results = new JSONArray(albums);
        return Response.ok(results.toString(), MediaType.APPLICATION_JSON).build();
    }
    
}
