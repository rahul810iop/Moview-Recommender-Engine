
/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class MovieRunnerSimilarRatings {

   private void letsInitialize() {
      String moviefile = "ratedmoviesfull.csv";
      String ratingsfile = "ratings.csv";
        
      MovieDatabase.initialize(moviefile);
      RaterDatabase.initialize(ratingsfile); 
      
      System.out.println("read data for "+RaterDatabase.size()+" raters");
      System.out.println("read data for "+MovieDatabase.size()+" movies");
    }
    public void printAverageRatings() {
      letsInitialize();
      FourthRatings fr = new FourthRatings();
      
      int minimalRaters = 35;
      ArrayList<Rating> avgRatingList = fr.getAverageRatings(minimalRaters);
      Collections.sort(avgRatingList);
      System.out.println("found "+avgRatingList.size()+" movies");
      
      System.out.println("Avg.Rating\t\t\tTitle");
      for(Rating rating : avgRatingList) {
          String title = MovieDatabase.getTitle(rating.getItem());
          System.out.println(rating.getValue()+"\t"+title);
      }
   }
   public void printAverageRatingsByYearAfterAndGenre() {
      letsInitialize();
       
      int selecYear = 1990;
      int minimalRaters = 8;
      String selecGenre = "Drama";
      
      Filter yf = new YearAfterFilter(selecYear);
      Filter gf = new GenreFilter(selecGenre);
      
      AllFilters filtersList = new AllFilters();
      
      filtersList.addFilter(yf);
      filtersList.addFilter(gf);

      FourthRatings fr = new FourthRatings();
      
      ArrayList<Rating> avgRatingList = fr.getAverageRatingsByFilter(minimalRaters,filtersList);
      Collections.sort(avgRatingList);
      System.out.println("found "+avgRatingList.size()+" movies");
     
      System.out.println("Rating\tTitle\tYear\tGenre");
      for(Rating rating : avgRatingList) {
          String genre = MovieDatabase.getGenres(rating.getItem());
          int year = MovieDatabase.getYear(rating.getItem());
          String title = MovieDatabase.getTitle(rating.getItem());
          System.out.println(rating.getValue()+"\t"+title+"\t"+year+"\t"+genre);
      }
   }
   
   public void printSimilarRatings() {
       letsInitialize();
       
       FourthRatings fr = new FourthRatings();
       
       String raterID = "71";
       int minimalRaters = 5;
       int topNumRaters = 20;
       
       ArrayList<Rating> recommendations = fr.getSimilarRatings(raterID,topNumRaters,minimalRaters);
       System.out.println(recommendations.size() + " movie(s) matched for raterID: "+raterID);
       System.out.println();
       
       MovieRunnerWithFilters mrwf = new MovieRunnerWithFilters();
       for(Rating rating: recommendations) {
           String title = MovieDatabase.getTitle(rating.getItem());
           System.out.println(mrwf.getAverageRatingOneMovie(title)+"\t\t"+title);
       }
   }
   
   public void printSimilarRatingsByGenre() {
       letsInitialize();
       
       String genre = "Mystery";
       String raterID = "964";
       int minimalRaters = 5;
       int topNumSimRaters = 20;
       
       FourthRatings fr = new FourthRatings();
       
       ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(raterID,topNumSimRaters,minimalRaters,new GenreFilter(genre));
       System.out.println(recommendations.size() + " movie(s) matched for raterID: "+raterID);
       System.out.println();
       
       MovieRunnerWithFilters mrwf = new MovieRunnerWithFilters();
       for(Rating rating: recommendations) {
           String title = MovieDatabase.getTitle(rating.getItem());
           System.out.println(mrwf.getAverageRatingOneMovie(title)+"\t\t"+title);
           System.out.println("\t\t"+MovieDatabase.getGenres(rating.getItem()));
        }
   }

   public void printSimilarRatingsByDirector() {
       letsInitialize();
       
       String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock"
       +",Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
       String raterID = "120";
       int minimalRaters = 2;
       int topNumSimRaters = 10;
       
       FourthRatings fr = new FourthRatings();
       
       ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(raterID,topNumSimRaters,minimalRaters,
       new DirectorsFilter(directors));
       System.out.println(recommendations.size() + " movie(s) matched for raterID: "+raterID);
       System.out.println();
       
       MovieRunnerWithFilters mrwf = new MovieRunnerWithFilters();
       for(Rating rating: recommendations) {
           String title = MovieDatabase.getTitle(rating.getItem());
           System.out.println(mrwf.getAverageRatingOneMovie(title)+"\t\t"+title);
           System.out.println("\t\t"+MovieDatabase.getDirector(rating.getItem()));
       }
   }
 
   public void printSimilarRatingsByGenreAndMinutes() {
      letsInitialize();
       
      int minMinutes = 80;
      int maxMinutes = 160;
      String selecGenre = "Drama";
      
      Filter yf = new MinutesFilter(minMinutes,maxMinutes);
      Filter gf = new GenreFilter(selecGenre);
      
      AllFilters filtersList = new AllFilters();
      
      filtersList.addFilter(yf);
      filtersList.addFilter(gf);

      int minimalRaters = 3;
      int topNumSimRaters = 10;
      String raterID = "168";
      
      FourthRatings fr = new FourthRatings();
       
      ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(raterID,topNumSimRaters,minimalRaters,
      filtersList);
      System.out.println(recommendations.size() + " movie(s) matched for raterID: "+raterID);
      System.out.println();
      
      MovieRunnerWithFilters mrwf = new MovieRunnerWithFilters();
       for(Rating rating: recommendations) {
           String title = MovieDatabase.getTitle(rating.getItem());
           System.out.println(mrwf.getAverageRatingOneMovie(title)+"\t\t"+title);
           System.out.println("\t\t"+MovieDatabase.getMinutes(rating.getItem())+"\t"+
           MovieDatabase.getGenres(rating.getItem()));
      }       
   }
   
   public void printSimilarRatingsByYearAfterAndMinutes() {
       int yearAfter = 1975;
       int minMinutes = 70;
       int maxMinutes = 200;
       
       Filter mf = new MinutesFilter(minMinutes,maxMinutes);
       Filter yf = new YearAfterFilter(yearAfter);
      
       AllFilters filtersList = new AllFilters();
      
       filtersList.addFilter(mf);
       filtersList.addFilter(yf);

       int minimalRaters = 5;
       int topNumSimRaters = 10;
       String raterID = "314";
      
       FourthRatings fr = new FourthRatings();
       
       letsInitialize();
       
       ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(raterID,topNumSimRaters,minimalRaters,
       filtersList);
       System.out.println(recommendations.size() + " movie(s) matched for raterID: "+raterID);
       System.out.println();
       
       MovieRunnerWithFilters mrwf = new MovieRunnerWithFilters();
       for(Rating rating: recommendations) {
           String title = MovieDatabase.getTitle(rating.getItem());
           System.out.println(mrwf.getAverageRatingOneMovie(title)+"\t\t"+title);
           System.out.println("\t\t"+MovieDatabase.getMinutes(rating.getItem())+"\t"+
           MovieDatabase.getYear(rating.getItem()));
       }
   }
}
