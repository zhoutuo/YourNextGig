/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig.processing;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author jason
 */
public class BillboardArtistOnlyToCSVTranslator implements JSONtoCSVTranslator{

    @Override
    public void translateJSON(String json, FileWriter outputWriter) throws JSONException, IOException {
        JSONObject billboardRankings = new JSONObject(json);
        ArtistNameFormatter artistFormatter = new ArtistNameFormatter();
        Iterator<String> artistKeys = billboardRankings.keys();
        while(artistKeys.hasNext())
        {
            String artistKey = artistKeys.next();
            outputWriter.append(artistFormatter.formatArtistName(artistKey));
            outputWriter.append("\n");
        }
    }

    @Override
    public void writeColumnHeaders(FileWriter csvOutputWriter) throws IOException {
         StringBuilder headersBuilder = new StringBuilder();
        headersBuilder.append("artist");
        headersBuilder.append("\n");
        csvOutputWriter.append(headersBuilder);
    }
    
}
