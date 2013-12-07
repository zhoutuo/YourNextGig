/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.util.Comparator;
import java.util.List;

/**
 *
 * @author jason
 */
public class ConcertComparator implements Comparator<Concert> {

    @Override
    public int compare(Concert o1, Concert o2) {
        List<Artist> artists1 = o1.getArtists();
        List<Artist> artists2 = o2.getArtists();
        if((artists1 == null || artists1.isEmpty()) && (artists2 == null || artists2.isEmpty()))
        {
            return 0;
        }
        if(artists1 == null || artists1.isEmpty())
        {
            return 1;
        }
        if(artists2 == null || artists2.isEmpty())
        {
            return -1;
        }
        int maxReviewScore1 = 0;
        int maxReviewScore2 = 0;
        maxReviewScore1 = findTopScore(artists1, maxReviewScore1);
        maxReviewScore2 = findTopScore(artists2, maxReviewScore2);
        return maxReviewScore2 - maxReviewScore1;
    }

    private int findTopScore(List<Artist> artists1, int maxReviewScore1) throws NumberFormatException {
        for(Artist artist : artists1)
        {
            for(Album a : artist.getAlbums())
            {
                String rating = a.getRating();
                if(rating != null && !(rating = rating.trim()).isEmpty())
                {
                    int score = Integer.parseInt(rating);
                    if(score > maxReviewScore1)
                    {
                        maxReviewScore1 = score;
                    }
                }
            }
        }
        return maxReviewScore1;
    }
    
}
