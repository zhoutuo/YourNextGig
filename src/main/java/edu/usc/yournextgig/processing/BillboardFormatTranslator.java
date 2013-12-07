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
public class BillboardFormatTranslator implements JSONTranslator{

    @Override
    public void translateJSON(String json, FileWriter outputWriter) throws JSONException, IOException {
        JSONObject billboardRankings = new JSONObject(json);
        Iterator<String> artistKeys = billboardRankings.keys();
        JSONArray newOutput = new JSONArray();
        while(artistKeys.hasNext())
        {
            JSONObject newArtistOutput = new JSONObject();
            String artistKey = artistKeys.next();
            newArtistOutput.put("name", artistKey);
            JSONObject artistRankings = billboardRankings.getJSONObject(artistKey);
            Iterator<String> rankingKeys = artistRankings.keys();
            JSONArray newRankings = new JSONArray();
            while(rankingKeys.hasNext())
            {
                JSONObject ranking = new JSONObject();
                String rankingKey = rankingKeys.next();
                ranking.put("year", rankingKey);
                ranking.put("ranking", artistRankings.get(rankingKey));
                newRankings.put(ranking);
            }
            newArtistOutput.put("rankings",newRankings);
            newOutput.put(newArtistOutput);
        }
        System.out.println(newOutput.length());
        outputWriter.append(newOutput.toString());
    }
    
}
