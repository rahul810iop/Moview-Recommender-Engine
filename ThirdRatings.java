/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;


public class ThirdRatings {
    
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        // default constructor
        this("data/ratings.csv");
    }
    public ThirdRatings(String ratingsFile) {
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters(ratingsFile);
    }
    
    public int getRaterSize() {
        return myRaters.size();
    }

    private double getAverageByID(String id, int minimalRaters) {
        double average = 0;
        double total = 0;
        int countRaters = 0;
        for(Rater rater:myRaters){
            if(rater.hasRating(id)){
                countRaters++;
                total = total + rater.getRating(id);
            }
        }
        
        if(countRaters >= minimalRaters){
            average = total/countRaters;
        }
        return average;
    }
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<String> moviesIDs = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> avgRatingList = new ArrayList<Rating>();
        for(String ID : moviesIDs) { 
           double avg = getAverageByID(ID,minimalRaters);
           if(avg > 0.0) {
               Rating currRating = new Rating(ID,avg);
               avgRatingList.add(currRating);
           }
        }
        return avgRatingList;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
      ArrayList<Rating> avgRatingListWithFilters = new ArrayList<Rating>();
      ArrayList<String> moviesIDs = MovieDatabase.filterBy(filterCriteria);
      for(String ID : moviesIDs) { 
           double avg = getAverageByID(ID,minimalRaters);
           if(avg > 0.0) {
               Rating currRating = new Rating(ID,avg);
               avgRatingListWithFilters.add(currRating);
           }
        }
      return avgRatingListWithFilters;
    }
   
}
   