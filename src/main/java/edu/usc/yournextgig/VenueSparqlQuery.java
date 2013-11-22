/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.text.ParseException;
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
public class VenueSparqlQuery extends SparqlQuery<Venue> {

    private static Logger LOG = LoggerFactory.getLogger(VenueSparqlQuery.class);
    private static VenueSparqlQuery instance;

    private VenueSparqlQuery() {
    }

    public static VenueSparqlQuery getInstance() {
        if (null == instance) {
            instance = new VenueSparqlQuery();
        }
        return instance;
    }

    @Override
    protected String getQueryStringFileName() {
        return "venuequery.rdf";
    }

    @Override
    protected Venue translateQueryResult(JSONObject jsonVenue) throws JSONException {
        Venue venue = new Venue();
        venue.setName(jsonVenue.getJSONObject("name").getString("value"));
        venue.setId(jsonVenue.getJSONObject("id").getString("value"));
        Location location = new Location();
        Geo geo = new Geo();
        geo.setLatitude(Double.parseDouble(jsonVenue.getJSONObject("lat").getString("value")));
        geo.setLongitude(Double.parseDouble(jsonVenue.getJSONObject("lon").getString("value")));
        location.setGeo(geo);
        if (jsonVenue.has("statename")) {
            location.setState(jsonVenue.getJSONObject("statename").getString("value"));
        }
        if (jsonVenue.has("countryname")) {
            location.setCountry(jsonVenue.getJSONObject("countryname").getString("value"));
        }
        if (jsonVenue.has("cityname")) {
            location.setCity(jsonVenue.getJSONObject("cityname").getString("value"));
        }
        venue.setLocation(location);
        return venue;
    }

    @Override
    protected Venue emptyQueryResult() {
        return new Venue();
    }
}
