package fileio;

import java.util.ArrayList;

public class Input {

    private ArrayList<UserInput> users;
    private ArrayList<MovieInput> movies;
    private ArrayList<ActionInput> actions;

    public Input() {

    }

    /** Get users */
    public ArrayList<UserInput> getUsers() {
        return users;
    }

    /** Set users */
    public void setUsers(final ArrayList<UserInput> users) {
        this.users = users;
    }

    /** Get movies */
    public ArrayList<MovieInput> getMovies() {
        return movies;
    }

    /** Set movies */
    public void setMovies(final ArrayList<MovieInput> movies) {
        this.movies = movies;
    }

    /** Get actions */
    public ArrayList<ActionInput> getActions() {
        return actions;
    }

    /** Set actions */
    public void setActions(final ArrayList<ActionInput> actions) {
        this.actions = actions;
    }


}
