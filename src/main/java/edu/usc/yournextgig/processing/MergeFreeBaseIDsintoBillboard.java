/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig.processing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class MergeFreeBaseIDsintoBillboard {
        private static Logger LOG = LoggerFactory.getLogger(MergeFreeBaseIDsintoBillboard.class);

        public static void main(String[] args) throws JSONException, IOException
        {
            MergeFreeBaseIDsintoBillboard thing = new MergeFreeBaseIDsintoBillboard();
            File frilResults = new File("/Users/jason/projects/yng/fril_outputs/results_billboard.csv");
            File billboardJSON = new File("/Users/jason/projects/yng/karma_inputs/billboard_reformatted.json");
            FileWriter writer = FileOperations.setupOutputWriter("/Users/jason/projects/yng/karma_inputs/billboard.json");
            thing.garbage(frilResults, billboardJSON, writer);
        }
        public void garbage(File frilResults, File billboardJSON, FileWriter outputWriter) throws JSONException, IOException
        {
            String fril = FileOperations.readInFile(frilResults);
            String billboard = FileOperations.readInFile(billboardJSON);
            
            JSONArray billboardRankings = new JSONArray(billboard);
            String[] frilLines = fril.split("\n");
            Map<String, String> artistNameToFreebaseID = new HashMap<String,String>();
            for(String frilLine: frilLines)
            {
                String[] columns = frilLine.split(",");
                String artistName = columns[0].replace("\"","");
                String mid = "http://rdf.freebase.com/ns/" + columns[2].replace("\"","");
                
                artistNameToFreebaseID.put(artistName, mid);
            }
            for(int i = 0; i < billboardRankings.length(); i++)
            {
                JSONObject artistRankings = billboardRankings.getJSONObject(i);
                String name = artistRankings.getString("name");
                if(artistNameToFreebaseID.containsKey(name))
                {
                  
                    artistRankings.put("mid", artistNameToFreebaseID.get(name));
                }
            }
            outputWriter.append(billboardRankings.toString());
            outputWriter.close();
        }
}
