/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig.processing;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class ReleaseDateFormatter {

    private static Logger LOG = LoggerFactory.getLogger(ReleaseDateFormatter.class);
    private List<DateFormat> sourceDateFormats;

    public ReleaseDateFormatter(List<DateFormat> sourceDateFormats) {
        this.sourceDateFormats = sourceDateFormats;
    }

    public String formatReleaseDate(String dateString) {
        String dateResult = " ";
        SimpleDateFormat newDf = new SimpleDateFormat("MM/dd/yyyy");
        Date d = parseDate(dateString);
        if(d != null)
        {
            dateResult = newDf.format(d);
        }
        return dateResult;
    }
    
    public Date parseDate(String dateString)
    {
      dateString = dateString.toString().replace(", 00:00", "").replace(',', ' ').trim();
        
        
        Date d = null;
        if (null != dateString && !dateString.isEmpty()) {
            
            for (DateFormat df : sourceDateFormats) {
                try {
                    d = df.parse(dateString);
                    
                } catch (Exception ex) {

                    LOG.trace("unable to parse date", ex);
                }
            }

        }
        return d;
    }
    public static ReleaseDateFormatter createDefaultReleaseDateFormatter() {
        List<DateFormat> formats = new ArrayList<DateFormat>();
        formats.add(new SimpleDateFormat("yyyy-MM-dd"));        
        formats.add(new SimpleDateFormat("d MMM yyyy"));
        formats.add(new SimpleDateFormat("MMM dd  yyyy"));
        formats.add(new SimpleDateFormat("MMM d, yyyy"));
        formats.add(new SimpleDateFormat("yyyy"));
        ReleaseDateFormatter dateFormatter = new ReleaseDateFormatter(formats);
        return dateFormatter;
    }

    
}
