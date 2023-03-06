package database;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionInput;
import fileio.PrintOutput;
import fileio.UserInput;
import fileio.Input;
import fileio.MovieInput;
import startpage.HomeAut;
import startpage.HomeNeaut;
import startpage.homeaut.Logout;
import startpage.homeaut.Movies;
import startpage.homeaut.Upgrades;
import startpage.homeaut.movies.Details;
import startpage.homeaut.movies.Filter;
import startpage.homeaut.movies.Search;
import startpage.homeneaut.Login;
import startpage.homeneaut.Register;

import java.util.*;

public class Page {

    public enum PageTypes {
        HOMENEA,
        HOMEAUT,
        LOGIN,
        REGISTER,
        MOVIES,
        LOGOUT,
        UPGRADES,
        DETAILS
    }

    private PageTypes currentPage;
    private User currentUser;
    private ArrayList<Movie> currentMovies;
    private ArrayList<Movie> listofMovies;
    private ArrayList<User> listofUsers;
    private LinkedList<PageTypes> allPages;
    private HashMap<String, ArrayList<String>> subscriptions;
    private HashMap<String, Integer> mostLikedGenres;

    public Page(final Input inputData) {
        this.currentPage = PageTypes.HOMENEA;
        this.currentMovies = new ArrayList<>();
        this.currentUser = new User();
        this.listofUsers = new ArrayList<>();
        this.listofMovies = new ArrayList<>();
        this.allPages = new LinkedList<>();
        this.subscriptions = new HashMap<>();
        this.mostLikedGenres = new HashMap<>();
        for (UserInput user : inputData.getUsers()) {
            this.listofUsers.add(new User(user));
        }
        for (MovieInput movie : inputData.getMovies()) {
            this.listofMovies.add(new Movie(movie));
        }
    }

    /** Change the page if possible */
    public void changePage(final ActionInput act, final ArrayNode output) {

        Page.PageTypes previous = this.getCurrentPage();

        if (currentUser != null) {
            allPages.push(previous);
        }

        if (this.getCurrentPage() == Page.PageTypes.HOMENEA) {
            HomeNeaut.getInstance().actionChangePage(act, this);
        }

        if (this.getCurrentPage() == Page.PageTypes.HOMEAUT) {
            HomeAut.getInstance().actionChangePage(act, this);
        }

        if (this.getCurrentPage() == Page.PageTypes.MOVIES) {
            Movies.getInstance().actionChangePage(act, this);
            if (act.getPage().equals("movies")) {
                Movies.getInstance().doPrint(this, output);
                return;
            }
        }

        if (this.getCurrentPage() == Page.PageTypes.DETAILS) {
            Details.getInstance().actionChangePage(act, this);
        }

        if (this.getCurrentPage() == PageTypes.UPGRADES) {
            Upgrades.getInstance().actionChangePage(act, this);
        }

        if (this.getCurrentPage() == Page.PageTypes.LOGOUT) {
            Logout.getInstance().actionChangePage(act, this);
            allPages.clear();
        }

        if (this.getCurrentPage() == previous) {
            PrintOutput.getInstance().printError(output);
            return;
        }

        if (this.getCurrentPage() == Page.PageTypes.MOVIES) {
            Movies.getInstance().doPrint(this, output);
        }

        if (this.getCurrentPage() == Page.PageTypes.DETAILS) {
            Details.getInstance().doPrint(this, act, output);
        }
    }


    /** Do the action if the page is valid */
    public void onPage(final ActionInput act, final ArrayNode output) {
        int stop = 0;

        if (this.currentPage == PageTypes.LOGIN) {
            if (act.getFeature().equals("login")) {
                stop = 1;
            }
        }

        if (this.currentPage == PageTypes.REGISTER) {
            if (act.getFeature().equals("register")) {
                stop = 1;
            }
        }

        if (this.currentPage == PageTypes.MOVIES) {
            if (act.getFeature().equals("search") || act.getFeature().equals("filter")) {
                stop = 1;
            }
        }

        if (this.currentPage == PageTypes.DETAILS) {
            if (act.getFeature().equals("like") || act.getFeature().equals("rate")
                    || act.getFeature().equals("watch") || act.getFeature().equals("purchase")
                    || act.getFeature().equals("subscribe")) {
                stop = 1;
            }
        }

        if (this.currentPage == PageTypes.UPGRADES) {
            if (act.getFeature().equals("buy tokens")
                    || act.getFeature().equals("buy premium account")) {
                stop = 1;
            }
        }

        if (stop == 0) {
            PrintOutput.getInstance().printError(output);
            return;
        }

        if (act.getFeature().equals("login")) {
            Login.getInstance().actionOnPage(act, this);
            Login.getInstance().doPrint(this, output);
        }

        if (act.getFeature().equals("register")) {
            Register.getInstance().actionOnPage(act, this);
            Register.getInstance().doPrint(this, output);
        }

        if (act.getFeature().equals("search")) {
            Search.getInstance().actionOnPage(act, this);
            Search.getInstance().doPrint(this, output);
        }

        if (act.getFeature().equals("filter")) {
            Filter.getInstance().actionOnPage(act, this);
            Filter.getInstance().doPrint(this, output);

        }

        if (act.getFeature().equals("buy tokens")) {
            this.getCurrentUser().buyTokens(act);
        }

        if (act.getFeature().equals("buy premium account")) {
            this.getCurrentUser().buyPremium();
        }

        if (act.getFeature().equals("purchase")) {
            this.getCurrentUser().purchaseMovie(this.getListofMovies(), output);
        }

        if (act.getFeature().equals("like")) {
            this.getCurrentUser().likeMovie(output);
        }

        if (act.getFeature().equals("rate")) {
            this.getCurrentUser().rateMovie(act, output);
        }

        if (act.getFeature().equals("watch")) {
           this.getCurrentUser().watchMovie(output);
        }

        if (act.getFeature().equals("subscribe")) {
            this.getCurrentUser().subscribe(act.getSubscribedGenre(), output, this);
        }
    }

    /** Go back to the previous page */
    public void back(ActionInput act, ArrayNode output) {
        if (allPages.size() != 0 && currentUser != null) {

            PageTypes prev = allPages.pop();

            if (prev == PageTypes.MOVIES && this.getCurrentPage() == PageTypes.MOVIES) {
                setCurrentPage(PageTypes.DETAILS);
                while (true) {
                    PageTypes before = allPages.pop();
                    if (before != PageTypes.MOVIES) {
                        allPages.push(before);
                        break;
                    }
                }
                PrintOutput.getInstance().printError(output);
                return;
            }

            if (prev == this.getCurrentPage()) {
                allPages.pop();
            }

            setCurrentPage(prev);

            if (this.getCurrentPage() == Page.PageTypes.MOVIES) {
                Movies.getInstance().doPrint(this, output);
            }

            if (this.getCurrentPage() == PageTypes.DETAILS && allPages.size() == 0) {
                PrintOutput.getInstance().printError(output);
            }

        } else {
            PrintOutput.getInstance().printError(output);
        }
    }

    /** Add or delete movies from the database */
    public void database(final ActionInput act, final ArrayNode output) {

        if (act.getFeature().equals("add")) {
            for (Movie movie : this.listofMovies) {
                if (movie.getName().equals(act.getAddedMovie().getName())) {
                    PrintOutput.getInstance().printError(output);
                    return;
                }
            }
            this.listofMovies.add(act.getAddedMovie());

            // Notify the users that a new movie was added
            for (User user : this.getListofUsers()) {
                if (subscriptions.get(user.getCredentials().getName()) != null) {
                    for (String genre : act.getAddedMovie().getGenres()) {
                        for (String subs : subscriptions.get(user.getCredentials().getName())) {
                            if (subs.equals(genre)) {
                                Notification not = new Notification();
                                not.setMovieName(act.getAddedMovie().getName());
                                not.setMessage("ADD");
                                user.getNotifications().add(not);
                                return;
                            }
                        }
                    }
                }
            }
        }

        if (act.getFeature().equals("delete")) {
            ArrayList<Movie> actualised = new ArrayList<>();
            int hasToBeDeleted = 0;
            for (Movie movie : this.listofMovies) {
                if (movie.getName().equals(act.getDeletedMovie())) {
                    hasToBeDeleted = 1;
                } else {
                    actualised.add(new Movie(movie));
                }
            }
            listofMovies = actualised;
            if (hasToBeDeleted == 0) {
                PrintOutput.getInstance().printError(output);
            }
            else {
                for (User us : getListofUsers()) {
                    for (Movie mov : us.getPurchasedMovies()) {
                        if (mov.getName().equals(act.getDeletedMovie())) {
                            if (us.getCredentials().getAccountType().equals("premium")) {
                                us.setNumFreePremiumMovies(us.getNumFreePremiumMovies() + 1);
                            } else {
                                us.setTokensCount(us.getTokensCount() + 2);
                            }
                        }
                    }
                }
            }
        }

    }

    /** Send recommendations for the user if premium */
    public void recommendations(final ArrayNode output) {
        Notification not = new Notification();
        /* Find the recommendation, lambda function is used in order to increase the
        number of likes for every genre of the movie
         */
        for (Movie movie : this.getCurrentUser().getLikedMovies()) {
            for (String genre : movie.getGenres()) {
                mostLikedGenres.merge(genre, 1, Integer::sum);
            }
        }
        int max = 0;
        String bestGenre = null;
        for (String key : mostLikedGenres.keySet()) {
            if (mostLikedGenres.get(key) > max) {
                max = mostLikedGenres.get(key);
                bestGenre = key;
            } else if (mostLikedGenres.get(key) == max) {
                if (bestGenre.compareTo(key) > 0) {
                    bestGenre = key;
                }
            }
        }

        Login.getInstance().bannedCountries(listofMovies, currentUser);
        List<Movie> orderedByLikes = new ArrayList<>();
        for (Movie movie : Login.getInstance().getNotBanned()) {
            for (Movie alreadyWatched : currentUser.getWatchedMovies()) {
                if (!(movie.getName().equals(alreadyWatched.getName()))) {
                    orderedByLikes.add(movie);
                }
            }
        }

        orderedByLikes = new ArrayList<>(orderedByLikes.stream().
                sorted(Comparator.comparingInt(Movie::getNumLikes).reversed()).toList());

        for (Movie movie : orderedByLikes) {
           for (String genre : movie.getGenres()) {
               if (genre.equals(bestGenre)) {
                   not.setMovieName(movie.getName());
                   this.getCurrentUser().getNotifications().add(not);
                   PrintOutput.getInstance().printGood(output, this.currentUser, null);
                   return;
               }
           }
        }
        this.getCurrentUser().getNotifications().add(not);
        PrintOutput.getInstance().printGood(output, this.currentUser, null);
    }


    /** Get the list of all movies */
    public ArrayList<Movie> getListofMovies() {
        return listofMovies;
    }

    /** Set the list of all movies */
    public void setListofMovies(final ArrayList<Movie> listofMovies) {
        this.listofMovies = listofMovies;
    }

    /** Get the list of all users */
    public ArrayList<User> getListofUsers() {
        return listofUsers;
    }

    /** Set the list of all users */
    public void setListofUsers(final ArrayList<User> listofUsers) {
        this.listofUsers = listofUsers;
    }

    /** Get the current user */
    public User getCurrentUser() {
        return currentUser;
    }

    /** Set the current user */
    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    /** Get the current movies */
    public ArrayList<Movie> getCurrentMovies() {
        return currentMovies;
    }

    /** Set the current movies */
    public void setCurrentMovies(final ArrayList<Movie> currentMovies) {
        this.currentMovies = currentMovies;
    }

    /** Get the current page */
    public PageTypes getCurrentPage() {
        return currentPage;
    }

    /** Set the current page */
    public void setCurrentPage(final PageTypes currentPage) {
        this.currentPage = currentPage;
    }

    /** Get and remember the pages */
    public LinkedList<PageTypes> getAllPages() {
        return allPages;
    }

    /** Set the pages */
    public void setAllPages(final LinkedList<PageTypes> allPages) {
        this.allPages = allPages;
    }

    /** Get the subscription of a certain user */
    public HashMap<String, ArrayList<String>> getSubscriptions() {
        return subscriptions;
    }

    /** Set the subscription of a certain user */
    public void setSubscriptions(final HashMap<String, ArrayList<String>> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
