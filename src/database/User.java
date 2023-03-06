package database;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionInput;
import fileio.CredentialsInput;
import fileio.PrintOutput;
import fileio.UserInput;
import startpage.homeaut.movies.Details;

import java.util.ArrayList;
import java.util.Arrays;

public class User extends UserInput {

    private static final int MOVNUM = 15;
    private static final int MAXRATE = 5;
    private static final int PRICE = 10;

    private Integer tokensCount;
    private Integer numFreePremiumMovies;
    private ArrayList<Movie> purchasedMovies;
    private ArrayList<Movie> watchedMovies;
    private ArrayList<Movie> likedMovies;
    private ArrayList<Movie> ratedMovies;
    private ArrayList<Notification> notifications;


    public User(final User us) {
        super(us);
        this.tokensCount = us.getTokensCount();
        this.numFreePremiumMovies = us.getNumFreePremiumMovies();
        this.purchasedMovies = us.getPurchasedMovies();
        this.watchedMovies = us.getWatchedMovies();
        this.likedMovies = us.getLikedMovies();
        this.ratedMovies = us.getRatedMovies();
    }

    public User(final UserInput us) {
        super(us);
        this.tokensCount = 0;
        this.numFreePremiumMovies = MOVNUM;
        this.purchasedMovies = new ArrayList<>();
        this.watchedMovies = new ArrayList<>();
        this.likedMovies = new ArrayList<>();
        this.ratedMovies = new ArrayList<>();
        this.notifications = new ArrayList<>();

    }

    public User(final CredentialsInput credentials) {
        this.setCredentials(credentials);
        this.tokensCount = 0;
        this.numFreePremiumMovies = MOVNUM;
        this.purchasedMovies = new ArrayList<>();
        this.watchedMovies = new ArrayList<>();
        this.likedMovies = new ArrayList<>();
        this.ratedMovies = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public User() {

    }

    /** Action to buy tokens */
    public void buyTokens(final ActionInput act) {
        Integer newBal = Integer.parseInt(this.getCredentials().getBalance())
                - Integer.parseInt(act.getCount());
        this.getCredentials().setBalance(Integer.toString(newBal));
        this.setTokensCount(Integer.parseInt(act.getCount()));
    }

    /** Action to buy premium account */
    public void buyPremium() {
        this.setTokensCount(this.getTokensCount() - PRICE);
        this.getCredentials().setAccountType("premium");
    }

    /** Action to like a movie */
    public void likeMovie(final ArrayNode output) {
        if (Details.getInstance().getDetailedMovie().size() == 0) {
            PrintOutput.getInstance().printError(output);
            return;
        }

        for (Movie m : this.getWatchedMovies()) {
            if (m.getName().equals(Details.getInstance().
                    getDetailedMovie().get(0).getName())) {
                m.setNumLikes(m.getNumLikes() + 1);
                this.getLikedMovies().add(m);
                PrintOutput.getInstance().printGood(output,
                        this, Details.getInstance().getDetailedMovie());
                return;
            }
        }
        PrintOutput.getInstance().printError(output);
    }

    /** Action to rate a movie */
    public void rateMovie(final ActionInput act, final ArrayNode output) {
        int stop = 0;
        if (Details.getInstance().getDetailedMovie().size() == 0) {
            PrintOutput.getInstance().printError(output);
            return;
        }
        for (Movie alreadyExists : this.getRatedMovies()) {
            if (alreadyExists.getName().equals(Details.getInstance().getDetailedMovie().get(0).getName())) {
                stop = 1;
            }
        }
        for (Movie m : this.getWatchedMovies()) {
            if (m.getName().equals(Details.getInstance().getDetailedMovie().get(0).getName())) {
                if (act.getRate() <= MAXRATE) {
                    m.setNumRatings(m.getNumRatings() + 1);
                    if (m.getNumRatings() == 1) {
                        m.setRating(act.getRate());
                    } else {
                        double newrate = m.getRating() * (m.getNumRatings() - 1);
                        newrate = newrate + act.getRate();
                        m.setRating(newrate / m.getNumRatings());
                    }
                    if (stop == 0) {
                        this.getRatedMovies().add(m);
                    } else {
                        m.setNumRatings(m.getNumRatings() - 1);
                    }
                    PrintOutput.getInstance().printGood(output,
                            this, Details.getInstance().getDetailedMovie());
                    return;
                }
            }
        }
        PrintOutput.getInstance().printError(output);
    }

    /** Action to purchase a movie */
    public void purchaseMovie(final ArrayList<Movie> movies, final ArrayNode output) {
        if (Details.getInstance().getDetailedMovie().size() == 0) {
            PrintOutput.getInstance().printError(output);
            return;
        }
        for (Movie alreadyExists : this.getPurchasedMovies()) {
            if (alreadyExists.getName().equals(Details.getInstance().getDetailedMovie().get(0).getName())) {
                PrintOutput.getInstance().printError(output);
                return;
            }
        }
        for (Movie m : movies) {
            if (m.getName().equals(Details.getInstance().getDetailedMovie().get(0).getName())) {
                if (this.getCredentials().getAccountType().equals("premium")) {
                    if (this.getNumFreePremiumMovies() == 0) {
                        if (this.getTokensCount() >= 2) {
                            this.setTokensCount(this.getTokensCount() - 2);
                            this.getPurchasedMovies().add(m);
                        } else {
                            PrintOutput.getInstance().printError(output);
                            return;
                        }

                    } else {
                        this.setNumFreePremiumMovies(this.getNumFreePremiumMovies() - 1);
                        this.getPurchasedMovies().add(m);
                    }

                } else {
                    if (this.getTokensCount() >= 2) {
                        this.setTokensCount(this.getTokensCount() - 2);
                        this.getPurchasedMovies().add(m);
                    } else {
                        PrintOutput.getInstance().printError(output);
                        return;
                    }
                }
                PrintOutput.getInstance().printGood(output,
                        this, Details.getInstance().getDetailedMovie());
                return;
            }
        }
        PrintOutput.getInstance().printError(output);
    }

    /** Action to watch a movie */
    public void watchMovie(final ArrayNode output) {
        int stop = 0;
        if (Details.getInstance().getDetailedMovie().size() == 0) {
            PrintOutput.getInstance().printError(output);
            return;
        }
        for (Movie alreadyExists : this.getWatchedMovies()) {
            if (alreadyExists.getName().equals(Details.getInstance()
                    .getDetailedMovie().get(0).getName())) {
                stop = 1;
            }
        }
        for (Movie m : this.getPurchasedMovies()) {
            if (m.getName().equals(Details.getInstance()
                    .getDetailedMovie().get(0).getName())) {
                if (stop == 0) {
                    this.getWatchedMovies().add(m);
                }
                PrintOutput.getInstance().printGood(output,
                        this, Details.getInstance().getDetailedMovie());
                return;
            }
        }
        PrintOutput.getInstance().printError(output);
    }

    /** Subscribe to a genre */
    public void subscribe(final String movieGenre, final ArrayNode output, final Page page){
        for (String gen : Details.getInstance().getDetailedMovie().get(0).getGenres()) {
            if (gen.equals(movieGenre)) {
                if (page.getSubscriptions().get(this.getCredentials().getName()) == null) {
                    ArrayList<String> subs = new ArrayList<>();
                    subs.add(movieGenre);
                    page.getSubscriptions().put(this.getCredentials().getName(), subs);
                } else {
                    ArrayList<String> subs = new ArrayList<>(page.getSubscriptions().get(this.getCredentials().getName()));
                    for (String alreadySubscribed : subs) {
                        if (alreadySubscribed.equals(movieGenre)) {
                            PrintOutput.getInstance().printError(output);
                            return;
                        }
                    }
                    subs.add(movieGenre);
                    page.getSubscriptions().put(this.getCredentials().getName(), subs);
                }
            }
        }
    }


    /** Get the number of tokens */
    public Integer getTokensCount() {
        return tokensCount;
    }

    /** Set the number of tokens */
    public void setTokensCount(final Integer tokensCount) {
        this.tokensCount = tokensCount;
    }

    /** Get the number of free premium movies */
    public Integer getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    /** Set the number of free premium movies */
    public void setNumFreePremiumMovies(final Integer numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    /** Get purchased movies */
    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    /** Set purchased movies */
    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    /** Get watched movies */
    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    /** Set watched movies */
    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    /** Get liked movies */
    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    /** Set liked movies */
    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    /** Get rated movies */
    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    /** Set rated movies */
    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    /** Get the notifications */
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    /** Set the notifications */
    public void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }
}
