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
public class MetacriticReviewsByArtistToCsvTranslator implements JSONtoCSVTranslator{
    private static Logger LOG = LoggerFactory.getLogger(LastFmArtistToCsvTranslator.class);
     public void translateJSONtoCSV(String json, FileWriter csvOutputWriter) throws JSONException, IOException {
        ReleaseDateFormatter dateFormatter = ReleaseDateFormatter.createDefaultReleaseDateFormatter();
        
        JSONObject artistInfo = new JSONObject(json);
        JSONArray albumReview = artistInfo.getJSONArray("/music/artist/album");
        for(int i = 0; i < albumReview.length(); i++)
        {
            JSONObject album = albumReview.getJSONObject(i);
            StringBuilder candidateBuilder = new StringBuilder();
                
                candidateBuilder.append(artistInfo.get("name").toString().replace(',', ' ').trim());
                candidateBuilder.append(",");
                candidateBuilder.append(album.get("name").toString().replace(',', ' ').trim());
                candidateBuilder.append(",");
                if(album.has("/music/album/release_date"))
                {
                    String dateString = album.get("/music/album/release_date").toString();
                    candidateBuilder.append(dateFormatter.formatReleaseDate(dateString));
                  
                }
                candidateBuilder.append(",");
                candidateBuilder.append(album.get("url"));

                candidateBuilder.append("\n");
                csvOutputWriter.write(candidateBuilder.toString());
            
        }
    }

    @Override
    public void writeColumnHeaders(FileWriter csvOutputWriter) throws IOException {
                StringBuilder headersBuilder = new StringBuilder();
        headersBuilder.append("artistname");
        headersBuilder.append(",");
        headersBuilder.append("albumname");
        headersBuilder.append(",");
        headersBuilder.append("albumreleasedate");
        headersBuilder.append(",");
        headersBuilder.append("url");
        headersBuilder.append("\n");
        csvOutputWriter.append(headersBuilder);
    }
}
