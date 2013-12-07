/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class BillboardSparqlRESTQuery implements BillboardRESTQuery {

    private static Logger LOG = LoggerFactory.getLogger(BillboardSparqlRESTQuery.class);
       
    @Override
    public Response search(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response searchByArtist(String artistId) {
         LOG.trace(artistId);
        BillboardSparqlQuery instance = BillboardSparqlQuery.getInstance();
        List<Ranking> rankings = instance.searchByArtist(artistId);
        JSONArray array = new JSONArray();
        for(Ranking ranking : rankings)
        {
            array.put(new JSONObject(ranking));
        }
        return Response.ok(array.toString(), MediaType.APPLICATION_JSON).build();        
    }
    
}
