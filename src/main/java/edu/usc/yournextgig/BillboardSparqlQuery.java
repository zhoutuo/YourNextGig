/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class BillboardSparqlQuery extends SparqlQuery<Ranking> {

    
    private static Logger LOG = LoggerFactory.getLogger(BillboardSparqlQuery.class);
    private static BillboardSparqlQuery instance;

    private BillboardSparqlQuery() {
    }

    public static BillboardSparqlQuery getInstance() {
        if (null == instance) {
            instance = new BillboardSparqlQuery();
        }
        return instance;
    }
    
    @Override
    protected Ranking emptyQueryResult() {
        return new Ranking();
    }

    @Override
    protected Ranking translateQueryResult(JSONObject object) throws JSONException {
        Ranking ranking = new Ranking();
        ranking.setYear(object.getJSONObject("year").getString("value"));
        ranking.setRanking(object.getJSONObject("ranking").getString("value"));
        return ranking;
    }

    @Override
    protected String getQueryStringFileName() {
        return "billboardquery.rdf";
    }
    
    public List<Ranking> searchByArtist(String artistId)
    {
        return searchForMultipleResults(artistId, "billboardbyartistquery.rdf");
    }
}
