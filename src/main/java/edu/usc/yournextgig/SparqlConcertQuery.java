/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@Path("/concert")
public class SparqlConcertQuery extends SparqlQuery implements ConcertQuery {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SparqlConcertQuery.class);
  
    @Override
    public Response search(String id) {
        
        SesameTool sesame = SesameTool.getInstance();
        this.searchString = this.loadSearchString("concertquery.rdf");
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
                JSONObject jsonConcert = array.getJSONObject(0);
                    Concert concert = new Concert();
                    concert.setId(id);
                    concert.setName(jsonConcert.getJSONObject("eventname").getString("value"));
                    String dateString = jsonConcert.getJSONObject("eventdate").getString("value");
                    Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                    concert.setDtstart(d);
                    concert.setInfo(jsonConcert.getJSONObject("url").getString("value"));
                    return new JSONObject(concert);
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
                   LOG.error("Unable to parse release date: " + ex.getMessage());
                }
            }
        }
        return albums;
    }
    
   
    private String searchByLocationString;
    @Override
    public Response search(Double lat, Double lon, Long starttime, Long endtime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
