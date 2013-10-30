/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig.processing;

import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONException;

/**
 *
 * @author jason
 */
public interface JSONtoCSVTranslator {
    public void writeColumnHeaders(FileWriter csvOutputWriter) throws IOException;
    public void translateJSONtoCSV(String json, FileWriter csvOutputWriter) throws JSONException, IOException;
}
