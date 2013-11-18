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
public class LastFmArtistToCsvTranslator implements JSONtoCSVTranslator{
    private static Logger LOG = LoggerFactory.getLogger(LastFmArtistToCsvTranslator.class);
    @Override
    public void translateJSONtoCSV(String json, FileWriter csvOutputWriter) throws JSONException, IOException {
        ReleaseDateFormatter dateFormatter = ReleaseDateFormatter.createDefaultReleaseDateFormatter();
        
        JSONObject artistInfo = new JSONObject(json);
        JSONArray topAlbums = artistInfo.getJSONArray("topAlbums");
        for(int i = 0; i < topAlbums.length(); i++)
        {
            StringBuilder albumBuilder = new StringBuilder();
            final String artistName = artistInfo.get("name").toString().replace(',', ' ').trim();
            albumBuilder.append(artistName);
            albumBuilder.append(",");
            JSONObject album = topAlbums.getJSONObject(i);
            albumBuilder.append(album.get("name").toString().replace(',', ' '));
            albumBuilder.append(",");
            String dateString = album.get("releaseDate").toString();
            albumBuilder.append(dateFormatter.formatReleaseDate(dateString));
            
            
            albumBuilder.append("\n");
            csvOutputWriter.write(albumBuilder.toString());
        }
    }

    @Override
    public void writeColumnHeaders(FileWriter csvOutputWriter) throws IOException{
        StringBuilder headersBuilder = new StringBuilder();
        headersBuilder.append("artist");
        headersBuilder.append(",");
        headersBuilder.append("albumname");
        headersBuilder.append(",");
        headersBuilder.append("releasedate");
        headersBuilder.append("\n");
        csvOutputWriter.append(headersBuilder);
    }
}
