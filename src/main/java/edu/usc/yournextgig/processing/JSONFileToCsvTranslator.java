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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class JSONFileToCsvTranslator extends JSONFileTranslator<JSONtoCSVTranslator> {
    
    public static void main(String[] args)
    {
        JSONFileToCsvTranslator thing = new JSONFileToCsvTranslator();
        thing.translate("/Users/jason/projects/yng/review", "/Users/jason/projects/yng/karma_inputs/artistTopAlbums.csv", new FreebaseArtistsToCsvTranslator());
        thing.translate("/Users/jason/projects/yng/metacritic", "/Users/jason/projects/yng/karma_inputs/reviews.csv", new MetacriticReviewsByArtistToCsvTranslator());
    }
    private static Logger LOG = LoggerFactory.getLogger(JSONFileToCsvTranslator.class);
    @Override
    public void translate(String inputDirectory, String outputFileName, JSONtoCSVTranslator translator) 
    {
        File[] jsonFiles = FileOperations.findJSONFiles(inputDirectory);
        
        FileWriter outputWriter = FileOperations.setupOutputWriter(outputFileName);
        try {
            translator.writeColumnHeaders(outputWriter);
        } catch (IOException ex) {
            LOG.error("Unable to write column headers", ex);
           return;
        }
        
        for(File f : jsonFiles)
        {
            translateJSONFile(f, translator, outputWriter);
            
        }
        try {
            outputWriter.close();
        } catch (IOException ex) {
            LOG.error("unable to close csv writer", ex);
        }
                    
    }
    
}
