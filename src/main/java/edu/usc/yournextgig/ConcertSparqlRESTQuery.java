/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
@Path("/concert")
public class ConcertSparqlRESTQuery implements ConcertRESTQuery {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ConcertSparqlRESTQuery.class);
  
    @Override
    public Response search(String id) {
        LOG.trace(id);
        ConcertSparqlQuery instance = ConcertSparqlQuery.getInstance();
        Concert concert = instance.search(id);
        JSONObject obj = new JSONObject(concert);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response search(Double lat, Double lon, Long starttime, Long endtime) {
        LOG.trace("lat: " + lat);
        LOG.trace("lon: " + lon);
        LOG.trace("start: " + starttime);
        LOG.trace("stop: " + endtime);
        ConcertSparqlQuery instance = ConcertSparqlQuery.getInstance();
        List<Concert> concerts = instance.searchByLocation(lat, lon, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        Collections.sort(concerts, new ConcertComparator());
        
        JSONArray results = new JSONArray();
        for(int i = 0; i < 10 && i< concerts.size(); i++)
        {
            results.put(new JSONObject(concerts.get(i)));
        }
        return Response.ok(results.toString(), MediaType.APPLICATION_JSON).build();
    }

    
}
