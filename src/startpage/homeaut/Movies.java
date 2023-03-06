package startpage.homeaut;

import actions.ChangePage;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Page;
import fileio.PrintOutput;
import fileio.ActionInput;
import startpage.homeneaut.Login;


public final class Movies implements ChangePage {

    private static Movies instance = null;

    private Movies() {
    }

    /** Singleton */
    public static Movies getInstance() {
        if (instance == null) {
            instance = new Movies();
        }
        return instance;
    }

    /** Change the page if possible */
    @Override
    public void actionChangePage(final ActionInput act, final Page page) {

        switch (act.getPage()) {
            case "see details" -> page.setCurrentPage(Page.PageTypes.DETAILS);
            case "upgrades" -> page.setCurrentPage(Page.PageTypes.UPGRADES);
            case "logout" -> page.setCurrentPage(Page.PageTypes.LOGOUT);
            default -> {
                break;
            }
        }
    }

    /** Print the results */
    public void doPrint(final Page page, final ArrayNode output) {
        Login.getInstance().bannedCountries(page.getListofMovies(), page.getCurrentUser());
        page.setCurrentMovies(Login.getInstance().getNotBanned());
        PrintOutput.getInstance().printGood(output, page.getCurrentUser(), page.getCurrentMovies());
    }
}
