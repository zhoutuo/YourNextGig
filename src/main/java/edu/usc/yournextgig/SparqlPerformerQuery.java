/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.util.logging.Level;
import java.util.logging.Logger;
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
@Path("/performer")
public class SparqlPerformerQuery extends SparqlQuery implements PerformerQuery {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SparqlPerformerQuery.class);
  
    @Override
    public Response search(String id) {
        
        SesameTool sesame = SesameTool.getInstance();
        this.searchString = this.loadSearchString("performerquery.rdf");
        String populatedString = searchString.replace("{0}", id);
        JSONArray result = sesame.queryForData(populatedString);
        JSONObject obj = translateQueryResultToVenue(result, id);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }

    @Override
    protected JSONObject translateQueryResultToVenue(JSONArray array, String id) {
       
        if(array != null && array.length() > 0)
        {
            try {
                JSONObject jsonPerformer = array.getJSONObject(0);
                Performer performer = new Performer();
                performer.setId(id);
                performer.setName(jsonPerformer.getJSONObject("artistname").getString("value"));
                performer.setInfo(jsonPerformer.getJSONObject("wikipage").getString("value"));
                return new JSONObject(performer);
            } catch (JSONException ex) {
                Logger.getLogger(SparqlPerformerQuery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new JSONObject();
    }
    
}
