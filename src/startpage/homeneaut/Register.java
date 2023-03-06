package startpage.homeneaut;

import actions.OnPage;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.User;
import database.Page;
import fileio.PrintOutput;
import fileio.ActionInput;

public final class Register implements OnPage {

    private static Register instance = null;

    private User newRegister;

    private Register() {
        this.newRegister = null;
    }

    /** Singleton */
    public static Register getInstance() {
        if (instance == null) {
            instance = new Register();
        }
        return instance;
    }

    /** Action if the page is valid */
    @Override
    public void actionOnPage(final ActionInput act, final Page page) {

        int add = 1;
        for (User eachUser : page.getListofUsers()) {
            if (eachUser != null) {
                if (eachUser.getCredentials().getName().equals(act.getCredentials().getName())) {
                    add = 0;
                }
            }
        }
        if (add == 1) {
            this.newRegister = new User(act.getCredentials());
        }

    }

    /** Print the result */
    public void doPrint(final Page page, final ArrayNode output) {
        if (Register.getInstance().getNewRegister() == null) {
            PrintOutput.getInstance().printError(output);
            page.setCurrentPage(Page.PageTypes.HOMENEA);
            return;
        }
        page.setCurrentUser(Register.getInstance().getNewRegister());
        PrintOutput.getInstance().printGood(output, page.getCurrentUser(), page.getCurrentMovies());
        page.setCurrentPage(Page.PageTypes.HOMEAUT);
    }

    /** Get the new user */
    public User getNewRegister() {
        return newRegister;
    }

    /** Set the new user */
    public void setNewRegister(final User newRegister) {
        this.newRegister = newRegister;
    }
}
