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
public class MetacriticReviewsToCsvTranslator implements JSONtoCSVTranslator{
    private static Logger LOG = LoggerFactory.getLogger(LastFmArtistToCsvTranslator.class);
     public void translateJSONtoCSV(String json, FileWriter csvOutputWriter) throws JSONException, IOException {
        ReleaseDateFormatter dateFormatter = ReleaseDateFormatter.createDefaultReleaseDateFormatter();
        ArtistNameFormatter artistFormatter = new ArtistNameFormatter();
        AlbumNameFormatter albumFormatter = new AlbumNameFormatter();
    
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
                String artistName = artistInfo.getString("name");
                candidateBuilder.append(artistFormatter.formatArtistName(artistName));
                candidateBuilder.append(",");
                String albumName = album.getString("name");
                candidateBuilder.append(albumFormatter.formatAlbumName(albumName));
                candidateBuilder.append(",");
                if(album.has("releaseDate"))
                {
                    
                    String dateString = album.get("releaseDate").toString();
                    candidateBuilder.append(dateFormatter.formatReleaseDate(dateString));
                    candidateBuilder.append(",");
                }
                 final String candidateArtistName = candidate.getString("artist");
                candidateBuilder.append(artistFormatter.formatArtistName(candidateArtistName));
                candidateBuilder.append(",");
                final String candidateAlbumName = candidate.getString("name");
                candidateBuilder.append(albumFormatter.formatAlbumName(candidateAlbumName));
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
