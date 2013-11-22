/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public abstract class SparqlQuery<E> {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SparqlQuery.class);
    
    
    public E search(String id) {
        SesameTool sesame = SesameTool.getInstance();
        this.searchString = this.loadSearchString(getQueryStringFileName());
        String populatedString = searchString.replace("{0}", id);
        JSONArray result = sesame.queryForData(populatedString);
        return translateQueryResult(result);
    }
    
    protected String searchString = null;
      public String loadSearchString(String fileName)
    {
        if(searchString != null && !searchString.isEmpty())
        {
            return searchString;
        }
        searchString = "";
        try {
            BufferedReader reader = new BufferedReader( new InputStreamReader(this.getClass().getResourceAsStream(fileName)));
            String         line = null;
            StringBuilder  stringBuilder = new StringBuilder();
            String         ls = System.getProperty("line.separator");

            while( ( line = reader.readLine() ) != null ) {
                stringBuilder.append( line );
                stringBuilder.append( ls );
            }

            searchString = stringBuilder.toString();
        } catch (IOException ex) {
            LOG.error("Unable to load search string", ex);
            
        }
        return searchString;
    }
      
    protected E translateQueryResult(JSONArray array) {

        if (array != null && array.length() > 0) {
            try {
                JSONObject json = array.getJSONObject(0);
                return translateQueryResult(json);
            } catch (JSONException ex) {
                LOG.error("Unable to translate query result: " + ex.getMessage());
            }
        }
        return emptyQueryResult();
    }
    
    protected List<E> translateQueryResults(JSONArray array) {

        List<E> results = new LinkedList<E>();
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject json = array.getJSONObject(i);
                    results.add(translateQueryResult(json));
                } catch (JSONException ex) {
                    LOG.error("Unable to translate query result: " + ex.getMessage());
                }
            }
        }
        return results;
    }
    
    protected abstract E emptyQueryResult();
    protected abstract E translateQueryResult(JSONObject object) throws JSONException;
    
    protected abstract String getQueryStringFileName();
}
