package startpage.homeneaut;

import actions.OnPage;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import database.User;
import database.Page;
import fileio.PrintOutput;
import fileio.ActionInput;

import java.util.ArrayList;

public final class Login implements OnPage {

    private static Login instance = null;
    private User cur;
    private ArrayList<Movie> notBanned;

    private Login() {
        this.cur = null;
        this.notBanned = new ArrayList<>();
    }

    /** Singleton */
    public static Login getInstance() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }

    /** Do the action if the page is valid */
    @Override
    public void actionOnPage(final ActionInput act, final Page page) {
        this.cur = null;
        this.notBanned = new ArrayList<>();
        for (User eachUser : page.getListofUsers()) {
            if (eachUser != null) {
                if (eachUser.getCredentials().getName().equals(act.getCredentials().getName())
                        && eachUser.getCredentials().getPassword().
                        equals(act.getCredentials().getPassword())) {
                    this.cur = eachUser;
                }
            }
        }
    }

    /** Get the movies that are not banned for the user, the ones that (s)he can watch */
    public void bannedCountries(final ArrayList<Movie> mov, final User cur) {

        if (mov.size() == 0) {
            return;
        }

        this.notBanned = new ArrayList<>();
        for (Movie m : mov) {
            int add = 1;
            for (String coun : m.getCountriesBanned()) {
                if (coun.equals(cur.getCredentials().getCountry())) {
                    add = 0;
                }
            }
            if (add == 1) {
                this.notBanned.add(m);
            }
        }
    }

    /** Print the result*/
    public void doPrint(final Page page, final ArrayNode output) {
        page.setCurrentUser(Login.getInstance().getCur());

        if (Login.getInstance().getCur() == null) {
            PrintOutput.getInstance().printError(output);
            page.setCurrentPage(Page.PageTypes.HOMENEA);
            return;
        }

        PrintOutput.getInstance().printGood(output, page.getCurrentUser(),
                page.getCurrentMovies());
        page.setCurrentPage(Page.PageTypes.HOMEAUT);
    }

    /** Get the valid movies */
    public ArrayList<Movie> getNotBanned() {
        return notBanned;
    }

    /** Set the valid movies */
    public void setNotBanned(final ArrayList<Movie> notBanned) {
        this.notBanned = notBanned;
    }

    /** Get the current user */
    public User getCur() {
        return cur;
    }

    /** Set the current user */
    public void setCur(final User cur) {
        this.cur = cur;
    }
}
