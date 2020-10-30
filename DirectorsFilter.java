
/**
 * Write a description of DirectorsFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DirectorsFilter implements Filter{
    private String myDirectors;
    public DirectorsFilter(String directors) {
       myDirectors = directors;    
    }
    
    @Override
    public boolean satisfies(String id) {
       String[] directorList = myDirectors.split(",");
       int counts = 0;
       for(String direc : directorList) {
           if(MovieDatabase.getDirector(id).contains(direc)) {
               counts++;
	   }
       }
       if(counts > 0) {
           return true;
       }
       else {
           return false;
       }
 
    }
}