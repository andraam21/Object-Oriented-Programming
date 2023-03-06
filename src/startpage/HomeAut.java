package startpage;

import actions.ChangePage;
import database.Page;
import fileio.ActionInput;

public final class HomeAut implements ChangePage {
    private static HomeAut instance = null;

    private HomeAut() {
    }

    /** Singleton */
    public static HomeAut getInstance() {
        if (instance == null) {
            instance = new HomeAut();
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
}
