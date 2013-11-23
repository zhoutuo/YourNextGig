/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class ArtistSparqlQuery extends SparqlQuery<Artist> {

    private static Logger LOG = LoggerFactory.getLogger(ArtistSparqlQuery.class);
    private static ArtistSparqlQuery instance;

    private ArtistSparqlQuery() {
    }

    public static ArtistSparqlQuery getInstance() {
        if (null == instance) {
            instance = new ArtistSparqlQuery();
        }
        return instance;
    }
    

    @Override
    public Artist search(String id) {
        Artist artist = super.search(id);
        searchForArtistAlbums(artist);
        return artist;
        
    }
    
    @Override
    protected String getQueryStringFileName() {
        return "artistquery.rdf";
    }
    private String searchByEventString = null;

    public List<Artist> searchByEvent(String eventId) {

        SesameTool sesame = SesameTool.getInstance();
        searchByEventString = loadSearchString("artistbyeventquery.rdf");
        String populatedString = searchByEventString.replace("{0}", eventId);
        LOG.trace(populatedString);
        JSONArray result = sesame.queryForData(populatedString);
        LOG.trace(result.toString());
        List<Artist> artists = translateQueryResults(result);
        for(Artist artist : artists)
        {
           searchForArtistAlbums(artist);
        }
        return artists;
        

    }

    @Override
    protected Artist translateQueryResult(JSONObject jsonArtist) throws JSONException {
        Artist artist = new Artist();
        artist.setId(jsonArtist.getJSONObject("id").getString("value"));
        artist.setName(jsonArtist.getJSONObject("artistname").getString("value"));
        artist.setInfo(jsonArtist.getJSONObject("wikipage").getString("value"));

        return artist;

    }

    @Override
    protected Artist emptyQueryResult() {
        return new Artist();
    }

    private void searchForArtistAlbums(Artist artist) {
        if(artist != null&& artist.getId() != null)
        {
        List<Album> albums = AlbumSparqlQuery.getInstance().searchByArtist(artist.getId());
        artist.getAlbums().addAll(albums);
        }
    }
}
