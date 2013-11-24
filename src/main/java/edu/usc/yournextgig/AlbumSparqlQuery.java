/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import edu.usc.yournextgig.processing.ReleaseDateFormatter;
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
    private static ReleaseDateFormatter dateFormatter = ReleaseDateFormatter.createDefaultReleaseDateFormatter();
    
    private AlbumSparqlQuery() {
        
    }

    public static AlbumSparqlQuery getInstance() {
        if (null == instance) {
            instance = new AlbumSparqlQuery();
        }
        return instance;
    }

    @Override
    public Album search(String id) {
        Album album = super.search(id);
        searchForAlbumReviews(album);
        searchForAlbumAwards(album);
        return album;
    }
    @Override
    protected String getQueryStringFileName() {
        return "albumquery.rdf";
    }

    public List<Album> searchByArtist(String artistId) {

        List<Album> albums = searchForMultipleResults(artistId, "albumbyartistquery.rdf");
        for(Album album : albums)
        {
            searchForAlbumReviews(album);
            searchForAlbumAwards(album);
        }
        return albums;

    }

    private void translateReleaseDate(JSONObject jsonAlbum, Album album) throws JSONException {
            String dateString = jsonAlbum.getJSONObject("releaseDate").getString("value");
            Date d = dateFormatter.parseDate(dateString);
            album.setReleaseDate(d);

    }

    @Override
    protected Album translateQueryResult(JSONObject jsonAlbum) throws JSONException {
                Album album = new Album();
        album.setId(jsonAlbum.getJSONObject("album").getString("value"));
        album.setName(jsonAlbum.getJSONObject("albumname").getString("value"));
        if(jsonAlbum.has("rating"))
        {
            album.setRating(jsonAlbum.getJSONObject("rating").getString("value"));
        }
        translateReleaseDate(jsonAlbum, album);
        return album;
    }

    @Override
    protected Album emptyQueryResult() {
        return new Album();
    }

    private void searchForAlbumReviews(Album album) {
        if(!album.getReviews().isEmpty())
        {
            return;
        }
        album.getReviews().addAll(ReviewSparqlQuery.getInstance().searchByAlbum(album.getId()));
    }

    private void searchForAlbumAwards(Album album) {
        if(!album.getAwards().isEmpty())
        {
            return;
        }
        album.getAwards().addAll(AwardSparqlQuery.getInstance().searchByAlbum(album.getId()));
    }
}
