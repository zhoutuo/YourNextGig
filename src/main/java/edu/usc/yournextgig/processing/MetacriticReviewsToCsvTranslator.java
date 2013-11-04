/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig.processing;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class MetacriticReviewsToCsvTranslator implements JSONtoCSVTranslator{
    private static Logger LOG = LoggerFactory.getLogger(LastFmArtistToCsvTranslator.class);
     public void translateJSONtoCSV(String json, FileWriter csvOutputWriter) throws JSONException, IOException {
        
        JSONObject artistInfo = new JSONObject(json);
        JSONArray topAlbums = artistInfo.getJSONArray("topAlbums");
        for(int i = 0; i < topAlbums.length(); i++)
        {
            JSONObject album = topAlbums.getJSONObject(i);
            JSONArray candidates = album.getJSONArray("candidates");
            for(int j = 0; j < candidates.length(); j++)
            {
                JSONObject candidate = candidates.getJSONObject(j);
                StringBuilder candidateBuilder = new StringBuilder();
                
                candidateBuilder.append(artistInfo.get("name").toString().replace(',', ' ').trim());
                candidateBuilder.append(",");
                candidateBuilder.append(album.get("name").toString().replace(',', ' ').trim());
                candidateBuilder.append(",");
                if(album.has("releaseDate"))
                {
                candidateBuilder.append(album.get("releaseDate").toString().trim());
                candidateBuilder.append(",");
                }
                final String candidateArtistName = candidate.get("artist").toString().replace(',', ' ').trim();
                candidateBuilder.append(candidateArtistName);
                candidateBuilder.append(",");
                candidateBuilder.append(candidate.get("title").toString().replace(',', ' ').trim());
                candidateBuilder.append(",");
                String dateString =candidate.get("releaseDate").toString().trim();
            
                SimpleDateFormat originalDateFormat = new SimpleDateFormat("MMM d, yyyy");
                
                SimpleDateFormat newDf = new SimpleDateFormat("MM/dd/yyyy");
                if(!dateString.isEmpty())
                {
                    try {
                        Date d = originalDateFormat.parse(dateString);
                        candidateBuilder.append(newDf.format(d));
                    } catch (ParseException ex) {
                        LOG.error("Unable to parse date for candidate " +  candidateArtistName,ex);
                    }
                }
                candidateBuilder.append(",");
                candidateBuilder.append(candidate.get("url"));

                candidateBuilder.append("\n");
                csvOutputWriter.write(candidateBuilder.toString());
            }
        }
    }

    @Override
    public void writeColumnHeaders(FileWriter csvOutputWriter) throws IOException {
                StringBuilder headersBuilder = new StringBuilder();
        headersBuilder.append("targetartist");
        headersBuilder.append(",");
        headersBuilder.append("targetalbumname");
        headersBuilder.append(",");
        headersBuilder.append("candidateartist");
        headersBuilder.append(",");
        headersBuilder.append("candidatealbumname");
        headersBuilder.append(",");
        headersBuilder.append("candidatereleasedate");
        headersBuilder.append(",");
        headersBuilder.append("url");
        headersBuilder.append("\n");
        csvOutputWriter.append(headersBuilder);
    }
}
