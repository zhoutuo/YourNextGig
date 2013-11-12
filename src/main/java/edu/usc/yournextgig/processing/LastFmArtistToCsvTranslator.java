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
public class LastFmArtistToCsvTranslator implements JSONtoCSVTranslator{
    private static Logger LOG = LoggerFactory.getLogger(LastFmArtistToCsvTranslator.class);
    @Override
    public void translateJSONtoCSV(String json, FileWriter csvOutputWriter) throws JSONException, IOException {
        
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
            String dateString = album.get("releaseDate").toString().replace(", 00:00", "").replace(',', ' ').trim();
            
            SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy");
            SimpleDateFormat newDf = new SimpleDateFormat("MM/dd/yyyy");
            if(!dateString.isEmpty())
            {
                try {
                    Date d = df.parse(dateString);
                    albumBuilder.append(newDf.format(d));
                } catch (ParseException ex) {
                   LOG.error("Unable to parse date for " +  artistName,ex);
                }
            }
            
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
