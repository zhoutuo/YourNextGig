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
public class FreebaseArtistsToCsvTranslator implements JSONtoCSVTranslator {

    private static Logger LOG = LoggerFactory.getLogger(FreebaseArtistsToCsvTranslator.class);

    public void translateJSONtoCSV(String json, FileWriter csvOutputWriter) throws JSONException, IOException {
        ReleaseDateFormatter dateFormatter = ReleaseDateFormatter.createDefaultReleaseDateFormatter();
        ArtistNameFormatter artistFormatter = new ArtistNameFormatter();
        AlbumNameFormatter albumFormatter = new AlbumNameFormatter();
        
        JSONObject artistInfo = new JSONObject(json);
        JSONArray topAlbums = artistInfo.getJSONArray("/music/artist/album");
        for (int i = 0; i < topAlbums.length(); i++) {
            JSONObject album = topAlbums.getJSONObject(i);
            StringBuilder candidateBuilder = new StringBuilder();
            String artistName = artistInfo.getString("name");
            candidateBuilder.append(artistFormatter.formatArtistName(artistName));
            candidateBuilder.append(",");
            String albumName = album.getString("name");
            candidateBuilder.append(albumFormatter.formatAlbumName(albumName));
            candidateBuilder.append(",");
            if (album.has("/music/album/release_date")) {
                String dateString = album.get("/music/album/release_date").toString();
                String dateResult = dateFormatter.formatReleaseDate(dateString); 
                candidateBuilder.append(dateResult);
                candidateBuilder.append(",");
                if(album.has("mid"))
                {
                    String mid = album.getString("mid");
                    mid = "http://rdf.freebase.com/" +mid.substring(1).replace('/', '.');
                    candidateBuilder.append(mid);
                }
            }
            
            candidateBuilder.append("\n");
            csvOutputWriter.write(candidateBuilder.toString());

        }
    }

    @Override
    public void writeColumnHeaders(FileWriter csvOutputWriter) throws IOException {
        StringBuilder headersBuilder = new StringBuilder();
        headersBuilder.append("artist");
        headersBuilder.append(",");
        headersBuilder.append("albumname");
        headersBuilder.append(",");
        headersBuilder.append("releasedate");
        headersBuilder.append(",");
        headersBuilder.append("mbid");
        headersBuilder.append("\n");
        csvOutputWriter.append(headersBuilder);
    }

 
}
