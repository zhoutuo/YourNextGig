/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 *
 * @author jason
 */
@Path("/stubperformer")
public class StubPerformerQuery implements PerformerQuery {

    @Override
    public Response search(String id) {
        Performer performer = new Performer();
        performer.setId(id);
        performer.setInfo("here's some background info");
        performer.setName("Beatles");
        JSONObject obj = new JSONObject(performer);
        return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }
    
}
