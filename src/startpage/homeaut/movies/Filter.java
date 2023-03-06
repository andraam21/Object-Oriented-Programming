package startpage.homeaut.movies;

import actions.OnPage;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import database.Page;
import fileio.PrintOutput;
import fileio.ActionInput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Filter implements OnPage {

    private List<Movie> filteredMovies;
    private static Filter instance = null;

    private Filter() {
        filteredMovies = new ArrayList<>();
    }

    /** Singleton */
    public static Filter getInstance() {
        if (instance == null) {
            instance = new Filter();
        }
        return instance;
    }

    /** Do the action if the page is valid */
    @Override
    public void actionOnPage(final ActionInput act, final Page page) {

        this.filteredMovies = new ArrayList<>();
        List<Movie> firstSort = new ArrayList<>();
        List<Movie> secSort = new ArrayList<>();

        if (page.getCurrentMovies().size() == 0) {
            return;
        }

        if (act.getFilters().getContains() != null) {

            if (act.getFilters().getContains().getActors() != null) {
                for (String actor : act.getFilters().getContains().getActors()) {
                    for (Movie movie : page.getCurrentMovies()) {
                        for (String actorMov : movie.getActors()) {
                            if (actor.equals(actorMov)) {
                                firstSort.add(movie);
                            }
                        }
                    }
                }
                this.filteredMovies = firstSort;
            }

            if (act.getFilters().getContains().getGenre() != null) {
                for (String genre : act.getFilters().getContains().getGenre()) {
                    for (Movie movie : page.getCurrentMovies()) {
                        for (String genMovie : movie.getGenres()) {
                            if (genre.equals(genMovie)) {
                                secSort.add(movie);
                            }
                        }
                    }
                }
                this.filteredMovies = secSort;
            }

            if (act.getFilters().getContains().getActors() != null
                    && act.getFilters().getContains().getGenre() != null) {
                this.filteredMovies = new ArrayList<>();
                for (Movie m : firstSort) {
                    for (Movie m1 : secSort) {
                        if (m1.getName().equals(m.getName())) {
                            this.filteredMovies.add(m);
                        }
                    }
                }
            }
        } else {
            this.filteredMovies = new ArrayList<>(page.getCurrentMovies());
        }

        /* In order to sort the list, a comparator for streams is defined based on a certain case */
        if (act.getFilters().getSort() != null) {

            Comparator<Movie> compareDurAndRat = null;

            if (act.getFilters().getSort().getDuration() != null) {
                if (act.getFilters().getSort().getDuration().equals("increasing")) {
                    compareDurAndRat = Comparator.comparing(Movie::getDuration);
                    if (act.getFilters().getSort().getRating() != null) {
                        if (act.getFilters().getSort().getRating().equals("increasing")) {
                            compareDurAndRat = Comparator.comparing(Movie::getDuration).
                                    thenComparing(Movie::getRating);
                        } else {
                            compareDurAndRat = Comparator.comparing(Movie::getDuration).
                                    thenComparing(Movie::getRating).reversed();
                        }
                    }
                }
                if (act.getFilters().getSort().getDuration().equals("decreasing")) {
                    compareDurAndRat = Comparator.comparing(Movie::getDuration).reversed();
                    if (act.getFilters().getSort().getRating() != null) {
                        if (act.getFilters().getSort().getRating().equals("increasing")) {
                            compareDurAndRat = Comparator.comparing(Movie::getDuration).reversed().
                                    thenComparing(Movie::getRating);
                        } else {
                            compareDurAndRat = Comparator.comparing(Movie::getDuration).reversed()
                                    .thenComparing(Movie::getRating).reversed();
                        }
                    }
                }
            } else {
                if (act.getFilters().getSort().getRating() != null) {
                    if (act.getFilters().getSort().getRating().equals("increasing")) {
                        compareDurAndRat = Comparator.comparing(Movie::getRating);
                    } else {
                        compareDurAndRat = Comparator.comparing(Movie::getRating).reversed();
                    }
                }
            }
            List<Movie> sorted = this.filteredMovies.stream().sorted(compareDurAndRat).toList();
            this.filteredMovies = sorted;
        }
    }

    /** Print the result */
    public void doPrint(final Page page, final ArrayNode output) {
        page.setCurrentMovies(new ArrayList<>(this.getFilteredMovies()));
        PrintOutput.getInstance().printGood(output, page.getCurrentUser(), page.getCurrentMovies());
        page.setCurrentPage(Page.PageTypes.MOVIES);
    }



    /** Get the movies */
    public List<Movie> getFilteredMovies() {
        return filteredMovies;
    }

    /** Set the movies */
    public void setFilteredMovies(final List<Movie> filteredMovies) {
        this.filteredMovies = filteredMovies;
    }
}
