
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class MovieRunnerAverage {

   public void printAverageRatings() {
      SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv"); 
      System.out.println("Movie size : "+sr.getMovieSize());
      System.out.println("Rater size : "+sr.getRaterSize());
      
      int minimalRaters = 12;
      ArrayList<Rating> avgRatingList = sr.getAverageRatings(minimalRaters);
      Collections.sort(avgRatingList);
      System.out.println("Avg. Rating\tTitle");
      for(Rating rating : avgRatingList) {
          System.out.println(rating.getValue()+"\t"+sr.getTitle(rating.getItem()));
      }
   }
   
   public void getAverageRatingOneMovie() {
      SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv");
      int minimalRaters = 1;
      String title = "Moneyball";
      String movieID = sr.getID(title);
      
      ArrayList<Rating> avgRatingsList = sr.getAverageRatings(minimalRaters);
      for(Rating r : avgRatingsList) {
          String currTitle = sr.getTitle(r.getItem());
          if(currTitle.equals(title)) {
              System.out.println("Avg. rating of "+title+":"+r.getValue());
          }
      }
   }
}
