/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig.processing;

/**
 *
 * @author jason
 */
public class AlbumNameFormatter {
 
    public String formatAlbumName(String albumName)
    {
        return albumName.replace(',', ' ').replace('"', ' ').replace("&amp;", "&").trim();
    }
}
