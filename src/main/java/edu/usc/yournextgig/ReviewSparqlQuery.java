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
public class ReviewSparqlQuery extends SparqlQuery<Review> {

    
    private static Logger LOG = LoggerFactory.getLogger(ReviewSparqlQuery.class);
    private static ReviewSparqlQuery instance;

    private ReviewSparqlQuery() {
    }

    public static ReviewSparqlQuery getInstance() {
        if (null == instance) {
            instance = new ReviewSparqlQuery();
        }
        return instance;
    }
    
    @Override
    protected Review emptyQueryResult() {
        return new Review();
    }

    @Override
    protected Review translateQueryResult(JSONObject object) throws JSONException {
        Review review = new Review();
        review.setGrade(object.getJSONObject("rating").getString("value"));
        review.setReview(object.getJSONObject("description").getString("value"));
        review.setSource(object.getJSONObject("author").getString("value"));
        return review;
    }

    @Override
    protected String getQueryStringFileName() {
        return "reviewquery.rdf";
    }
    
    public List<Review> searchByAlbum(String albumId)
    {
        return searchForMultipleResults(albumId, "reviewbyartistquery.rdf");
    }
}
