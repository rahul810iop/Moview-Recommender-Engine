/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;


public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("data/ratedmoviesfull.csv", "data/ratings.csv");
    }
    public SecondRatings(String movieFile,String ratingsFile) {
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies(movieFile);
        myRaters = fr.loadRaters(ratingsFile);
    }
    
    public int getMovieSize() {
        return myMovies.size();
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
        ArrayList<Rating> avgRatingList = new ArrayList<Rating>();
        for(Movie movie : myMovies) {
           String ID = movie.getID(); 
           double avg = getAverageByID(ID,minimalRaters);
           if(avg > 0.0) {
               Rating currRating = new Rating(ID,avg);
               avgRatingList.add(currRating);
           }
        }
        return avgRatingList;
    }
    
    public String getTitle(String id) {
        for(Movie movie : myMovies) {
            if(movie.getID().equals(id)) {
                return movie.getTitle();
            }
        }
        return "The ID was not found";
    }
    
    public String getID(String title) {
        for(Movie movie : myMovies) {
            if(movie.getTitle().equals(title)) {
                return movie.getID();
            }
        }
        return "NO SUCH TITLE";
    }
}