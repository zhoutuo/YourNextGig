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
public class JSONIdentityTranslator implements JSONTranslator {

    @Override
    public void translateJSON(String json, FileWriter outputWriter) throws JSONException, IOException {
        outputWriter.append(json);
    }
    
}
