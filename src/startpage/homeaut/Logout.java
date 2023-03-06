package startpage.homeaut;

import actions.ChangePage;
import database.Page;
import fileio.ActionInput;

import java.util.ArrayList;

public final class Logout implements ChangePage {

    private static Logout instance = null;

    private Logout() {
    }

    /** Singleton */
    public static Logout getInstance() {
        if (instance == null) {
            instance = new Logout();
        }
        return instance;
    }

    /** Change the page if possible */
    @Override
    public void actionChangePage(final ActionInput act, final Page page) {
        page.setCurrentPage(Page.PageTypes.HOMENEA);
        page.setCurrentUser(null);
        page.setCurrentMovies(new ArrayList<>());
    }
}
