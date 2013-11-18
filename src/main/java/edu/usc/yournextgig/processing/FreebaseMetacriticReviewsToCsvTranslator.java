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
public class FreebaseMetacriticReviewsToCsvTranslator implements JSONtoCSVTranslator{
    private static Logger LOG = LoggerFactory.getLogger(FreebaseMetacriticReviewsToCsvTranslator.class);
     public void translateJSONtoCSV(String json, FileWriter csvOutputWriter) throws JSONException, IOException {
        ReleaseDateFormatter dateFormatter = ReleaseDateFormatter.createDefaultReleaseDateFormatter();
        JSONObject artistInfo = new JSONObject(json);
        JSONArray topAlbums = artistInfo.getJSONArray("/music/artist/album");
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
                candidateBuilder.append(album.get("name").toString().replace(',', ' ').replace('"', ' ').trim());
                candidateBuilder.append(",");
                if(album.has("/music/album/release_date"))
                {
                    String dateString = album.get("/music/album/release_date").toString();
                    candidateBuilder.append(dateFormatter.formatReleaseDate(dateString));
                    candidateBuilder.append(",");
                }
                final String candidateArtistName = candidate.get("artist").toString().replace(',', ' ').trim();
                candidateBuilder.append(candidateArtistName);
                candidateBuilder.append(",");
                candidateBuilder.append(candidate.get("name").toString().replace(',', ' ').replace('"', ' ').trim());
                candidateBuilder.append(",");
                String dateString =candidate.get("releaseDate").toString().trim();
                candidateBuilder.append(dateFormatter.formatReleaseDate(dateString));
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
        headersBuilder.append("targetreleasedate");
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
