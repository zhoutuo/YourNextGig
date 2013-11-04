/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.logging.Level;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class JSONFileToCsvTranslator {
    
    public static void main(String[] args)
    {
        JSONFileToCsvTranslator thing = new JSONFileToCsvTranslator();
        thing.translate("/Users/jason/projects/yng/data", "/Users/jason/projects/yng/data/artistTopAlbums.csv", new LastFmArtistToCsvTranslator());
        thing.translate("/Users/jason/projects/yng/review", "/Users/jason/projects/yng/review/reviews.csv", new MetacriticReviewsToCsvTranslator());
    }
    private static Logger LOG = LoggerFactory.getLogger(JSONFileToCsvTranslator.class);
    public void translate(String inputDirectory, String outputFileName, JSONtoCSVTranslator translator) 
    {
        File inputDir = new File(inputDirectory);
        if(!inputDir.isDirectory())
        {
            LOG.error("Input " + inputDirectory + " is not a directory");
            return;
        }
        File outputFile = new File(outputFileName);
        File outputDirectory = outputFile.getParentFile();
        
        if(!outputDirectory.exists() && !outputDirectory.isDirectory())
        {
            LOG.error("Output " + outputDirectory.getAbsolutePath() + " is not a directory");
            return;
        }
        outputDirectory.mkdirs();
        FileWriter csvOutputWriter;
        try { 
             csvOutputWriter = new FileWriter(outputFile);
             translator.writeColumnHeaders(csvOutputWriter);
        } catch (IOException ex) {
           LOG.error("Unable to create output file ", ex);
           return;
        }
        
        File[] jsonFiles = inputDir.listFiles(new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name)
            {
                return name.toLowerCase().endsWith(".json");
            }
        });
        
        for(File f : jsonFiles)
        {
            Reader reader = null;
            try {
                StringBuilder jsonBuffer = new StringBuilder();
                CharBuffer rawJsonBuffer = CharBuffer.allocate(1000);
                reader = new BufferedReader(new FileReader(f));
                int numRead = 0;
                while(-1 != (numRead = reader.read(rawJsonBuffer)))
             
                {
                        jsonBuffer.append(rawJsonBuffer.array(), 0, numRead);
                        rawJsonBuffer.clear();
                    
                }
                translator.translateJSONtoCSV(jsonBuffer.toString(), csvOutputWriter);
                
            } catch (FileNotFoundException ex) {
               LOG.error("Input file doesn't exist " + f.getAbsolutePath(), ex);
            } catch (IOException ex) {
                LOG.error("Unable to read from  file " + f.getAbsolutePath(), ex);
            } catch (JSONException ex) {
                LOG.error("Unable to parse json from  file " + f.getAbsolutePath(), ex);
            } finally {
                try {
                    if(null != reader)
                    {
                        reader.close();
                    }
                    
                } catch (IOException ex) {
                    LOG.error("Unable to close reader  ", ex);
                }
            }
            
        }
        try {
            csvOutputWriter.close();
        } catch (IOException ex) {
            LOG.error("unable to close csv writer", ex);
        }
                    
    }

    
}
