/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig.processing;

import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author jason
 */
public interface JSONtoCSVTranslator extends JSONTranslator{
    public void writeColumnHeaders(FileWriter csvOutputWriter) throws IOException;
    
}
