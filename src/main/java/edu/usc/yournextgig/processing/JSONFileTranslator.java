/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig.processing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public  class JSONFileTranslator<E extends JSONTranslator> {
    
    private static Logger LOG = LoggerFactory.getLogger(JSONFileTranslator.class);
    
    public void translate(String inputDirectory, String outputFileName, E translator) 
    {
        File[] jsonFiles = FileOperations.findJSONFiles(inputDirectory);
        
        FileWriter outputWriter = FileOperations.setupOutputWriter(outputFileName);
        
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
    protected void translateJSONFile(File f, JSONTranslator translator, FileWriter outputWriter) {
        
       String contents = FileOperations.readInFile(f);
       if(contents == null || contents.isEmpty())
       {
           return;
       }
        try {
            translator.translateJSON(contents, outputWriter);
        } catch (JSONException ex) {
           LOG.error("Unable to translate JSON ",ex);
        } catch (IOException ex) {
            LOG.error("Error writing out translation ", ex);
        }
     
    }
   
    
}
