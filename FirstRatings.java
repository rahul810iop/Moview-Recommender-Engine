import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
public class FirstRatings {

    public ArrayList<Movie> loadMovies (String fileName) {
        FileResource resource = new FileResource(fileName);
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        for(CSVRecord record:resource.getCSVParser()) {
            Movie currMovie = new Movie(record.get("id"),record.get("title"),
            record.get("year"),record.get("genre"),record.get("director"),
            record.get("country"),record.get("poster"),
            Integer.parseInt(record.get("minutes")));
            movieList.add(currMovie);
        }
        return movieList;
    }
    public void testLoadMovies () {
        ArrayList<Movie> loadedMovieList = loadMovies("data/ratedMovies_short.csv");
        System.out.println("No. of movies : "+loadedMovieList.size());
        System.out.println("LoadedMovie : "+loadedMovieList);
        //System.out.println("No.of movies with Comedy genre : "+genreComedy);
    }
    
    
    public ArrayList<Movie> genreComedy() {
        //int counts = 0;
        ArrayList<Movie> movieList = loadMovies("data/ratedMoviesfull.csv");
        ArrayList<Movie> comedyMovie = new ArrayList<Movie>();
        for(Movie m : movieList) {
            String genres = m.getGenres();
            if(genres.contains("Comedy")) {
               comedyMovie.add(m);  
            }
        }
        return comedyMovie;
    }
    public void testGenreComedy() {
        ArrayList<Movie> comedyGenre = genreComedy();
        System.out.println("No.of Comedy movies : "+comedyGenre.size());
    }
    
    
    public ArrayList<Movie> lengthyMovies() {
        ArrayList<Movie> movieList = loadMovies("data/ratedMoviesfull.csv");
        ArrayList<Movie> longMovies = new ArrayList<Movie>();
        for(Movie m : movieList) {
           int min = m.getMinutes();
           if(min > 150) {
              longMovies.add(m);  
           }
        }
        return longMovies; 
    }
    public void testLengthyMovies() {
        ArrayList<Movie> lengthyMovie = lengthyMovies();
        System.out.println("No.of lengthy movies : "+lengthyMovie.size());
    }
    
    
    public HashMap<String,ArrayList<Movie>> sameDirector() {
        HashMap<String,ArrayList<Movie>> director = new HashMap<String,ArrayList<Movie>> ();
        ArrayList<Movie> movieList = loadMovies("data/ratedMoviesfull.csv");
        for(Movie m : movieList) {
           ArrayList<Movie> movies;
           if(!director.containsKey(m.getDirector())) {
               movies = new ArrayList<Movie> ();
               movies.add(m);
               director.put(m.getDirector(),movies);
           }
           else {
               director.get(m.getDirector()).add(m);
           }
        }
        return director;
    }
    public void testSameDirector() {
        HashMap<String,ArrayList<Movie>> directorMap= sameDirector();
        int maxMovies = 0;
        for(String director : directorMap.keySet()) {
            int movies = (directorMap.get(director)).size();
            if(maxMovies < movies) {
                maxMovies = movies;
            }
        }
        System.out.println("Max no.of movies by a single director : "+maxMovies);
        System.out.println("Max movies by :");
        for(String director : directorMap.keySet()) {
            int movies = (directorMap.get(director)).size();
            if(maxMovies == movies) {
                System.out.println(director);
            }
        }
    }
    
    public ArrayList<Rater> loadRaters(String fileName) {
        ArrayList<Rater> raters = new ArrayList<>();
        FileResource resource = new FileResource(fileName);
        //CSVParser parser = fr.getCSVParser();
        for (CSVRecord record:resource.getCSVParser()) {
            String rater_id = record.get("rater_id");
            String movie_id = record.get("movie_id");
            double rating = Double.parseDouble(record.get("rating"));
            //-------TODO: CAN WE simplify line 129-146 ???------------
            int count = 0;
            for (Rater j : raters) {
                if (j.getID().contains(rater_id)) {
                    //Rater m = new Rater(rater_id);
                    j.addRating(movie_id, rating);
                    count++;
                    //System.out.println("count = " + count);
                    //System.out.println("Existing rater updated! " + j.getID() + "\t" + movie_id + "\t" + rating);
                    //raters.add(m);
                    break;
                }
            }
            if (count == 0) {
                Rater m = new EfficientRater(rater_id);
                m.addRating(movie_id, rating);
                raters.add(m);
                //System.out.println("New rater added!        " + rater_id + "\t" + movie_id + "\t" + rating);
            }
        }
        return raters;
    }
    public void testLoadRaters() {
        ArrayList<Rater> loadedRatersList = loadRaters("data/ratings_short.csv");
        System.out.println("No. of raters : "+loadedRatersList.size());
        /*for(Rater r : loadedRatersList) {
            String rater_id = r.getID();
            System.out.println("*************************");
            System.out.println("Rater_id : "+rater_id+"   No.of ratings : "+r.numRatings());
            ArrayList<String> items = r.getItemsRated();
            for(String item : items) {
              System.out.println("Movie_id : "+item+"   Rating : "+r.getRating(item));  
            }
        
        }*/
    }
    
    public int ratingsCount(ArrayList<Rater> raters,String ID) {
       //ArrayList<Rater> loadedRatersList = loadRaters("data/ratings_short.csv");
       for(Rater r : raters) {
          if((r.getID()).equals(ID)) {
              return r.numRatings();
          }
       }
       return -1;
    }
    public void testRatingsCount() {
       ArrayList<Rater> raters = loadRaters("data/ratings.csv");
       String rater_id = "193";
       System.out.println("No. of rating by rater_id "+rater_id+" : "+ratingsCount(raters,rater_id));
    }
    
    public void maxNo_Of_Rating(ArrayList<Rater> raters) {
        int max = 0;
        for(Rater r : raters) {
            if(r.numRatings() > max) {
                max = r.numRatings();
            }
        }
        System.out.println("Max no. of ratings "+max+" by these IDs : ");
        int countMax = 0;
        for(Rater r : raters) {
            if(r.numRatings()==max) {
                System.out.println(r.getID());
                countMax++;
            }
        }
        System.out.println("No . of raters with maxinum no. of ratings : "+countMax);
    }
    public void testMax_No_Of_Rating() {
        ArrayList<Rater> raters = loadRaters("data/ratings.csv");
        maxNo_Of_Rating(raters);
    }
    
    public int no_Of_Ratings_Of_Movie(ArrayList<Rater> raters,String movie_id) {
        int count = 0;
        for(Rater r : raters) {
            ArrayList<String> items = r.getItemsRated();
            if(items.contains(movie_id)) {
                count++;
            }
        }
        return count;
    }
    public void testNo_Of_Ratings_Of_Movie() {
        ArrayList<Rater> raters = loadRaters("data/ratings.csv");
        String movie_id = "1798709";
        System.out.println("No of ratings of movie_id "+movie_id+" is "+no_Of_Ratings_Of_Movie(raters,movie_id));
    }
    
    public ArrayList<String> differentMovies(ArrayList<Rater> raters) {
        ArrayList<String> differentMovies = new ArrayList<String>();
        for(Rater r : raters) {
            if(differentMovies.size()==0) {
                differentMovies = r.getItemsRated();
            }
            else {
                ArrayList<String> temp = r.getItemsRated();
                for(String mov : temp ){
                    if(!differentMovies.contains(mov)) {
                        differentMovies.add(mov);
                    }
                }
            }
        }
        return differentMovies;
    }
    public void testDifferentMovies() {
        ArrayList<Rater> raters = loadRaters("data/ratings.csv");
        ArrayList<String> diffMovies = differentMovies(raters); 
        System.out.println("No.of Different Movies rated so far "+diffMovies.size());
    }
}