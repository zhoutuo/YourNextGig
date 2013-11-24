/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import edu.usc.yournextgig.processing.ReleaseDateFormatter;
import java.util.Date;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class AwardSparqlQuery extends SparqlQuery<Award>{
    
    private static Logger LOG = LoggerFactory.getLogger(AwardSparqlQuery.class);
    private static AwardSparqlQuery instance;
    private static ReleaseDateFormatter dateFormatter = ReleaseDateFormatter.createDefaultReleaseDateFormatter();
    private AwardSparqlQuery() {
    }

    public static AwardSparqlQuery getInstance() {
        if (null == instance) {
            instance = new AwardSparqlQuery();
        }
        return instance;
    }
    
    public List<Award> searchByArtist(String artistId) {
        return searchForMultipleResults(artistId, "awardbyartistquery.rdf");
    }
    public List<Award> searchByAlbum(String albumId) {
        return searchForMultipleResults(albumId, "awardbyalbumquery.rdf");
    }
    
    @Override
    protected Award emptyQueryResult() {
        return new Award();
    }

    @Override
    protected Award translateQueryResult(JSONObject object) throws JSONException {
        Award award = new Award();
        award.setName(object.getJSONObject("awardname").getString("value"));
        if(object.has("awarddate"))
        {
            
            Date d = dateFormatter.parseDate(object.getJSONObject("awarddate").getString("value"));
            award.setDate(d);
        }
        return award;
    }

    @Override
    protected String getQueryStringFileName() {
        return "awardquery.rdf";
    }
    
}
