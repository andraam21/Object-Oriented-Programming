package fileio;

import java.util.ArrayList;

public class
ContainsInput {

    private ArrayList<String> actors;
    private ArrayList<String> genre;

    public ContainsInput() {

    }

    /** Get the actors */
    public ArrayList<String> getActors() {
        return actors;
    }

    /** Set the actors */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    /** Get the genre of the movie */
    public ArrayList<String> getGenre() {
        return genre;
    }

    /** Set the genre of the movie */
    public void setGenre(final ArrayList<String> genre) {
        this.genre = genre;
    }

}
