/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig.processing;

/**
 *
 * @author jason
 */
public class ArtistNameFormatter {
 
    public String formatArtistName(String artistName)
    {
        return artistName.replace(',', ' ').replace('"', ' ').replace("&amp;", "&").trim();
    }
}
