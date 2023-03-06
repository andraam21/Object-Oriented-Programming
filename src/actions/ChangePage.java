package actions;

import database.Page;
import fileio.ActionInput;

public interface ChangePage {

    /** Change the page if possible */
    void actionChangePage(ActionInput act, Page page);
}
