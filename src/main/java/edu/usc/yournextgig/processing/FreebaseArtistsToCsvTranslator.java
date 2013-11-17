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
public class FreebaseArtistsToCsvTranslator implements JSONtoCSVTranslator{
    private static Logger LOG = LoggerFactory.getLogger(FreebaseArtistsToCsvTranslator.class);
     public void translateJSONtoCSV(String json, FileWriter csvOutputWriter) throws JSONException, IOException {
        
        JSONObject artistInfo = new JSONObject(json);
        JSONArray topAlbums = artistInfo.getJSONArray("/music/artist/album");
        for(int i = 0; i < topAlbums.length(); i++)
        {
            JSONObject album = topAlbums.getJSONObject(i);
                           StringBuilder candidateBuilder = new StringBuilder();
                
                candidateBuilder.append(artistInfo.get("name").toString().replace(',', ' ').trim());
                candidateBuilder.append(",");
                candidateBuilder.append(album.get("name").toString().replace(',', ' ').trim());
              candidateBuilder.append(",");
            if(album.has("/music/album/release_date"))
                {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat newDf = new SimpleDateFormat("MM/dd/yyyy");
                    String dateString = album.get("/music/album/release_date").toString().replace(", 00:00", "").replace(',', ' ').trim();
                    if(null != dateString && !dateString.isEmpty())
                    {
                        Date d;
                        try {
                            d = df.parse(dateString);
                            candidateBuilder.append(newDf.format(d));

                        } catch (ParseException ex) {
                            try{
                                 df = new SimpleDateFormat("yyyy");
                                  d = df.parse(dateString);
                                candidateBuilder.append(newDf.format(d));
                            }
                            catch (ParseException ex1)
                            {
                                LOG.error("unable to parse date", ex1);
                            }
                            LOG.error("unable to parse date", ex);
                        }
                    }
                    else
                    {
                        candidateBuilder.append(" ");    
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
        headersBuilder.append("\n");
        csvOutputWriter.append(headersBuilder);
    }
}
