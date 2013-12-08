/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
    private int queryCount = 0;
    private int skippedQueryCount = 0;
    private final ConcurrentHashMap<String, String> queryStrings = new ConcurrentHashMap<String, String>();
    private final ConcurrentHashMap<String, E> cachedResults = new ConcurrentHashMap<String, E>();
    private final ConcurrentHashMap<String, List<E>> cachedMultiResults = new ConcurrentHashMap<String, List<E>>();
    public E search(String id) {
        String queryStringFileName = getQueryStringFileName();
        return search(id, queryStringFileName);
    }
    
    protected E search(String id, String queryStringFileName) {
        if(cachedResults.containsKey(id))
        {
            skippedQueryCount++;
            return cachedResults.get(id);
        }
        SesameTool sesame = SesameTool.getInstance();
        String searchString = loadSearchString(queryStringFileName);
        String populatedString = searchString.replace("{0}", id);
        LOG.trace(populatedString);
        JSONArray result = sesame.queryForData(populatedString);
        E r = translateQueryResult(result);
        E oldr = cachedResults.putIfAbsent(id, r);
        if(oldr != null)
        {
           r = oldr;
        }
        queryCount++;
        return r;
    }

    protected List<E> searchForMultipleResults(String id, String queryStringFileName) {
        if(cachedMultiResults.containsKey(id))
        {
            skippedQueryCount++;
            return cachedMultiResults.get(id);
        }
        SesameTool sesame = SesameTool.getInstance();
        String searchString = loadSearchString(queryStringFileName);
        String populatedString = searchString.replace("{0}", id);
        LOG.trace(populatedString);
        JSONArray result = sesame.queryForData(populatedString);
        List<E> results = translateQueryResults(result);
        List<E> oldr = cachedMultiResults.putIfAbsent(id, results);
        if(oldr != null)
        {
           results = oldr;
        }
        queryCount++;
        return results;
    }
    
    public String loadSearchString(String fileName)
    {
        if(queryStrings.containsKey(fileName))
        {
            return queryStrings.get(fileName);
        }
        String searchString = "";
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
            queryStrings.put(fileName, searchString);
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
