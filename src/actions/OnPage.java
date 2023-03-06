package actions;

import database.Page;
import fileio.ActionInput;

public interface OnPage {

    /** The action that happens on a certain page*/
    void actionOnPage(ActionInput act, Page page);
}
