package database;

import fileio.MovieInput;

public class Movie extends MovieInput {

    private Integer numLikes;
    private double rating;
    private Integer numRatings;

    public Movie() {
        this.numLikes = 0;
        this.rating = 0.00;
        this.numRatings = 0;
    }

    public Movie(final MovieInput mov) {

        this.setActors(mov.getActors());
        this.setDuration(mov.getDuration());
        this.setName(mov.getName());
        this.setYear(mov.getYear());
        this.setGenres(mov.getGenres());
        this.setCountriesBanned(mov.getCountriesBanned());
        this.numLikes = 0;
        this.rating = 0.00;
        this.numRatings = 0;
    }


    /** Get the number of likes */
    public Integer getNumLikes() {
        return numLikes;
    }

    /** Set the number of likes */
    public void setNumLikes(final Integer numLikes) {
        this.numLikes = numLikes;
    }

    /** Get the rating */
    public double getRating() {
        return rating;
    }

    /** Set the rating */
    public void setRating(final double rating) {
        this.rating = rating;
    }

    /** Get the number of rating */
    public Integer getNumRatings() {
        return numRatings;
    }

    /** Set the number of rating */
    public void setNumRatings(final Integer numRatings) {
        this.numRatings = numRatings;
    }

}
