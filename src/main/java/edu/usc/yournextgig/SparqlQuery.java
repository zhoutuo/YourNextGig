/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public abstract class SparqlQuery {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SparqlQuery.class);
    
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
      
          
    protected abstract JSONObject translateQueryResultToVenue(JSONArray array, String id);
}
