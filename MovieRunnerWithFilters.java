
/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class MovieRunnerWithFilters {

   public void printAverageRatings() {
      ThirdRatings tr = new ThirdRatings("data/ratings.csv"); 
      System.out.println("read data for "+tr.getRaterSize()+" raters");
      
      MovieDatabase.initialize("ratedmoviesfull.csv");
      System.out.println("read data for "+MovieDatabase.size()+" movies");
      
      int minimalRaters = 35;
      ArrayList<Rating> avgRatingList = tr.getAverageRatings(minimalRaters);
      Collections.sort(avgRatingList);
      System.out.println("found "+avgRatingList.size()+" movies");
      
      System.out.println("Avg.Rating\t\t\tTitle");
      for(Rating rating : avgRatingList) {
          String title = MovieDatabase.getTitle(rating.getItem());
          System.out.println(rating.getValue()+"\t"+title);
      }
   }
   
   public double getAverageRatingOneMovie(String title) {
      SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv");
      int minimalRaters = 10;
      String movieID = sr.getID(title);
      
      ArrayList<Rating> avgRatingsList = sr.getAverageRatings(minimalRaters);
      for(Rating r : avgRatingsList) {
          String currTitle = sr.getTitle(r.getItem());
          if(currTitle.equals(title)) {
              return (r.getValue());
          }
      }
      return 0.0;
   }
   
   public void printAverageRatingsByYearAfter() {
      Filter yf = new YearAfterFilter(2000);
      String movieFile = "ratedmoviesfull.csv";
      String ratingsFile = "data/ratings.csv";
       
      ThirdRatings tr = new ThirdRatings(ratingsFile);
      System.out.println("read data for "+tr.getRaterSize()+" raters");
       
      MovieDatabase.initialize(movieFile);
      System.out.println("read data for "+MovieDatabase.size()+" movies");
      
      int minimalRaters = 20;
      ArrayList<Rating> avgRatingList = tr.getAverageRatingsByFilter(minimalRaters,yf);
      Collections.sort(avgRatingList);
      System.out.println("found "+avgRatingList.size()+" movies");
      
      System.out.println("Avg.Rating\t year \t\tTitle");
      for(Rating rating : avgRatingList) {
          int year = MovieDatabase.getYear(rating.getItem());
          String title = MovieDatabase.getTitle(rating.getItem());
          System.out.println(rating.getValue()+"\t"+year+"\t"+title);
      }
   }
   
   public void printAverageRatingsByGenre() {
      String Genre = "Comedy";
      Filter gr = new GenreFilter(Genre);
      String movieFile = "ratedmoviesfull.csv";
      String ratingsFile = "data/ratings.csv";
       
      ThirdRatings tr = new ThirdRatings(ratingsFile);
      System.out.println("read data for "+tr.getRaterSize()+" raters");
       
      MovieDatabase.initialize(movieFile);
      System.out.println("read data for "+MovieDatabase.size()+" movies");
      
      int minimalRaters = 20;
      ArrayList<Rating> avgRatingList = tr.getAverageRatingsByFilter(minimalRaters,gr);
      Collections.sort(avgRatingList);
      System.out.println("found "+avgRatingList.size()+" movies");
      
      System.out.println("Avg.Rating\t\tTitle\tGenre");
      for(Rating rating : avgRatingList) {
          String genre = MovieDatabase.getGenres(rating.getItem());
          String title = MovieDatabase.getTitle(rating.getItem());
          System.out.println(rating.getValue()+"\t"+title);
          System.out.println("\t\t\t"+genre);
      }
   }
   
   public void printAverageRatingsByMinutes() {
     int minMinutes = 105, maxMinutes = 135;
     Filter minutes = new MinutesFilter(minMinutes,maxMinutes);
       
     String movieFile = "ratedmoviesfull.csv";
     String ratingsFile = "data/ratings.csv";
      
     ThirdRatings tr = new ThirdRatings(ratingsFile);
     System.out.println("read data for "+tr.getRaterSize()+" raters");
       
     MovieDatabase.initialize(movieFile);
     System.out.println("read data for "+MovieDatabase.size()+" movies");
      
     int minimalRaters = 5;
     ArrayList<Rating> avgRatingList = tr.getAverageRatingsByFilter(minimalRaters,minutes);
     Collections.sort(avgRatingList);
     System.out.println("found "+avgRatingList.size()+" movies");
      
     System.out.println("Avg.Rating\tDuration\tTitle");
      for(Rating rating : avgRatingList) {
          String Title = MovieDatabase.getTitle(rating.getItem());
          int duration = MovieDatabase.getMinutes(rating.getItem());
          System.out.println(rating.getValue()+"\t"+duration+"\t"+Title);
     }
   }
   
   public void printAverageRatingsByDirectors() {
     String directors = "Clint Eastwood,Joel Coen,Martin Scorsese,"+
     "Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
     Filter df = new DirectorsFilter(directors);
       
     String movieFile = "ratedmoviesfull.csv";
     String ratingsFile = "data/ratings.csv";
      
     ThirdRatings tr = new ThirdRatings(ratingsFile);
     System.out.println("read data for "+tr.getRaterSize()+" raters");
       
     MovieDatabase.initialize(movieFile);
     System.out.println("read data for "+MovieDatabase.size()+" movies");
      
     int minimalRaters = 4;
     ArrayList<Rating> avgRatingList = tr.getAverageRatingsByFilter(minimalRaters,df);
     Collections.sort(avgRatingList);
     System.out.println("found "+avgRatingList.size()+" movies");
      
     System.out.println("Rating\tTitle\tDirector(s)");
     for(Rating rating : avgRatingList) {
         String Title = MovieDatabase.getTitle(rating.getItem());
         String particularDirector = MovieDatabase.getDirector(rating.getItem());
         System.out.println(rating.getValue()+"\t"+Title+"\t"+particularDirector);
     }
  }
  
  public void printAverageRatingsByYearAfterAndGenre() {
      int selecYear = 1990;
      int minimalRaters = 8;
      String selecGenre = "Drama";
      
      Filter yf = new YearAfterFilter(selecYear);
      Filter gf = new GenreFilter(selecGenre);
      
      AllFilters filtersList = new AllFilters();
      
      filtersList.addFilter(yf);
      filtersList.addFilter(gf);

     String movieFile = "ratedmoviesfull.csv";
     String ratingsFile = "data/ratings.csv";
      
     ThirdRatings tr = new ThirdRatings(ratingsFile);
     System.out.println("read data for "+tr.getRaterSize()+" raters");
       
     MovieDatabase.initialize(movieFile);
     System.out.println("read data for "+MovieDatabase.size()+" movies");
      
     ArrayList<Rating> avgRatingList = tr.getAverageRatingsByFilter(minimalRaters,filtersList);
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
 
   public void printAverageRatingsByDirectorsAndMinutes() {
      int minMinutes = 90;
      int maxMinutes = 180;
      String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
      Filter minutes = new MinutesFilter(minMinutes,maxMinutes);
      Filter dr = new DirectorsFilter(directors);
      
      AllFilters filtersList = new AllFilters();
      
      filtersList.addFilter(minutes);
      filtersList.addFilter(dr);

      int minimalRaters = 3;
      String movieFile = "ratedmoviesfull.csv";
     String ratingsFile = "data/ratings.csv";
      
     ThirdRatings tr = new ThirdRatings(ratingsFile);
     System.out.println("read data for "+tr.getRaterSize()+" raters");
       
     MovieDatabase.initialize(movieFile);
     System.out.println("read data for "+MovieDatabase.size()+" movies");
      
     ArrayList<Rating> avgRatingList = tr.getAverageRatingsByFilter(minimalRaters,filtersList);
     Collections.sort(avgRatingList);
     System.out.println("found "+avgRatingList.size()+" movies");
     
     System.out.println("Rating\tTitle\tDuration\tDirector(s)");
     for(Rating rating : avgRatingList) {
          int duration = MovieDatabase.getMinutes(rating.getItem());
          String particularDirectors = MovieDatabase.getDirector(rating.getItem());
          String title = MovieDatabase.getTitle(rating.getItem());
          System.out.println(rating.getValue()+"\t"+title+"\t"+duration+"\t"+particularDirectors);
     }
  }
}


