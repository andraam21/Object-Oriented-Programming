package startpage.homeaut.movies;

import actions.ChangePage;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import database.Page;
import fileio.PrintOutput;
import fileio.ActionInput;
import startpage.homeneaut.Login;

import java.util.ArrayList;

public final class Details implements ChangePage {

    private static Details instance = null;

    private ArrayList<Movie> detailedMovie;

    private Details() {
        detailedMovie = new ArrayList<>();
    }

    /** Singleton */
    public static Details getInstance() {
        if (instance == null) {
            instance = new Details();
        }
        return instance;
    }

    /** Change the page if possible */
    @Override
    public void actionChangePage(final ActionInput act, final Page page) {

        switch (act.getPage()) {
            case "movies" -> page.setCurrentPage(Page.PageTypes.MOVIES);
            case "upgrades" -> page.setCurrentPage(Page.PageTypes.UPGRADES);
            case "logout" -> page.setCurrentPage(Page.PageTypes.LOGOUT);
            default -> {
                break;
            }
        }
    }

    /** Print the result */
    public void doPrint(final Page page, final ActionInput act, final ArrayNode output) {
        int stop = 1;
        this.detailedMovie = new ArrayList<>();
        if (act.getMovie() != null) {
            for (Movie m : page.getCurrentMovies()) {
                if (act.getMovie().equals(m.getName())) {
                    this.getDetailedMovie().add(m);
                    PrintOutput.getInstance().printGood(output, page.getCurrentUser(),
                            this.getDetailedMovie());
                    stop = 0;
                }
            }
            if (stop == 1) {
                PrintOutput.getInstance().printError(output);
                page.setCurrentMovies(Login.getInstance().getNotBanned());
                page.setCurrentPage(Page.PageTypes.MOVIES);
            }
        }
    }

    /** Get the movie */
    public ArrayList<Movie> getDetailedMovie() {
        return detailedMovie;
    }

    /** Set the movie */
    public void setDetailedMovie(final ArrayList<Movie> detailedMovie) {
        this.detailedMovie = detailedMovie;
    }
}
