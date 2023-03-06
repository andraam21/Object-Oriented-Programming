package fileio;

import java.util.ArrayList;

public class MovieInput {

    private String name;
    private String year;
    private Integer duration;

    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;

    public MovieInput() {

    }

    /** Get the name */
    public String getName() {
        return name;
    }

    /** Set the name */
    public void setName(final String name) {
        this.name = name;
    }

    /** Get the year */
    public String getYear() {
        return year;
    }

    /** Set the year */
    public void setYear(final String year) {
        this.year = year;
    }

    /** Get the duration */
    public Integer getDuration() {
        return duration;
    }

    /** Set the duration */
    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    /** Get the genre */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /** Set the genre */
    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    /** Get the actors */
    public ArrayList<String> getActors() {
        return actors;
    }

    /** Set the actors */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    /** Get the banned countries */
    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    /** Set the banned countries */
    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

}
