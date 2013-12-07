/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig.processing;

/**
 *
 * @author jason
 */
public class BillboardGenerator {
    public static void main(String[] args)
    {
        JSONFileTranslator translator = new JSONFileTranslator();
        translator.translate("/Users/jason/projects/yng/billboard", "/Users/jason/projects/yng/karma_inputs/billboard_reformatted.json", new BillboardFormatTranslator());
        
        JSONFileToCsvTranslator csvTranslator = new JSONFileToCsvTranslator();
        csvTranslator.translate("/Users/jason/projects/yng/review", "/Users/jason/projects/yng/fril_inputs/artist_with_mbid.csv", new FreebaseArtistsJustFreebaseIDoCsvTranslator());
        csvTranslator.translate("/Users/jason/projects/yng/billboard", "/Users/jason/projects/yng/fril_inputs/billboard.csv", new BillboardArtistOnlyToCSVTranslator());
    }
}
