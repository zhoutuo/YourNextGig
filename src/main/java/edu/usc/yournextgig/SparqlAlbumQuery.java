/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
@Path("/album")
public class SparqlAlbumQuery extends SparqlQuery implements AlbumQuery {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SparqlAlbumQuery.class);
  
    @Override
    public Response search(String id) {
        
        SesameTool sesame = SesameTool.getInstance();
        this.searchString = this.loadSearchString("albumquery.rdf");
        String populatedString = searchString.replace("{0}", id);
        JSONArray result = sesame.queryForData(populatedString);
        JSONObject obj = translateQueryResult(result, id);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }

    @Override
    protected JSONObject translateQueryResult(JSONArray array, String id) {
       
        if(array != null && array.length() > 0)
        {
            try {
                JSONObject jsonAlbum = array.getJSONObject(0);
                    Album album = new Album();
                    album.setId(id);
                    album.setName(jsonAlbum.getJSONObject("albumname").getString("value"));
                    String dateString = jsonAlbum.getJSONObject("releaseDate").getString("value");
                    Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                    album.setReleaseDate(d);
                    album.setInfo(jsonAlbum.getJSONObject("url").getString("value"));
                    return new JSONObject(album);
            } catch (JSONException ex) {
                LOG.error("Unable to translate query result to album: " + ex.getMessage());
            } catch (ParseException ex) {
                LOG.error("Unable to translate query result to album: " + ex.getMessage());
            }
        }
        return new JSONObject();
    }
   
    protected JSONArray translateQueryResults(JSONArray array, String id) {
       
        JSONArray albums = new JSONArray();
        if(array != null)
        {
            for(int i = 0; i < array.length(); i++)
            {
                try {
                    JSONObject jsonAlbum = array.getJSONObject(i);
                    Album album = new Album();
                    //this needs to "be got" from the query
                    album.setId(jsonAlbum.getJSONObject("album").getString("value"));
                    album.setName(jsonAlbum.getJSONObject("albumname").getString("value"));
                    String dateString = jsonAlbum.getJSONObject("releaseDate").getString("value");
                    Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                    album.setReleaseDate(d);
                    album.setInfo(jsonAlbum.getJSONObject("url").getString("value"));
                    albums.put(new JSONObject(album));
                } catch (JSONException ex) {
                    LOG.error("Unable to translate query result to album: " + ex.getMessage());
                } catch (ParseException ex) {
                    Logger.getLogger(SparqlAlbumQuery.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return albums;
    }
    
    private String searchByArtistString = null;
    @Override
    public Response searchByArtist(String artistId) {
        
        SesameTool sesame = SesameTool.getInstance();
        this.searchByArtistString = this.loadSearchString("albumbyartistquery.rdf");
        String populatedString = searchByArtistString.replace("{0}", artistId);
        LOG.info(populatedString);
        JSONArray result = sesame.queryForData(populatedString);
        LOG.info(result.toString());
        JSONArray obj = translateQueryResults(result, artistId);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }
    
}
