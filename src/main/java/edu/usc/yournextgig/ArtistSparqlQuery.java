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
        List<Album> albums = AlbumSparqlQuery.getInstance().searchByArtist(id);
        artist.getAlbums().addAll(albums);
        return artist;
        
    }
    
    @Override
    protected String getQueryStringFileName() {
        return "artistquery.rdf";
    }
    private String searchByEventString = null;

    public List<Artist> searchByArtist(String artistId) {

        SesameTool sesame = SesameTool.getInstance();
        searchByEventString = loadSearchString("artistbyeventquery.rdf");
        String populatedString = searchByEventString.replace("{0}", artistId);
        LOG.trace(populatedString);
        JSONArray result = sesame.queryForData(populatedString);
        LOG.trace(result.toString());
        return translateQueryResults(result);

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
}
