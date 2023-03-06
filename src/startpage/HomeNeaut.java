package startpage;

import actions.ChangePage;
import database.Page;
import fileio.ActionInput;

public final class HomeNeaut implements ChangePage {
    private static HomeNeaut instance = null;

    private HomeNeaut() {
    }

    /** Singleton */
    public static HomeNeaut getInstance() {
        if (instance == null) {
            instance = new HomeNeaut();
        }
        return instance;
    }

    /** Change the page if possible */
    @Override
    public void actionChangePage(final ActionInput act, final Page page) {
        switch (act.getPage()) {
            case "login" -> page.setCurrentPage(Page.PageTypes.LOGIN);
            case "register" -> page.setCurrentPage(Page.PageTypes.REGISTER);
            default -> {
                break;
            }
        }
    }
}
