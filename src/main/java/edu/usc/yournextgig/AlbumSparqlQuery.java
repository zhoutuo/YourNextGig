/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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
public class AlbumSparqlQuery extends SparqlQuery<Album> {

    private static Logger LOG = LoggerFactory.getLogger(AlbumSparqlQuery.class);
    private static AlbumSparqlQuery instance;

    private AlbumSparqlQuery() {
    }

    public static AlbumSparqlQuery getInstance() {
        if (null == instance) {
            instance = new AlbumSparqlQuery();
        }
        return instance;
    }

    @Override
    protected String getQueryStringFileName() {
        return "albumquery.rdf";
    }
    private String searchByArtistString = null;

    public List<Album> searchByArtist(String artistId) {

        SesameTool sesame = SesameTool.getInstance();
        searchByArtistString = loadSearchString("albumbyartistquery.rdf");
        String populatedString = searchByArtistString.replace("{0}", artistId);
        LOG.trace(populatedString);
        JSONArray result = sesame.queryForData(populatedString);
        LOG.trace(result.toString());
        return translateQueryResults(result);

    }

    private void translateReleaseDate(JSONObject jsonAlbum, Album album) throws JSONException {
        try {
            String dateString = jsonAlbum.getJSONObject("releaseDate").getString("value");
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            album.setReleaseDate(d);
        } catch (ParseException ex) {
            LOG.error("Unable to translate query result to album: " + ex.getMessage());
        }
    }

    @Override
    protected Album translateQueryResult(JSONObject jsonAlbum) throws JSONException {
                Album album = new Album();
        album.setId(jsonAlbum.getJSONObject("album").getString("value"));
        album.setName(jsonAlbum.getJSONObject("albumname").getString("value"));
        translateReleaseDate(jsonAlbum, album);
        album.setInfo(jsonAlbum.getJSONObject("url").getString("value"));
        return album;
    }

    @Override
    protected Album emptyQueryResult() {
        return new Album();
    }
}
