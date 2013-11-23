/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class ConcertSparqlQuery extends SparqlQuery<Concert> {

    private static Logger LOG = LoggerFactory.getLogger(ConcertSparqlQuery.class);
    private static ConcertSparqlQuery instance;
    private ConcertSparqlQuery()
    {
        
    }
    public static ConcertSparqlQuery getInstance()
    {
        if(null == instance)
        {
            instance = new ConcertSparqlQuery();
        }
        return instance;
    }
    
    @Override
    public Concert search(String id) {
        Concert concert = super.search(id);
        searchForArtists(concert);
        searchForVenue(concert);
        return concert;
        
    }
    
    @Override
    protected Concert translateQueryResult(JSONArray array) {
              
        if(array != null && array.length() > 0)
        {
            try {
                JSONObject jsonConcert = array.getJSONObject(0);
                Concert concert = translateQueryResult(jsonConcert);
                return concert;
            } catch (JSONException ex) {
                LOG.error("Unable to translate query result to concert: " + ex.getMessage());
            } 
        }
        return new Concert();
    }


    @Override
    protected String getQueryStringFileName() {
        return "concertquery.rdf";
    }

    private String searchByEventString = null;
    
    public List<Concert> searchByLocation(Double lat, Double lon, Date start, Date end) {
        
        SesameTool sesame = SesameTool.getInstance();
        //TODO add query
        searchByEventString = loadSearchString("eventbylocationquery.rdf");
     
        String populatedString = searchByEventString;
        LOG.trace(populatedString);
        JSONArray result = sesame.queryForData(populatedString);
        LOG.trace(result.toString());
        return translateQueryResults(result);
        
    }

    @Override
    protected Concert translateQueryResult(JSONObject jsonConcert) throws JSONException {
        Concert concert = new Concert();
        concert.setId(jsonConcert.getJSONObject("id").getString("value"));
        concert.setName(jsonConcert.getJSONObject("eventname").getString("value"));
        translateEventDate(jsonConcert, concert);
        concert.setInfo(jsonConcert.getJSONObject("url").getString("value"));
        return concert;
    }

    private void translateEventDate(JSONObject jsonConcert, Concert concert) throws JSONException {
        try {
            String dateString = jsonConcert.getJSONObject("eventdate").getString("value");
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            concert.setDtstart(d);
        } catch (ParseException ex) {
            LOG.error("Unable to translate event date: " + ex.getMessage());
        }
    }

    @Override
    protected Concert emptyQueryResult() {
        return new Concert();
    }

    private void searchForArtists(Concert concert) {
        concert.getArtists().addAll(ArtistSparqlQuery.getInstance().searchByEvent(concert.getId()));
    }

    private void searchForVenue(Concert concert) {
        concert.setVenue(VenueSparqlQuery.getInstance().searchByEvent(concert.getId()));
    }
    
}
