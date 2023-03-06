package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import database.User;

import java.util.ArrayList;

public final class PrintOutput {

    private static PrintOutput instance = null;
    private String error;
    private ArrayList<Movie> currentMoviesList;
    private User currentUser;

    /** Singleton */
    public static PrintOutput getInstance() {
        if (instance == null) {
            instance = new PrintOutput();
        }
        return instance;
    }

    private PrintOutput() {
        this.error = "Error";
        this.currentMoviesList = new ArrayList<>();
        this.currentUser = null;
    }

    /** Print in case of error */
    public void printError(final ArrayNode output) {
        PrintOutput res = new PrintOutput();
        ObjectMapper mapper = new ObjectMapper();
        output.add(mapper.valueToTree(res));
    }

    /** Print in case the action was done successfully */
    public void printGood(final ArrayNode output, final User cur, final ArrayList<Movie> mov) {
        PrintOutput res = new PrintOutput();
        res.error = null;
        res.currentUser = cur;
        res.setCurrentMoviesList(mov);
        ObjectMapper mapper = new ObjectMapper();
        output.add(mapper.valueToTree(res));
    }

    /** Get the error */
    public String getError() {
        return error;
    }

    /** Set the error */
    public void setError(final String error) {
        this.error = error;
    }

    /** Get the current movies */
    public ArrayList<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }

    /** Set the current movies */
    public void setCurrentMoviesList(final ArrayList<Movie> currentMovieList) {
        this.currentMoviesList = currentMovieList;
    }

    /** Get the current user */
    public User getCurrentUser() {
        return currentUser;
    }

    /** Get the current user */
    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }
}
