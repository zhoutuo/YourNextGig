/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
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
        LOG.info("searching for concert with id " + id);
        Concert concert = super.search(id);
        searchForArtists(concert);
        searchForVenue(concert);
        return concert;
        
    }
  

    @Override
    protected String getQueryStringFileName() {
        return "concertquery.rdf";
    }
    
    
    public List<Concert> searchByLocation(Double lat, Double lon, Date start, Date end) {
        
        SesameTool sesame = SesameTool.getInstance();
        String searchString = loadSearchString("eventbylocationquery.rdf");
        JSONArray result = sesame.queryForData(searchString);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Concert> concerts = translateQueryResults(result);
        
        Iterator<Concert> i = concerts.iterator();
        while(i.hasNext())
        {
            Concert concert = i.next();
            LOG.trace("Processing " + concert.getName() + " on " + concert.getDtstart());
            if(concert.getDtstart().after(start))
            {
                executor.submit(new ConcertPopulator(concert));
            }
            else
            {
                LOG.trace("removed " + concert.getName() + " because it was on " + concert.getDtstart());
                i.remove();
            }
        }
        LOG.info("searched for " + concerts.size());
        executor.shutdown();
        try {
            executor.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException ex) {
            LOG.error("took too long to complete query");
        }
        return concerts;
    }

    @Override
    protected Concert translateQueryResult(JSONObject jsonConcert) throws JSONException {
        Concert concert = new Concert();
        concert.setId(jsonConcert.getJSONObject("id").getString("value"));
        concert.setName(jsonConcert.getJSONObject("eventname").getString("value"));
        translateEventDate(jsonConcert, concert);
        concert.setInfo(jsonConcert.getJSONObject("url").getString("value"));
        Geo geo = new Geo();
        geo.setLatitude(Double.parseDouble(jsonConcert.getJSONObject("lat").getString("value")));
        geo.setLongitude(Double.parseDouble(jsonConcert.getJSONObject("lon").getString("value")));
        concert.setGeo(geo);
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
        if(!concert.getArtists().isEmpty())
        {
            return;
        }
        concert.getArtists().addAll(ArtistSparqlQuery.getInstance().searchByEvent(concert.getId()));
    }

    private void searchForVenue(Concert concert) {
        concert.setVenue(VenueSparqlQuery.getInstance().searchByEvent(concert.getId()));
    }


    /**
     *
     * @author jason
     */
    public class ConcertPopulator implements Runnable{

        Concert c;
        public ConcertPopulator(Concert c)
        {
            this.c = c;
        }
        @Override
        public void run() {
            searchForArtists(c);  
            searchForVenue(c);
            
        }

    }

}
