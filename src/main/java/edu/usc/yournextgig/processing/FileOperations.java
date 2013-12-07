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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class FileOperations {
    private static Logger LOG = LoggerFactory.getLogger(FileOperations.class);
    
    protected static String readInFile(File f) {
        StringBuilder jsonBuffer = new StringBuilder();
        Reader reader = null;
        try {

            CharBuffer rawJsonBuffer = CharBuffer.allocate(1000);
            reader = new BufferedReader(new FileReader(f));
            int numRead = 0;
            while (-1 != (numRead = reader.read(rawJsonBuffer))) {
                jsonBuffer.append(rawJsonBuffer.array(), 0, numRead);
                rawJsonBuffer.clear();

            }

            return jsonBuffer.toString();

        } catch (FileNotFoundException ex) {
            LOG.error("Input file doesn't exist " + f.getAbsolutePath(), ex);
        } catch (IOException ex) {
            LOG.error("Unable to read from  file " + f.getAbsolutePath(), ex);
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }

            } catch (IOException ex) {
                LOG.error("Unable to close reader  ", ex);
            }
        }
        return "";
    }

    protected static boolean setupOutputDirectory(File outputFile) {
        File outputDirectory = outputFile.getParentFile();
        
        outputDirectory.mkdirs();
        if (!outputDirectory.exists() && !outputDirectory.isDirectory()) {
            LOG.error("Output " + outputDirectory.getAbsolutePath() + " is not a directory");
            return false;
        }
        return true;
    }

    protected static FileWriter setupOutputWriter(String outputFileName) {
        FileWriter outputWriter = null;
        File outputFile = new File(outputFileName);
        if (!setupOutputDirectory(outputFile)) {
            return outputWriter;
        }
        
        try { 
             outputWriter = new FileWriter(outputFile);
        } catch (IOException ex) {
           LOG.error("Unable to create output file ", ex);
        }
        return outputWriter;
    }

    protected static File[] findJSONFiles(String inputDirectory)
    {
        File inputDir = new File(inputDirectory);
        if(!inputDir.isDirectory())
        {
            LOG.error("Input " + inputDirectory + " is not a directory");
            return null;
        }
        
        
        File[] jsonFiles = inputDir.listFiles(new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name)
            {
                return name.toLowerCase().endsWith(".json");
            }
        });
        return jsonFiles;
    } 
}
