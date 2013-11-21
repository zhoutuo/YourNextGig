/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig.processing;

/**
 *
 * @author jason
 */
public class YNGInputGenerators {
    public static void main (String[] args){
        JSONFileToCsvTranslator.main(args);
        JSONFileConsolidator.main(args);
    }
}
