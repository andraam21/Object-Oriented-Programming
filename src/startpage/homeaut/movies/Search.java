package startpage.homeaut.movies;

import actions.OnPage;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import database.Page;
import fileio.PrintOutput;
import fileio.ActionInput;

import java.util.ArrayList;

public final class Search implements OnPage {
    private static Search instance = null;

    private ArrayList<Movie> searchedMovies;

    private Search() {
        this.searchedMovies = new ArrayList<>();
    }

    /** Singleton */
    public static Search getInstance() {
        if (instance == null) {
            instance = new Search();
        }
        return instance;
    }

    /** Do the action if the page is valid */
    @Override
    public void actionOnPage(final ActionInput act, final Page page) {
        this.searchedMovies = new ArrayList<>();

        if (page.getCurrentMovies().size() == 0) {
            return;
        }

        for (Movie m : page.getCurrentMovies()) {
            int index = m.getName().indexOf(act.getStartsWith());
            if (index == 0) {
                this.searchedMovies.add(m);
            }
        }
    }

    /** Print the result */
    public void doPrint(final Page page, final ArrayNode output) {
        PrintOutput.getInstance().printGood(output, page.getCurrentUser(),
                this.getSearchedMovies());
    }

    /** Get the searched movie */
    public ArrayList<Movie> getSearchedMovies() {
        return searchedMovies;
    }

    /** Set the searched movie */
    public void setSearchedMovies(final ArrayList<Movie> searchedMovies) {
        this.searchedMovies = searchedMovies;
    }

}
