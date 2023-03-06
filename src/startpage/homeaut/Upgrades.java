package startpage.homeaut;

import actions.ChangePage;
import database.Page;
import fileio.ActionInput;

public final class Upgrades implements ChangePage {

    private static Upgrades instance = null;

    private Upgrades() {
    }

    /** Singleton */
    public static Upgrades getInstance() {
        if (instance == null) {
            instance = new Upgrades();
        }
        return instance;
    }

    /** Change page if possible */
    @Override
    public void actionChangePage(final ActionInput act, final Page page) {
        switch (act.getPage()) {
            case "movies" -> page.setCurrentPage(Page.PageTypes.MOVIES);
            case "see details" -> page.setCurrentPage(Page.PageTypes.DETAILS);
            case "upgrades" -> page.setCurrentPage(Page.PageTypes.UPGRADES);
            case "logout" -> page.setCurrentPage(Page.PageTypes.LOGOUT);
            default -> {
                break;
            }
        }
    }
}
