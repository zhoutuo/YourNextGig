/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig.processing;

import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class FreebaseArtistsJustFreebaseIDoCsvTranslator implements JSONtoCSVTranslator {

    private static Logger LOG = LoggerFactory.getLogger(FreebaseArtistsJustFreebaseIDoCsvTranslator.class);

    @Override
    public void translateJSON(String json, FileWriter csvOutputWriter) throws JSONException, IOException {
        
        ArtistNameFormatter artistFormatter = new ArtistNameFormatter();
        JSONObject artistInfo = new JSONObject(json);

         StringBuilder candidateBuilder = new StringBuilder();
            String artistName = artistInfo.getString("name");
            candidateBuilder.append(artistFormatter.formatArtistName(artistName));
            candidateBuilder.append(",");
            candidateBuilder.append(artistInfo.getString("mid").substring(1).replace('/', '.'));
            candidateBuilder.append("\n");
            csvOutputWriter.write(candidateBuilder.toString());
        /*JSONArray keys = artistInfo.getJSONArray("/type/object/key");
        for (int i = 0; i < keys.length(); i++) {
            StringBuilder candidateBuilder = new StringBuilder();
            String artistName = artistInfo.getString("name");
            candidateBuilder.append(artistFormatter.formatArtistName(artistName));
            candidateBuilder.append(",");
            candidateBuilder.append(keys.getJSONObject(i).get("value"));
            candidateBuilder.append("\n");
            csvOutputWriter.write(candidateBuilder.toString());

        }*/


    }

    @Override
    public void writeColumnHeaders(FileWriter csvOutputWriter) throws IOException {
        StringBuilder headersBuilder = new StringBuilder();
        headersBuilder.append("artist");
        headersBuilder.append(",");
        headersBuilder.append("mbid");
        headersBuilder.append("\n");
        csvOutputWriter.append(headersBuilder);
    }

 
}
