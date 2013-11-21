/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
@Path("/venue")
public class SparqlVenueQuery extends SparqlQuery implements VenueQuery{
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SparqlVenueQuery.class);
   
    @Override
    public Response search(String id) {
        SesameTool sesame = SesameTool.getInstance();
        searchString = loadSearchString("venuequery.rdf");
        String filledoutsearchstring = searchString.replace("{0}", id);
        LOG.trace("sparql query:" + filledoutsearchstring);
        JSONArray array = sesame.queryForData(filledoutsearchstring);
        JSONObject obj = translateQueryResult(array, id);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }
 
  

    @Override
    protected JSONObject translateQueryResult(JSONArray array, String id) {
        if(array.length() > 0)
        {
            try {
                JSONObject jsonVenue = array.getJSONObject(0);
                Venue venue = new Venue();
                venue.setName(jsonVenue.getJSONObject("name").getString("value"));
                venue.setId(id);
                Location location = new Location();
                Geo geo = new Geo();
                geo.setLatitude(Double.parseDouble(jsonVenue.getJSONObject("lat").getString("value")));
                geo.setLongitude(Double.parseDouble(jsonVenue.getJSONObject("lon").getString("value")));
                location.setGeo(geo);
                if(jsonVenue.has("statename"))
                {
                    location.setState(jsonVenue.getJSONObject("statename").getString("value"));
                }
                if(jsonVenue.has("countryname"))
                {
                location.setCountry(jsonVenue.getJSONObject("countryname").getString("value"));
                }
                if(jsonVenue.has("cityname"))
                {
                    location.setCity(jsonVenue.getJSONObject("cityname").getString("value"));
                }
                venue.setLocation(location);
                return new JSONObject(venue);
            } catch (JSONException ex) {
              LOG.error("Unable to translate query result to venue: ", ex);
            }
        }
        return new JSONObject();
        
    }
}
