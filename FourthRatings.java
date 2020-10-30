/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;


public class FourthRatings {
    
    //private ArrayList<Rater> myRaters;
    
    public int getRaterSize() {
        return RaterDatabase.getRaters().size();
    }

    private double getAverageByID(String id, int minimalRaters) {
        double average = 0;
        double total = 0;
        int countRaters = 0;
        for(Rater rater:RaterDatabase.getRaters()){
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
    
    private double dotProduct(Rater me, Rater r) {
        double similarValue = 0;
        ArrayList<String> itemsRatedByMe = me.getItemsRated();
        
        for(String movieID : itemsRatedByMe) {
            if(r.hasRating(movieID)) {
                double rRating = r.getRating(movieID)-5;
                double myRating = me.getRating(movieID)-5;
                
                similarValue += rRating * myRating;
           }
        }
        return similarValue;
    }
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> similarList= new ArrayList<Rating>();
        
        Rater me = RaterDatabase.getRater(id);
        
        for(Rater r: RaterDatabase.getRaters()) {
            String raterID = r.getID();
            //add dot_product(r,me) to list if r! = me
            if(!raterID.equals(id)) {
                double similarValue = dotProduct(me,r);
                if(similarValue > 0) {
                Rating similarRating = new Rating(raterID,similarValue);
                similarList.add(similarRating);
              }
            }
        }
        Collections.sort(similarList, Collections.reverseOrder());
    	return similarList;
    }
    
   public ArrayList<Rating> getSimilarRatings(String id,int numSimilarRaters,int minimalRaters) {
       return getSimilarRatingsByFilter(id,numSimilarRaters,minimalRaters,new TrueFilter());
   }
    public ArrayList<Rating> getSimilarRatingsByFilter(String id,int numSimilarRaters,int minimalRaters,Filter filterCriteria) {
      ArrayList<Rating> weightedMovieSimRatings = new ArrayList<>();
      ArrayList<Rating> list = getSimilarities(id);	
      ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
      for (String movieID : movies) {
         double weightedAverage = 0;
         double sum = 0;
         int countRaters = 0;
	 for (int i = 0; i < numSimilarRaters; i++) {
	     Rating r = list.get(i);
	     double weight = r.getValue();
	     String raterID = r.getItem();
	     Rater myRater = RaterDatabase.getRater(raterID);
	     if(myRater.hasRating(movieID)) {
	    	 countRaters++;
	    	 sum += weight * myRater.getRating(movieID);
	     }
	 }
	 if (countRaters >= minimalRaters) {
	     weightedAverage = sum / countRaters;
	     weightedMovieSimRatings.add(new Rating(movieID, weightedAverage));
	 }			 
      }
      Collections.sort(weightedMovieSimRatings, Collections.reverseOrder());
      return weightedMovieSimRatings;	
   }
}